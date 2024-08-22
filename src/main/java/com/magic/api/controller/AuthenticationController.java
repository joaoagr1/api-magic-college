package com.magic.api.controller;

import com.magic.api.domain.User;
import com.magic.api.records.AuthenticationDTO;
import com.magic.api.records.AuthenticationResponseDTO;
import com.magic.api.records.RegisterDTO;
import com.magic.api.repository.UserRepository;
import com.magic.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new AuthenticationResponseDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {

        String encodedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.username(), encodedPassword, data.email());
        newUser.setRole("ROLE_USER"); // Ensure the role is set
        this.userRepository.save(newUser);
        return ResponseEntity.ok(data);

    }

}