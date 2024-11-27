package com.maids.librarymanagementsystem.security.jwt;

import com.maids.librarymanagementsystem.security.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {

    @Value("${lms.secret_key}")
    private String jwtSecret;

    @Value("${lms.jwt_expiration_ms}")
    private int jwtExpirationMs;

    @Value("${lms.jwt_cookie_name}")
    private String jwtCookieName;


    public String getJwtFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        else {
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(User userDetails) {

        String jwt = generateTokenFromUsername(userDetails.getUsername());

        ResponseCookie responseCookie = ResponseCookie.from(jwtCookieName, jwt)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(false)
                .build();

        return responseCookie;

    }


    public ResponseCookie cleanJwtCookie(){
        return ResponseCookie.from(jwtCookieName, null)
                .path("/api")
                .build();
    }

    public String generateTokenFromUsername(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
