package com.security.learning.security.jwtfilterconfiguration;


import com.nimbusds.jose.Algorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;


public class JwtToken {

    //It also means,i can call method inside a method.
    public String generateToken(String username){
        System.out.println("inside generate token");
        return Jwts.builder()
                        .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
                .addClaims(new HashMap<>())
                .signWith(this.getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignedKey(){
        String mySecretKey = "SecretAbracadabraDeathlySpell@12312";
        return Keys.hmacShaKeyFor(mySecretKey.getBytes());
    }
}
