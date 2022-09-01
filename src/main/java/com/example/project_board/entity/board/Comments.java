package com.example.project_board.entity.board;

import com.example.project_board.entity.base.BaseTimeEntity;
import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.*;

@Getter
@Entity
public class Comments extends BaseTimeEntity {

    @Id
    private Long seq;

    private String comments;

    @Transient
    private Long board_seq;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "board_seq" ,referencedColumnName = "seq")
    private Board board;
}
