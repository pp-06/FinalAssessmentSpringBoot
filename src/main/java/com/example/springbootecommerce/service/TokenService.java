package com.example.springbootecommerce.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class TokenService {
    public static final String token_secret = "pp12asfappsddisdbkdsvj";

    // Creating a token
    public String createToken(ObjectId userId) {
        try{
            Algorithm algo = Algorithm.HMAC256(token_secret);
            String token = JWT.
                    create().
                    withClaim("userId",userId.toString()).
                    withClaim("createdAt", new Date()).
                    sign(algo);
            return token;
        }catch(UnsupportedEncodingException |JWTCreationException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Decoding a token
    public String getUserIdToken(String token) {
        try {
            Algorithm algo = Algorithm.HMAC256(token_secret);
            JWTVerifier jwtVerifier = JWT.require(algo).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return decodedJWT.getClaim("userId").asString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Token valid
    public boolean isTokenValid(String token) {
        String userId = this.getUserIdToken(token);
        return userId != null;
    }
}
