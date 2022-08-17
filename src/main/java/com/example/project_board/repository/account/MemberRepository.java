package com.example.project_board.repository.account;

import com.example.project_board.entity.account.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//MemberRepository는 CrudRepository를 상속받아 기능을 온전히 씀
//CrudRepository : JPA를 통해 DB에 기본적인 SQL문을 통해 소통(INSERT INFO, SELECT, UPDATE, DELETE)

public interface MemberRepository extends JpaRepository<Member, Long> {

    //2022.08.16
    //Return 내용선언, find~변수명에 맞춰서 메서드 생성, 필요한 매개변수
    @Query(value = "select m from Member m where m.email = :email_1 or m.id = :id_1")
    //m 은 Member의 별칭. * 대신에 별칭을 쓴다.
//    List<Member> findMembersByEmailOrId(String email_1, String Id);
    Member findMembersByEmailOrId(String email_1, String id_1);
    //레포지토리가 Member에 연결되어 있어서 Members 안써도 됨

    //(Id는 중복 가능한 구조애서) Id값의 일부분만 매개변수로 넣고, 아이디 생성날짜가 가장 최신인 것
    @Query(value = "select m from Member m where m.id = :id_1 order by m.createDate DESC")
    Member findFirstById(String id_1);


//    List<Member> findMemberWhereIdAndROWNUL1(String id);
}
