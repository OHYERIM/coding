package com.example.project_board.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitController {

    private static final String main = "index";

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("introduce", "hello!!");
        return "init";
    }
}
