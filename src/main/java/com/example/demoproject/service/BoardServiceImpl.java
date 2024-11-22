package com.example.demoproject.service;

import com.example.demoproject.domain.Board;
import com.example.demoproject.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 모든 게시물 조회
    @Override
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // 특정 게시물 조회
    @Override
    public Optional<Board> getBoardByBseq(int bseq) {
        return boardRepository.findById(bseq);
    }

    // 게시물 생성
    @Override
    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    // 게시물 업데이트
    @Override
    public Board updateBoard(int bseq, Board updatedBoard) {
        Optional<Board> optionalBoard = boardRepository.findById(bseq);
        if (optionalBoard.isPresent()) {
            Board existingBoard = optionalBoard.get();
            existingBoard.setTitle(updatedBoard.getTitle());
            existingBoard.setContent(updatedBoard.getContent());
            existingBoard.setAnswerContent(updatedBoard.getAnswerContent());
            existingBoard.setAnswered("Y".equals(updatedBoard.getAnswered()) ? "Y" : "N");
            return boardRepository.save(existingBoard);
        } else {
            throw new RuntimeException("Board not found with bseq: " + bseq);
        }
    }

    // 게시물 삭제
    @Override
    public void deleteBoard(int bseq, String loggedInUsername) {
        Board board = boardRepository.findById(bseq)
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + bseq));

        if (!board.getMember().getUsername().equals(loggedInUsername)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        boardRepository.delete(board);
    }
}