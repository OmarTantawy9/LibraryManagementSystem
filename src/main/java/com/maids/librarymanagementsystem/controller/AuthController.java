package com.maids.librarymanagementsystem.controller;

import com.maids.librarymanagementsystem.security.payload.LoginRequest;
import com.maids.librarymanagementsystem.security.payload.SignUpRequest;
import com.maids.librarymanagementsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        String authResponse = authService.registerUser(signUpRequest);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseCookie jwtAuthCookie = authService.login(loginRequest);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        jwtAuthCookie.toString()
                )
                .body("User Logged in Successfully");
    }

    @PostMapping("/signout")
    public ResponseEntity<String> logoutUser() {
        ResponseCookie responseCookie = authService.logoutUser();
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        responseCookie.toString()
                )
                .body("User Logged Out Successfully");
    }


}
