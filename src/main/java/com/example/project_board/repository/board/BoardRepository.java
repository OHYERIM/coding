package com.example.project_board.repository.board;

import com.example.project_board.entity.board.Board;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//CrudRepository를 상속받음
//CrudRepository : Entity 를 매개체로 create, read, update, delete 기능을 하는 인터페이스
//CrudRepository<Board, Long>의 매개변수 Board(Entity)와 Long(PK기본키의 타입)을 선언
//JPA가 어떤 객체로 어떤 타입으로 찾아야하는지 알아 들음
public interface BoardRepository extends JpaRepository<Board, Long> {

    //2022.08.22
    //(현업내용) 튜닝: JOIN과 WHERE절의 순서를 정함으로써 SELECT속도 튜닝을 어떻게 할지 전략적으로 구성.아래의 순서 1>2>[3>4]
    //          Member의 튜플이 무척 많을 경우 WHERE절을 통해 ID검색 이후 Board와 JOIN하는 것이 DB 검색 속도에 유리하다.(컴퓨터 공학 관련 공부내용)
    @Query(value = "SELECT b FROM Board b INNER JOIN Member m On b.writer = m.id WHERE m.id = :memberId")
    //INNER JOIN: ANSI QUERY이다. ORACLE QUERY와 다르다.
    // Board의 튜플을 가져와야하기 때문에 FROM Board(Board 테이블이 기준)
    //SELECT b FROM Board b -> Board테이블의 튜플을 검색하겠다(모든 컬럼) (1)
    // INNER JOIN Member m -> Member테이블과의 교집합 조인(INNER JOIN)하겠다 (2)
    // On b.writer = m.id  -> board의 writer과 member의 id가 동일한 튜플을 검색하겠다(b는 board의 별칭, m은 member의 별칭)(3)
    // WHERE m.id = :memberId -> INNER JOIN한 튜플들의 결과물 중에 member.id가 매개변수 memberID와 동일한 조건을 걸겠다.(4)

    //2022.08.22
    List<Board> findAllByMemberIdEqualsBoardWriter(String memberId);
    //회원 id를 검색하면 (writer의 ID가 동일) 관련된 writer의 게시글 모두를 출력 받아 리턴
}