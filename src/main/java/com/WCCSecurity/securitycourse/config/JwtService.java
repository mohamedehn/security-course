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

    //génération de notre token sur la base du username et du role
    // on récupère ensuite le contenu via d'autre méthode
    public String generateToken(String username, String role) {
        String jwt = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // date d'expiration du token
                .claim("role", role)
                .signWith(generateKey())
                .compact();
        return jwt;
    }

    //Jeton JWT en entrée, vérifié en utilisant la secretKey, puis extrait le subject
    public String getSubject(String token) {
        String subject = Jwts
                .parser()
                .verifyWith(generateKey())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
        return subject;
    }

    //idem que getSubject mais pour le role
    public String getRole(String token) {
        String role = Jwts
                .parser()
                .verifyWith(generateKey())
                .build().parseSignedClaims(token)
                .getPayload().get("role", String.class);
        return role;
    }

    //idem pour la date d'expiration
    public Date getExpirationDate(String token) {
        Date role = Jwts
                .parser()
                .verifyWith(generateKey())
                .build().parseSignedClaims(token)
                .getPayload().getExpiration();
        return role;
    }

    //vérifie la date d'expiration du token et le username
    public Boolean isValid(String token, String username) {
        Boolean isValid = getExpirationDate(token).after(new Date()) && getSubject(token).equals(username);
        return isValid;
    }

    //
}
