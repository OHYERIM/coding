package com.example.project_board.repository.customRepository;

import com.example.project_board.entity.customDto.CustomDtoExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomDtoExampleRepository extends JpaRepository<CustomDtoExample, String> {

    //nativeQuery를 쓰는 이유(장점)
    //1. Entity단위로 DB조회와 Client데이터 전송을 동시에 할 경우 table 구조가 드러나기 때문에 보안적인 위험 > DTO를 만들어서 데이터 전송에 쓰인다.
    //2. JPA Entity단위로 데이터를 조회할 경우 자유도가 현격히 떨어지므로 일반 DTO를 만들어서 JOIN등등 데이터 리턴값을 자유롭게 받을 수 있다.
    //단, 단점으로는 JPQL을 써서 데이터를 주고 받을 경우 객체 구조적인 ~ 단단함. 유지보수에 용이.
    //SQL보다는 JPQL + DTO를 쓰는 것이 가장 좋다고 생각하는 이유는 구조적인 견고함과 DTO의 유연함을 동시에 취하고 상황에 따라 대처할 수 있는 유연성을 가지기 때문
    @Query(value = "SELECT m.id AS input_id, b.writer AS input_writer, b.title AS input_title" +
                           "FROM Member m" +
                           "INNER JOIN Board b ON m.id = b.writer" +
                           "WHERE m.id = :memberId",
                    nativeQuery = true)
    CustomDtoExample findExample(String memberId);
}
