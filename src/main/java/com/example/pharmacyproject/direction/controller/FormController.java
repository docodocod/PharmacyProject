package com.example.pharmacyproject.direction.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class FormController {

    @GetMapping("/")
    public String main() {
        return "main";
    };
};
