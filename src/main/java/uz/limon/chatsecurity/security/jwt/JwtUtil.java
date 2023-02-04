package uz.limon.chatsecurity.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secret;

    public String generateToken(String subject){
        return  Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secret)
                .setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 60 * 1000))
                .setSubject(subject)
                .setIssuedAt(Date.valueOf(LocalDate.now()))
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()//parser
                .parseClaimsJws(token)
                .getBody();
    }

    public String validateTokenAndGetSubject(String token) {
        try {
            return getClaims(token).getSubject();
        }catch (ExpiredJwtException e){
            log.error("Token expired: " + token);
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
