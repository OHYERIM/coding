package com.example.project_board.entity.account;

import com.example.project_board.entity.base.BaseTimeEntity;
import com.example.project_board.entity.board.Board;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

//@Entity JPA가 이 객체를 기준으로 table을 만들어야 한다고 선언
@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity implements Serializable { //Serializable라는 구현체를 사용하는 이유는?.. @OneToMany를 사용하기 위해서는 구현체가 필요 (@OneToMany에서 백엔드를 저장하기 위해)

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

    //2022.08.22
    //table끼리 조언을 하는 조건
    //1. Member.id는 member의 튜플마다 유일한 값이다.(유니크 키)
    //  member마다 게시글(board)를 쓸 수 있다는 조건이 있으므로 board입장에서는 member의 id가 유일해야 식별이 가능.
    //2. null처리 (null이 들어가게 되면 board는 Id를 식별할 수 없다.)-> 'nullable=false'
    @Column(length = 40,nullable = false, unique = true)
    private String id;

    //2022.08.22
    //member는 여러개의 board를 가질 수 있다고 선언하는 것
    //필드값으로 board를 가지고 있다고 필드에 넣어줌(JPA는 이 필드내용으로 테이블 연관관계(join)으로 식별)
    //@OneToMany는 member 1튜플마다 여러개의 board를 가진다는 속성을 선언과 다수 엔티티 연동에 Springboot는 Serializable 상속을 요구한다.
    @OneToMany(mappedBy = "member") //일대다를 의미하는 어노테이션
    private List<Board> boardList = new ArrayList<>();

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