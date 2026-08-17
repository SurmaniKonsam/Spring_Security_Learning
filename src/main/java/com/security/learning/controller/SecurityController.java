package com.security.learning.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.security.learning.entity.Users;
import com.security.learning.repository.UsersRepository;
import com.security.learning.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class SecurityController {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserService userService;

    public record RegisterResponse(@JsonProperty("Message") String message, @JsonProperty("Users") Users users){}

    @PostMapping("/registerUser")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody Users users){
        try{
            userService.saveUser(users);
            return ResponseEntity.status(HttpStatus.OK).
                    body(new RegisterResponse("User registered successfully",users));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new RegisterResponse("User not reqistered",null));
        }
    }

    @GetMapping("/hello")
    public String greet(){
        return "Hello world";
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }

    //Now, our available resources.
    @GetMapping("/conquer")
    public String conquer(){
        return "You have conquered basic authentication";
    }


}
