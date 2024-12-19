package com.example.Teacher_portal.jwt;

import com.example.Teacher_portal.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtProvider {
    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());


    public String generateToken(Authentication auth) {

        List<String> roles = getRolesFromUser(auth);


        String jwt = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 84600000))
                .claim("email", auth.getName())
                .claim("roles", roles)  // Add roles as a claim
                //	.claim("userId", auth.get)
                .signWith(key).compact();

        return jwt;
    }


    public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String email = String.valueOf(claims.get("email"));

        return email;

    }

    private List<String> getRolesFromUser(Authentication authentication) {

        return authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority().replace("ROLE_", ""))  // Removing "ROLE_" prefix if present
                .collect(Collectors.toList());
    }


}
