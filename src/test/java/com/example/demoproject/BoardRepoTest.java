/*
package com.example.demoproject;

import com.example.demoproject.domain.Board;
import com.example.demoproject.domain.Member;
import com.example.demoproject.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class BoardRepoTest {

    @Autowired
    private BoardRepository boardRepo;

    @Test
    public void testSaveBoard() {
        Member member = Member.builder()
                .username("testUser")  // 실제 필요한 필드에 유효한 값 설정
                .pwd("testPassword")  // 비밀번호 등 기타 필드
                .email("testUser@example.com")  // 이메일 등
                .build();
        // 필드값 수정 및 유효한 값 설정
        Board board = Board.builder()
                .member(member)
                .answerContent("답변 내용입니다.")
                .answered("N") // 'Y' 또는 'N'만 허용됨
                .content("게시글 내용입니다.")
                .title("게시글 제목입니다.")
                .createdDate(new Date())
                .build();

        // 게시글 저장
        boardRepo.save(board);
    }
}
*/
