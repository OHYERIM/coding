package com.example.project_board.entity.base;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.MappedSuperclass;
import javax.persistence.EntityListeners;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    private Date updateDate;
}
