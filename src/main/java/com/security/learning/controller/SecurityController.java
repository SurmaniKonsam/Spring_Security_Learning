package com.security.learning.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.security.learning.entity.AuthBody;
import com.security.learning.entity.Users;
import com.security.learning.repository.UsersRepository;
import com.security.learning.security.jwtfilterconfiguration.JwtToken;
import com.security.learning.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
class SecurityController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtToken jwtTokenGeneration;

    @Autowired
    private AuthenticationManager authenticationManager;

    public record RegisterResponse(@JsonProperty("Message") String message){}

    @PostMapping("/registerUser")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody Users users){
        try{
            userService.saveUser(users);
            return ResponseEntity.status(HttpStatus.OK).
                    body(new RegisterResponse("User registered successfully"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new RegisterResponse("User not registered"));
        }
    }

    @GetMapping("/hello")
    public String greet(){
        System.out.println("🔥 Greet controller executed");
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

    //JWT token is to be generated only if the user is authenticated.
    /*
        Therefore, we are using AuthenticateManager
        Therefore, we are using auth request
        Therefore, we are using UserNameAndPasswordAuthentication
        PostMapping -> have a request body, that's the basic concept i know by now.
        Time to make note.
     */
    @PostMapping("/authenticate")
    public String generateJwtToken(@RequestBody AuthBody authBody){
        //System.out.println("hello world");
        Authentication authenticate = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                        authBody.getUserName(),
                        authBody.getPassword()));
        System.out.println("is authenticated : "+authenticate.isAuthenticated());
        if(authenticate.isAuthenticated()){
            //return jwtTokenGeneration
            return jwtTokenGeneration.generateToken(authBody.getUserName());
        }
        return null;
    }






}