//package com.security.learning.service;
//
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//
//@Service
//public class JWTTokenGeneration {
//
//    private final String secretKey = "MySecretKeyAbcradabaraEliteCoder@1312312$#fsfsafas";
//
//    /**
//     * @param userName -> Why is userName used here?
//     */
//    public String generateToken(String userName){
//        return Jwts.builder()
//                .setSubject(userName)
//                .setClaims(new HashMap<>())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
//                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public Key getSignedKey(){
//        return Keys.hmacShaKeyFor(secretKey.getBytes());
//    }
//}
