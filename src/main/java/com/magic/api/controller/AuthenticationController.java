package com.magic.api.controller;

import com.magic.api.records.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(),
                                                                        data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok(auth);

    }



}
