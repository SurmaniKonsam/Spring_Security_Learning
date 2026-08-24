package com.security.learning.security.jwtfilterconfiguration;


import com.nimbusds.jose.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.security.Key;
import java.security.Signature;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtToken {

    private final String mySecretKey = "7f3c9a2e6d1b8f405c7e2a91d4f6380b9e5c1a7d3f2b8e604c9a1d5f7b3e6c20";

    //It also means,i can call method inside a method.
    public String generateToken(String username){
        /**
         * Testing token with only 2 minutes, time limit.
         */
        Date expiry = new Date(System.currentTimeMillis() + 1000L*60*3);
        return Jwts.builder()
               .setSubject(username)
               .setClaims(new HashMap<>())
               .setIssuedAt(new Date())
               .setExpiration(expiry)
               .signWith(getSignedKey(),SignatureAlgorithm.HS256)
               .compact();

    }

    private Key getSignedKey(){
        return Keys.hmacShaKeyFor(mySecretKey.getBytes());
    }

    public Claims verifySignatureAndExtractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return verifySignatureAndExtractClaims(token).getSubject();
    }


    public Date getExpiration(String token){
        return verifySignatureAndExtractClaims(token).getExpiration();
    }


    public boolean isTokenExpired(String token){
        //So Date always gives the current time.
        return getExpiration(token).before(new Date());
    }





}
