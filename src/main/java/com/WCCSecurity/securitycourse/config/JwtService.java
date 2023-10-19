package com.WCCSecurity.securitycourse.config;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;

@Service
public class JwtService {

    public SecretKey generateKey() {
        byte[] secretByte = Decoders.BASE64.decode("duezbfcziuevcbyibvdnbcdbhebhzjehzbjhfjzayvcdsibdxjakcbxjbazkdd");
        SecretKey key = Keys.hmacShaKeyFor(secretByte);
        return key;
    }

    public String generateToken(String username, String role) {
        String jwt = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .claim("role", role)
                .signWith(generateKey())
                .compact();
        return jwt;
    }

    public String getSubject(String token) {
        String subject = Jwts
                .parser()
                .verifyWith(generateKey())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
        return subject;
    }

    public String getRole(String token) {
        String role = Jwts
                .parser()
                .verifyWith(generateKey())
                .build().parseSignedClaims(token)
                .getPayload().get("role", String.class);
        return role;
    }

    public Date getExpirationDate(String token) {
        Date role = Jwts
                .parser()
                .verifyWith(generateKey())
                .build().parseSignedClaims(token)
                .getPayload().getExpiration();
        return role;
    }

    public Boolean isValid(String token) {
        Boolean isValid = getExpirationDate(token).after(new Date()) && getSubject(token).equals("usernameInDB");
        return isValid;
    }

    //
}
