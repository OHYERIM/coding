package com.example.project_board.service.textTransfer;

import org.springframework.stereotype.Service;

@Service
public class TextTransfer {

    public String transferText3Word(String text) throws Exception {

        //java 문자열치환 내장메소드: split, subString..
        String wordFirst3 = text.substring(0,3);
        System.out.println("앞의 3글자 = " + wordFirst3);
        String wordLast = text.substring(4,text.length());
        System.out.println("뒤의 나머지 글자 =" +wordLast);
        //subString과 split 구조 차이
        //subString: 원본 배열을 참조해서 순번과 갈이 가지고 자름(객체를 따로 생성해서 관리X)
        //split: 새로운 인스턴스를 만들어서 문자열을 자르고, 더불어 결과값을 String배열로 받아옴

        //replaceAll 메소드의 변결할 값에 "."을 쓰면 모든 문자를ㄹ 지정
        wordLast = wordLast.replaceAll(".","*");
        System.out.println(wordFirst3);
        System.out.println(wordLast);

        return wordFirst3+wordLast;
    }
}
