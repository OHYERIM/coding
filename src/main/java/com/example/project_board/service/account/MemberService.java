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

}

