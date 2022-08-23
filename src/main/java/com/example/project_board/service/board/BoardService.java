package com.example.project_board.service.board;

import com.example.project_board.entity.account.Member;
import com.example.project_board.entity.board.Board;

import java.util.List;

public interface BoardService {
    List<Board> getBoardList();

    void insertBoard(Board board);

    Board getBoard(Board board);

    void updateBoard(Board board);

    void deleteBoard(Board board);

//    void insertComment(Commnets commnets);

    //board의 작성자와 회원이 같은지 확인 [2,5조...]
    boolean booleanMemberIdEqualsBoardWriterByMember(Member member);

    //작성자의 모든 게시글 출력
    List<Board> getBoardListByMemberId(Member member);

    //키워드 분석
    List<String> doNounsAnalysis(List<Board> boardList);

    //관련된 키워드 게시글 출력
    List<Board> getAutoKeywordBoardList(List<String> keyword);

    //오름차순으로 변경 (arrayList)
    List<Board> getBoardListSortColumnBoardList(List<Board> boardList);

}
