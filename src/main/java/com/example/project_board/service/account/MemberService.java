package com.example.project_board.service.account;

import com.example.project_board.entity.account.Member;

import java.util.List;

public interface MemberService {

    Member getMemberWhereIdOrEmail(String Email, String Id);
    Member getMemberWhereIdAndROWNUL1(String id);
    //멤버 타입으로 리스트를 만들어서 리턴하겠다.

    List<Member> getMemberList();
    //이게 없었음
    void insertMember(Member member);

    Member getMember(Member member);

    void updateMember(Member member);

    void deleteMember(Member member);

    //일부분만 검색하여 사용유저 찾기 위한 조건
    //결과값: 입력받은 정보(email, id, pw)의 유사사실유무를 확인 후 비밀번호 변경(updateMember의 password)
    boolean booleanSearchUserByEmail(Member member);
    boolean booleanSearchUserById(Member member);
    boolean booleanSearchUserByPassword(Member member);

    //보안을 위한 ***별표처리 MemberList (replace..)
    List<Member> getMemberListEmailSecurityStarByMemberList(List<Member> memberList);

    //민감데이터(SHA256..)
    List<Member> getMemberListEncodingByMemberList(List<Member> memberList);

    //30일 지난 회원에게 변경 페이지 안내 [1조]
    boolean booleanAfter30DaysChangePasswordByMemberUpdateDate(Member member);

    //비밀번호 변경 테이블 생성 후 변경한 기록을 남긴 뒤, 변경 내용 최신 3회 내용과 비교
    boolean booleanChangedPassword3CheckByMemberPassword(Member member);
}

