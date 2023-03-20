package br.com.ada.security.services.imp;

import br.com.ada.security.services.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String createJwtToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = now.plusMinutes(30);
        Date issuedAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date expiresAt = Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder().setSubject(username).setIssuedAt(issuedAt).setExpiration(expiresAt)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUsername(String token) {
        token = token.replace("Bearer ", "");
        JwtParser parser = Jwts.parserBuilder().setSigningKey(getSignInKey()).build();
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}