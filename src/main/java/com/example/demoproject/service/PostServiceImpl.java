package com.example.demoproject.service;

import com.example.demoproject.PostDto;
import com.example.demoproject.domain.Like;
import com.example.demoproject.domain.Member;
import com.example.demoproject.domain.Post;
import com.example.demoproject.repository.LikeRepository;
import com.example.demoproject.repository.MemberRepository;
import com.example.demoproject.repository.PostRepository;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final Path fileStorageLocation;

    private final PostRepository pr;
    private final MemberRepository mr;
    private final LikeRepository li;

    @Autowired
    public PostServiceImpl(@Value("${file.upload-dir}") String uploadDir,
                           PostRepository pr,
                           MemberRepository mr,
                           LikeRepository li) {
        this.uploadDir = uploadDir;
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("업로드 디렉토리를 생성할 수 없습니다.", ex);
        }
        this.pr = pr;
        this.mr = mr;
        this.li = li;
    }

    @Transactional
    @Override
    public void savePost(PostDto postDto, UserDetails userDetails) {
        System.out.println("savePost 메서드 시작");

        if (userDetails == null) {
            throw new RuntimeException("인증되지 않은 사용자입니다.");
        }

        String username = userDetails.getUsername();
        var memberOptional = mr.findById(username);
        if (memberOptional.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        var member = memberOptional.get();

        String imagePath = null;

        if (postDto.getImagePath() != null && !postDto.getImagePath().isEmpty()) {
            imagePath = postDto.getImagePath();
        } else if (postDto.getImageFile() != null && !postDto.getImageFile().isEmpty()) {
            imagePath = storeImage(postDto.getImageFile());
        }

        if (imagePath == null) {
            throw new RuntimeException("이미지 파일을 업로드해 주세요.");
        }

        Post post = Post.builder()
                .content(postDto.getContent())
                .imagePath(imagePath)
                .member(member)
                .heart(0)
                .likes(0)
                .comments("")
                .build();

        try {
            pr.save(post);
            pr.flush();  // 데이터베이스에 즉시 반영
            System.out.println("게시글 저장 성공: " + post);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("게시글 저장 실패");
            throw e;  // 예외 재발생
        }
    }

    @Override
    public String storeImage(MultipartFile image) {
        // 파일 유효성 검증
        if (image == null || image.isEmpty()) {
            throw new RuntimeException("유효한 이미지 파일을 제공해 주세요.");
        }

        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));

        try {
            // 파일 이름에 부적절한 문자가 포함되어 있는지 확인
            if (originalFileName.contains("..")) {
                throw new RuntimeException("파일 이름에 부적절한 문자가 포함되어 있습니다: " + originalFileName);
            }

            // Apache Tika를 사용하여 파일 유형 검증
            Tika tika = new Tika();
            String detectedType = tika.detect(image.getInputStream());
            if (!detectedType.startsWith("image/")) {
                throw new RuntimeException("이미지 파일만 업로드할 수 있습니다.");
            }

            // 파일 확장자 추출
            String fileExtension = getFileExtension(originalFileName);
            if (fileExtension.isEmpty()) {
                throw new RuntimeException("파일 확장자를 찾을 수 없습니다.");
            }

            String newFileName = UUID.randomUUID().toString() + "." + fileExtension;

            Path targetLocation = this.fileStorageLocation.resolve(newFileName);

            // 이미지 파일 저장
            Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 저장된 파일의 상대 경로 반환 (예: unique-file-name.jpg)
            return newFileName;

        } catch (IOException ex) {
            throw new RuntimeException("이미지 파일을 저장하는 중 오류가 발생했습니다.", ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    @Override
    public void deletePost(Post post) {
        pr.delete(post);
    }

    @Override
    public List<Post> getPosts() {
        return pr.findAll();
    }

    @Override
    public List<Post> getPostsByMember(Member member) {
        return pr.findByMember(member);
    }

    @Override
    public List<Post> searchPostsByTitle(String title) {
        return pr.findByTitleContaining(title);
    }

    @Override
    public Page<Post> getLatestPosts(Pageable pageable) {
        return pr.findAllByOrderByPostdateDesc(pageable);
    }

    @Transactional
    public int toggleLike(int pseq, Member currentUser) {
        Post post = pr.findById(pseq)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        Optional<Like> existingLike = li.findByMemberAndPost(currentUser, post);

        if (existingLike.isPresent()) {
            // 이미 좋아요한 경우, 좋아요 취소
            li.delete(existingLike.get());
            post.setLikes(post.getLikes() - 1);
        } else {
            // 아직 좋아요하지 않은 경우, 좋아요 추가
            Like like = Like.builder()
                    .member(currentUser)
                    .post(post)
                    .build();
            li.save(like);
            post.setLikes(post.getLikes() + 1);
        }

        pr.save(post);
        return post.getLikes();
    }
}
