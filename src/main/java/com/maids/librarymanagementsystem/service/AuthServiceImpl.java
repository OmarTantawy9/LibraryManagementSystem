package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.exception.APIException;
import com.maids.librarymanagementsystem.security.jwt.JWTUtils;
import com.maids.librarymanagementsystem.security.model.Role;
import com.maids.librarymanagementsystem.security.model.User;
import com.maids.librarymanagementsystem.security.payload.LoginRequest;
import com.maids.librarymanagementsystem.security.payload.SignUpRequest;
import com.maids.librarymanagementsystem.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final JWTUtils jwtUtils;

    private final AuthenticationManager authManager;

    private final PasswordEncoder passwordEncoder;


    @Override
    public String registerUser(SignUpRequest signUpRequest) {

        if(userRepository.existsByUsername(signUpRequest.getUsername())){
            throw new APIException("User with username " + signUpRequest.getUsername() + " already exists");
        }

        User user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(
                        signUpRequest.getRole()
                                .equalsIgnoreCase("admin")
                                ? Role.ADMIN_ROLE
                                : Role.USER_ROLE
                )
                .build();

        userRepository.save(user);

        return "You have been Registered";

    }

    @Override
    public ResponseCookie login(LoginRequest loginRequest) {

        Authentication authentication;

        try{
            authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new APIException("Incorrect Username or Password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userDetails = (User) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        return jwtCookie;

    }

    @Override
    public ResponseCookie logoutUser() {
        return jwtUtils.cleanJwtCookie();
    }
}
