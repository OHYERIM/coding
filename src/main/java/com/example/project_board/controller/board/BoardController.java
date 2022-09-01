package com.example.project_board.controller.board;

import com.example.project_board.entity.account.Member;
import com.example.project_board.entity.board.Board;
import com.example.project_board.entity.board.Comments;
import com.example.project_board.entity.data.FileUploadEntity;
import com.example.project_board.service.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.xml.stream.events.Comment;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.google.common.io.ByteStreams.toByteArray;

@Controller
@Slf4j
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

    @GetMapping("insertComments")
    public String insertComments(Board board, Model model) {
        model.addAttribute("board", board);
        return "/board/insertComments";
    }
    @PostMapping("insertComments")
    public String insertComments(Comments comments, Model model) {
        boardService.insertComments(comments);
        return "redirect:/board/getBoardList";
    }

    //board Seq를 전달하면 전체 comments를 불러오는 controller method
    @GetMapping("getCommentsList")
    public String getCommentsList(Comments comments, Model model) {
        model.addAttribute("commentsList", boardService.getAllComments(comments));
        return "/board/getCommentsList";
    }

    //client에서 server로 이미지파일 전송(데이터 전송)
    //html form태그에 upload버튼으로 이미지 데이터 전송(MultipartFile)
    //방법1. server는 이미지파일을 특정 폴더에 저장
    //      장점: 서버에 원본 이미지 파일을 저장하므로 필요할 때 서버에서 바로 전달 받을 수 있음 = db에 부담감이 줄어듬
    //      단점: 다수의 서버에 이미지 파일을 저장할 경우, 동일한 이미지 데이터 처리에 대한 이슈 발생 > UUID를 통해 이미지 이름을 구분
    //방법2. server는 이미지파일을 byte코드로 db에 저장
    //      장점: 이미지 데이터를 한 곳에 저장하고 관리
    //      단점: DB에 많은 부하가 걸림, 데이터 저장 포맷의 한게(Oracle 기준으로 Blob단위로 저장할 때, 4GB한계의 이슈)
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("uploadfile")MultipartFile[] uploadfile,
                             @RequestParam("writer")String input_writer) throws IOException {
        //@RequestParam("writer") = 클라이언트 html의 input tag의 name(key)값인 writer를 controller에서
        //매개변수 String input_writer로 전달
        log.info(input_writer);
        //multipartifile을 클라이언트에서 서버로 RequestParam데이터 받아옴 name = uploadfile
//        System.out.println("test");
        //@Slf4j Lombook라이브러리로 log데이터 찍음
        //info / error / debug 단위가 있고 단위마다 필터링하여 정보를 수집하고 관리 가능
        log.info("img load session");
        //multipartfile데이터를 수집하여 entity FileUploadEntity에 데이터 저장
        List<FileUploadEntity> list = new ArrayList<>();
        for(MultipartFile file : uploadfile) {
            //MultipartFile file이 있을 때까지 작업 진행
            if(!file.isEmpty()){
                //MultipartFile의 정보를 dto에 저장
                //file.get~ 메소드는 MultipartFile (이미지) 내부에 있는 메타데이터를 가져오는 메소드
                //input_writer는 클라이언트에서 데이터를 직접 전달하는 String데이터
                FileUploadEntity entity = new FileUploadEntity(null,
                        UUID.randomUUID().toString(),
                        file.getContentType(),
                        file.getName(),
                        file.getOriginalFilename(),
                        input_writer
                        );
                boardService.insertFileUploadEntity(entity);
                list.add(entity);
                File newFileName = new File(entity.getUuid()+"_"+entity.getName()+".PNG");
                //file을 서버에 저장하는 스트림행위는 서버가 성공할지 여부를 체크하므로 exeption처리 필요
                //메소드에 throws IOException 처리 = try catch처리 필요
                file.transferTo(newFileName);
            }
        }

        return "/board/getBoardList";
    }

    //server에서 client로 이미지 전송
    //springboot에서 URL주소를 통해 이미지를 받은 InputStream을 통해 파일을 http프로토콜에 전달하여 클라이언트에게 전송

    @GetMapping("viewImage/{imgname}")
    public ResponseEntity<byte[]> viewImage(@PathVariable("imgname")String input_imgName) throws IOException {
        //ResponseEntity<byte[]>: http프로토콜을 통해서 byte데이터를 전달하는 객체, byte(소문자 = 기본타입) []배열
        //@PathVariable: URL주소의 값을 받아옴
        String path = "C:\\\\\\\\Users\\\\\\\\82102\\\\\\\\Desktop\\\\\\\\coding\\\\\\\\Spring Boot\\\\\\\\Project_board\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\static\\\\\\\\upload\\\\\\\\"+input_imgName;
        //데이터(이미지)를 전송하기 위한 객체로써 java에서는 항상 데이터를 스트림타입으로 전달
        InputStream inputStream = new FileInputStream(path);
        //byte배열로 변환
        byte[] imgByteArr = toByteArray(inputStream);
        inputStream.close();
        //ResponseEntity를 통해 http프로토콜로 클라이언트에게 데이터 전송
        return new ResponseEntity<byte[]>(imgByteArr, HttpStatus.OK);
    }
}

