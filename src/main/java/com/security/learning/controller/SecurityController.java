package com.security.learning.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String greetHello(){
        return "Hello World";
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrf(HttpServletRequest servletRequest){
        return (CsrfToken) servletRequest.getAttribute(CsrfToken.class.getName());
    }

    @PostMapping("/debit")
    public String debitMoney(){
        return "Money Debited";
    }

}
