package com.example.project_board.entity.base;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseTimeEntity {


    @CreatedDate
    private Date createDate;


    @LastModifiedDate
    private Date updateDate;
}
