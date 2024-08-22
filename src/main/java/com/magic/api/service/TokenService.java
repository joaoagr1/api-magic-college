package com.magic.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.magic.api.domain.User;
import org.springframework.beans.factory.annotation.Value;

public class TokenService {

    @Value("{jwt.secret}")
    private String secret;

    public String generateToken(User user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withClaim("username", user.getUsername())
                    .withClaim("email", user.getEmail())
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "token";
    }

}
