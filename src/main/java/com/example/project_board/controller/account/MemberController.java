package com.example.project_board.controller.account;

import com.example.project_board.entity.account.Member;
import com.example.project_board.service.account.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

//디스패처 서블릿이 컨트롤러를 찾기 위해서 @Controller라고 선언
@Controller
@RequestMapping(path = "/account")
public class MemberController {

    //return 타입이 String이유 : HTML 파일명을 찾기 위해

    //2022.8.18
    //MemberController 클래스가 실행되면 MemberService를 불러와서 주입 당하는 것
    //@Autowired를 사용해서 MemberController는 MemberService를 주입하겠다고 선언
    //Springboot는 MemberController가 실행하려면 MemberService가 필요하다고 인식한다.
    //장점1 : MemberController가 실행되는 시점에서 필요한 객체만 실행하여 절약이 가능함
    //장점2 : 이미 컨테이너에 있는 객체를 활용하여 최대한 인스턴스(객체)를 최소한으로 사용함

    @Autowired  //2022.8.18
                //[주입방식에는 3가지가 있다.-> 메소드, 생성자, 필드 (객체의 데이터)] 해당 어노테이션은 필드 주입 방식으로 되어있다.
                //<필드 주입방식>의 경우에는 2개 이상 주입할 시에 어떤게 던져 주입당하는지를 모른다.
                //주입 당하는 A와 B가 서로 주입 당할 경우에는 어떤 게 먼저 생성될지 모르는 문제가 발생한다.
                //일반 자바라면 실행한 클래스(main) 안에서 인스턴스를 만들어서 인스턴스 안에 있는 메소드를 실행(static:불러옴)
                //실행되는 클래스(main)이 먼저 존재하고 인스턴스를 후에 생성
                //MemberService라는 객체를 선언
                //필드 주입방식은 @AutoWrited를 통해 컨테이너에서 주입당함 (할당)
    private final MemberService memberService;
    //final은 변하지 않는 한 개.
    //왜 필요하냐? Controller가 돌아가면서 알 수 없는 에러를 통해서 코드가 꼬이게되면 동작이 안되기 때문에 final을 사용하여 바뀔 수 없게 만들어준다.
    //그러므로 final로 바뀌지 않는 Memberservice를 사용하여 MemberController는 안심하고 동작을 할 수 있다.

    //2022.8.18
    //<생성자 주입방식>은 아래 생성자에 @Autowired를 붙혀서 컨테이너에서 주입당함
    //MemberController클래스의 생성자를 선언
    //매개변수를 MemberService로 받아서 위에 있는 필드값 MemberService에 할당
    //장점 : 객체 생성 시점에서 생성자를 통해서 주입 받기 때문에 순서가 명확해진다.
    @Autowired
    protected MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //2022.08.12
    //(클라이언트가 두 분류) 게시판 : 사용자 관점,
    // 시스템 관리 관점(회원관리, 게시판 관리,컨텐츠 관리) [웹 솔루션을 관리하는 오너]
    //getAccountList : 전체 회원 목록 보기 : 웹 솔루션에서 웹 시스템을 관리하는 관리자를 위한 기능
    //public : 전부 공개
    //String : 이 메서드가 실행 완료되면 최종적으로 리턴하는 타입(HTML 파일명을 찾기 위해)

//    @GetMapping("/getAccountList")
//    public String getAccountList(Model model){
//        //model : 컨트롤러에서 작업한 결과물을 HTML에 전달학 위한 매개체
//        //addAttribute : key/value으로 데이터를 저장하는 메서드
//        //attributeName(key) : 뒤에 있는 value를 호출하기 위한 문자열(key)
//        //memberService.getMemberList() : @Autowired로 선언된 MemberService 클래스를 호출하여
//        //getMemberList() 메서드 실행
//        model.addAttribute("memberList", memberService.getMemberList());
//        return "/account/getAccountList";
//    }

    @GetMapping("/getAccountList")
    public String getAccountList(Model model){
        //model : 컨트롤러에서 작업한 결과물을 HTML에 전달학 위한 매개체
        //addAttribute : key/value으로 데이터를 저장하는 메서드
        //attributeName(key) : 뒤에 있는 value를 호출하기 위한 문자열(key)
        //memberService.getMemberList() : @Autowired로 선언된 MemberService 클래스를 호출하여
        //getMemberList() 메서드 실행
        model.addAttribute("memberList",
//                memberService.getMemberListEncodingByMemberList(
                        memberService.getMemberList());
        return "/account/getAccountList";
    }


    @GetMapping("/getAccount")
    public String getAccount(Model model, Member member){
        //model.addAttribute("memberList", memberService.getMember(member));
        model.addAttribute("member", memberService.getMember(member));
        return "/account/getAccount";
    }

    @PostMapping("/updateAccount")
    public String updateAccount (Member member){
        memberService.updateMember(member);
        return "redirect:/account/getAccountList";
    }

    @GetMapping("/deleteAccount")
    public String deleteAccount (Member member){
        memberService.deleteMember(member);
        return "redirect:/account/getAccountList";
    }


    //deleteAccount : 회원정보 삭제

    //updateAccount : 회원정보 수정

    //기존 데이터의 무결성 체크를 위한 데이터 전체 조회와 일부 수정 작업 (sql 특정 컬럼의 값을 모두 gmail.com > naver.com)
    //+백업 entity
    // 회원 정보가 일정 수치까지 다다르면(혹은 이벤트가 발생시) updateAccountAll라는 메서드를 통해
    //기존 entity의 테이블의 정보를 모두 백업 entity에 저장
    //CrudRepasitory를 보면 인터페이스 메서드 findALl 회원정보 모두 불러온 뒤에 SaveAll메서드로 저장

    //++회원정보 1개의 테이블에서 관리하지 않음 > 1년 지난 회원은 로그인을 안한 장기 휴식회원
    //++1년 미접속 계정은 따로 테이블에 옮겨서 저장 (예전 스타일)
    //날짜별로 1년이 지나면 새로 테이블을 생성해서 회원을 관리합니다. (회원 가입한 날짜 기준)
    // > 장점 : 최신 회원들을 관리하는 마케팅 부서에게 도움
    // > 장점 : DB 백업할 때도 테이블 파편화로 트랜젝션 위험 또는 시간 절약
    // > 단점 : Admin(관리자)는 모든 회원 정보를 볼 때 다수의 테이블을 동시에 봐야 하기 때문에
    //          JOIN을 써서 속도가 느림


    //2022.08.11
    @GetMapping("/insertAccount")
    public String insertAccountView(){
        return "/account/insertAccount";
    }

    //Member라는 매개변수로 controller에 전달
    //Member(Entity)이고 DTO(Data Transfer Object)
    //어디선가 받거나 만든 데이터를 객체로 만드는 것 : DTO
    @PostMapping("/insertAccount")
    public String insertAccountView(Member member){
        //클라이언트에서 ID/PW만 가져왔음, (시퀀스는 자동 생성)
        //createDate
        //updateDate 는 안가져옴 (필드값이 부족함)

        //2022.08.18 main에 EnableJpaAuditing을 선언해서 자동으로 가져올 수 있도록 하기 위해 날림
//        member.setCreateDate(new Date());
//        member.setUpdateDate(new Date());

        memberService.insertMember(member);
//        return "index";   2022.08.16 수정
        return "redirect:/account/getAccountList";
    }

    //22.08.16
    @GetMapping("/selectAccount")
    public String selectAccount(){
        return "/account/selectAccount";
    }

    @PostMapping("/selectAccount")
    public String resultAccount(Member member, Model model){
        //memberService.getMemberWhereIdOrEmail(member.getEmail(), member.getId());
        model.addAttribute("member",
                memberService.getMemberWhereIdOrEmail(member.getEmail(), member.getId()));
        return "/account/resultAccount";
    }
}
