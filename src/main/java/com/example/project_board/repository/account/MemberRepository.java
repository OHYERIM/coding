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

    @Query(value = "SELECT m FROM Member m JOIN fetch m.boardList WHERE m.id = :memberId")
    List<Member> findAllByMemberIdEqualsBoardWriter(String memberId);

    //JPA는 메소드 이름으로 DB에 조회하는 기능
    //JPQL: JPA를 통해 JPA에서 제공하는 쿼리문으로 조회 (단, ENtity 기준으로만 조회 가능)
    //NativeQuery: 일반 SQL문으로 DB를 조회하며 보통 DTO단위로 리턴(ENtity단위로 리턴X)
    //jpql containing (SQL문의 like처럼 유사한 단어를 찾는 메소드명)

    List<Member> findByEmailContaining(String email);
    //*findallby로 받을 경우에는 list로 써야한다. 여러개로 받아야하기 때문에

    //jpql contains

}
