package com.example.project_board.service.board;

import com.example.project_board.entity.account.Member;
import com.example.project_board.entity.board.Board;
import com.example.project_board.repository.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


//JPA가 @Service로 선언된 클래스를 갖고 JDBC에게 기능적인 구현을 위한 속성
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepo;

    //순환참조 중단
    @Autowired
    protected BoardServiceImpl(BoardRepository boardRepo) {
        this.boardRepo = boardRepo;
    }

    //클라이언트에서 받아온 Board객체의 데이터를 BoardRepository의 상속받은 CrudRepository의 findAll메서드를 통해서
    //전체 조회
    @Override
    public List<Board> getBoardList() {
        return (List<Board>) boardRepo.findAll();
    }

    //클라이언트에서 받아온 Board객체의 데이터를 BoardRepository의 상속받은 CrudRepository의 Save메서드를 통해서
    //DB에 저장 (저장하는 SQL문 만들어서 실행)
    @Override
    public void insertBoard(Board board) {
        boardRepo.save(board);
    }

    @Override
    public Board getBoard(Board board) {
        return boardRepo.findById(board.getSeq()).get();
    }

    @Override
    public void updateBoard(Board board) {
        Board findBoard = boardRepo.findById(board.getSeq()).get();
        findBoard.setCategory(board.getCategory());
        findBoard.setTitle(board.getTitle());
        findBoard.setContent(board.getContent());
        System.out.println(findBoard);
        boardRepo.save(findBoard);
    }

    @Override
    public void deleteBoard(Board board) { boardRepo.deleteById(board.getSeq());
    }

//    @Override
//    public void insertComment(Comments comments) {
//
//    }
    @Override
    public boolean booleanMemberIdEqualsBoardWriterByMember(Member member) {
        return false;
    }
    
    @Override
    public List<Board> getBoardListByMemberId(Member member) {
        return boardRepo.findAllByMemberIdEqualsBoardWriter(member.getId());
    }

    @Override
    public List<String> doNounsAnalysis(List<Board> boardList) {
        return null;
    }

    @Override
    public List<Board> getAutoKeywordBoardList(List<String> keyword) {
        return null;
    }

    @Override
    public List<Board> getBoardListSortColumnBoardList(List<Board> boardList) {
        return null;
    }
}