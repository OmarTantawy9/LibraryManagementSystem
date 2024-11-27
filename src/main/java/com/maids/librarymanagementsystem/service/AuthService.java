package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.security.payload.LoginRequest;
import com.maids.librarymanagementsystem.security.payload.SignUpRequest;
import org.springframework.http.ResponseCookie;

public interface AuthService {
    String registerUser(SignUpRequest signUpRequest);

    ResponseCookie login(LoginRequest loginRequest);

    ResponseCookie logoutUser();
}
