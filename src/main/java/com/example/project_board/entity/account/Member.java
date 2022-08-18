package com.example.project_board.entity.account;

import com.example.project_board.entity.base.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

//@Entity JPA가 이 객체를 기준으로 table을 만들어야 한다고 선언
@ToString
@Entity
@Getter
@Setter // 이거때문에 문제 생김
public class Member extends BaseTimeEntity {

    //SELECT [*컬럼명=객체의 필드] FROM TABLE_NAME=객체;
    //CREATE TABLE (
    //        seq NUMBER primary key,
    //        id varchar2(40) NOT NULL
    //)
    //JPA : 객체에 맞춰서 SQL문으로 바꿔주는 것(번역)
    //@Id : table을 만들 떄, 테이블의 튜플(row)를 식별할 수 있는 기본키
    @Id
    @GeneratedValue
    @Column
    private Long seq;

    @Column(length = 40,nullable = false)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    //2022.08.18 BaseTimeEntity라는 추상클래스에서 상속을 받아 사용하기 위해 날린 것.
//    @Setter
//    @Temporal(TemporalType.DATE)
//    private Date createDate;
//
//    @Setter
//    @Temporal(TemporalType.DATE)
//    private Date updateDate;

}