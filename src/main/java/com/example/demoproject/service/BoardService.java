package com.example.demoproject.service;

import com.example.demoproject.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    // 모든 게시물 조회
    List<Board> getAllBoards();

    // 특정 게시물 조회
    Optional<Board> getBoardByBseq(int bseq);

    // 게시물 생성
    Board createBoard(Board board);

    // 게시물 업데이트
    Board updateBoard(int bseq, Board updatedBoard);

    // 게시물 삭제
    void deleteBoard(int bseq, String loggedInUsername);
}