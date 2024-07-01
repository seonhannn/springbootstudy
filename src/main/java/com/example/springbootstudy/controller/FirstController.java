package com.example.springbootstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    String niceToMeetYou(Model model) {
        model.addAttribute("username", "jiseon");
        return "greetings";
    }

    @GetMapping("/bye")
    String seeYouNext(Model model) {
        model.addAttribute("username", "jiseon");
        return "goodbye";
    }
}
