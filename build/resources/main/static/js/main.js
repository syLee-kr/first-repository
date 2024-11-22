document.addEventListener("DOMContentLoaded", function () {
    // 이미지 Lazy Loading
    const images = document.querySelectorAll('img[data-src]');
    images.forEach(img => {
        img.src = img.getAttribute('data-src');
        img.removeAttribute('data-src');
    });
});

function getCsrfToken() {
    return document.querySelector('meta[name="_csrf"]').getAttribute('content');
}

function getCsrfHeaderName() {
    return document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
}

async function toggleLike(button) {
    try {
        if (!(button instanceof HTMLElement)) {
            console.error('Invalid button element:', button);
            alert('잘못된 버튼 요소입니다.');
            return;
        }

        // 버튼의 data-post-id 속성에서 postId 가져오기
        const postId = button.getAttribute('data-post-id');
        console.log('Post ID:', postId); // 디버깅용 로그

        if (!postId || postId === 'null') {
            console.error('Invalid postId:', postId);
            alert('잘못된 게시물 ID입니다.');
            return;
        }

        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeaderName();

        console.log('CSRF Token:', csrfToken); // CSRF 토큰 확인용 로그
        console.log('CSRF Header:', csrfHeader); // CSRF 헤더 확인용 로그

        const response = await fetch(`/api/posts/${postId}/like`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            credentials: 'include'
        });

        if (!response.ok) {
            throw new Error(`Failed to toggle like: ${response.status}`);
        }

        const likeCount = await response.json();
        document.querySelector(`#likeCount-${postId} span`).innerText = likeCount;

        button.classList.toggle('liked');

    } catch (error) {
        console.error('에러:', error);
        alert('요청을 처리하는 중 문제가 발생했습니다. 잠시 후 다시 시도해 주세요.');
    }
}

window.onclick = function(event) {
    var modal = document.getElementById('myModal');
    if (event.target == modal) {
        closeModal();
    }
}

document.addEventListener('keydown', function(event) {
    if (event.key === "Escape") {
        closeModal();
    }
});

function previewImage(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();

        reader.onload = function(e) {
            // 이미지 미리보기를 위한 src 설정
            document.getElementById("imagePreview").src = e.target.result;

            // 업로드 폼 숨기기
            document.getElementById("uploadForm").style.display = "none";

            // 미리보기 컨테이너 표시
            document.getElementById("imagePreviewContainer").style.display = "flex";

            // "다음" 버튼 표시
            document.getElementById("nextButton").style.display = "block";
        };

        reader.readAsDataURL(file);
    }
}
function goToContent() {
    document.getElementById('imageUploadSection').style.display = 'none';
    document.getElementById('contentSection').style.display = 'block';
}
function openModal() {
    document.getElementById('myModal').style.display = 'block';
}

// 모달을 닫는 함수
function closeModal() {
    document.getElementById('myModal').style.display = 'none';
}