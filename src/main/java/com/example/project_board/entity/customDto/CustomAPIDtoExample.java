package com.example.project_board.entity.customDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
public class CustomAPIDtoExample {

    private String title;
    private String content;
}
