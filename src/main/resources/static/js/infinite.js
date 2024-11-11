$(document).ready(function () {
    let page = 0;  // 초기 페이지 설정
    let loading = false;  // 현재 로딩 중인지 확인

    // 초기 데이터 로드
    loadPosts(page);

    // 스크롤 이벤트 추가
    $(window).scroll(function () {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100 && !loading) {
            page++;
            loadPosts(page);
        }
    });

    // 게시글을 로드하는 함수
    function loadPosts(page) {
        loading = true; // 로딩 시작
        $('#loading').show(); // 로딩 메시지 표시

        $.ajax({
            url: `/api/posts?page=${page}`,  // 페이지 번호에 따라 게시글 요청
            type: 'GET',
            success: function (posts) {
                $('#loading').hide(); // 로딩 메시지 숨기기
                loading = false; // 로딩 종료

                // 게시글을 반복해서 추가
                posts.forEach(post => {
                    $('#post-container').append(`
                            <div class="post-item">
                                <div class="post-header">
                                    <span>${post.username}</span>
                                </div>
                                <div class="post-image">
                                    <img src="${post.imageUrl}" alt="Post Image">
                                </div>
                                <div class="post-description">
                                    <span>${post.description}</span>
                                </div>
                                <div class="post-footer">
                                    <button>좋아요</button>
                                    <span>좋아요 ${post.likes}개</span>
                                </div>
                            </div>
                        `);
                });

                // 만약 게시글이 더 이상 없으면 스크롤 이벤트 제거
                if (posts.length === 0) {
                    $(window).off('scroll');
                }
            },
            error: function (error) {
                console.error('게시글 로드 오류:', error);
                $('#loading').hide(); // 로딩 메시지 숨기기
                loading = false;
            }
        });
    }
});