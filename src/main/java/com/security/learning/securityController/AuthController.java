package com.security.learning.securityController;

import com.jarvis.dto.AuthRequest;
import com.jarvis.entity.securityLearning.User;
import com.jarvis.security.JwtUtil;
import com.jarvis.service.securityService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

public class AuthController {
    @Autowired
    private AuthenticationManager
            authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(
            @RequestBody AuthRequest request) {

        Authentication auth =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        if(auth.isAuthenticated()) {

            return jwtUtil.generateToken(
                    request.getUsername()
            );
        }

        return "Invalid Credentials";
    }
}
