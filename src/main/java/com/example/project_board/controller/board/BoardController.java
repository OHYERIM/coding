package com.example.project_board.controller.board;

import com.example.project_board.entity.account.Member;
import com.example.project_board.entity.board.Board;
import com.example.project_board.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/board")
public class BoardController {


    private final BoardService boardService;

    @Autowired
    protected BoardController(BoardService boardService){this.boardService = boardService;}
    //BoardService의 getBoardList메서드 실행 > BoardRepository(CrudRepository).findAll()를 통해서 (JPA번역)
    //DB의 데이터 불러오기(테이블전체) (SQL)
    @GetMapping("/getBoardList")
    public String getBoardList(Model model, Board board) {
        List<Board> boardList = boardService.getBoardList();
        model.addAttribute("boardList",boardList);
        return "/board/getBoardList";
    }

    @GetMapping("/insertBoard")
    public String insertBoard() {
        return "/board/insertBoard";
    }
//    public String insertBoardView() {
//
//        return "/board/insertBoard";
//    }

    @PostMapping("/insertBoard")
    public String insertBoard(Board board) {
//        board.setCreateDate(new Date());
        //꼭 알기!
        //클라이언트에서 board객체를 받아서 매개변수로 사용
        //[1]BoardService의 inserBoard메서드 실행 >
        //[2]BoardRepository(CrudRepository).save(board)를 통해서 (JPA번역)
        //DB의 저장 (SQL)
        //insertBoard라는 메서드에 board객체 인자값으로 넣기
        boardService.insertBoard(board);
        return "redirect:/board/getBoardList";
    }

    @GetMapping("/getBoard")    //
    public String getBoard(Board board, Model model) {
        model.addAttribute("board", boardService.getBoard(board));
        return "/board/getBoard";
    }

    @PostMapping("/updateBoard")
    public String updateBoard(Board board) {
        boardService.updateBoard(board);
//        return "redirect:/board/getBoardList";
        return "redirect:/board/getBoard?seq="+board.getSeq();
    }

    @GetMapping("/updateBoard")
    public String updateBoardView(Board board, Model model) {
        model.addAttribute("board", boardService.getBoard(board));
        return "/board/insertBoard";
    }

    @PostMapping("/deleteBoard")
    public String deleteBoard(Board board) {
        boardService.deleteBoard(board);
        return "redirect:/board/getBoardList";
    }

    //2022.08.22
    @GetMapping("/selectBoard")
    public String selectBoard(Member member, Model model) {
        //board.getId()는 클라이언트에서 가져옴
        //@Service에 board를 인자값으로 넣고, 메소드를 실행
        boardService.getBoardListByMemberId(member);
        model.addAttribute("boardList",boardService.getBoardListByMemberId((member)));

        //return 해줘야 되는것은? 회원이 작성한 게시글리스트(List<Board>)
        //어디에 해줘야 될까? [페이지 또는 Controller mapping] -> HTML에다가 뿌려주면 끝이다. Controller에 가면 메소드가 실행되면서 다른 결과물을 리턴받기 때문이다.
        //그러면 어느 HTML로 가야하는가? 객체지향은 재활용성이 중요한 요인 중 하나이기 때문에 HTML 중에서 재사용 할만한 것을 먼저 찾은 후 새로 만들기에 대한 고민을 해야한다. -> getBoardList
        return "/board/getBoardList";
    }
}

