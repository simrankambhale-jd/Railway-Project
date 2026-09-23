package practice.irctc.IRCTC.Config.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtHelper {

    private static final long ACCESS_TOKEN_VALIDITY = 10 * 60 * 1000; // 10 minutes
    private static final long REFRESH_TOKEN_VALIDITY = 60 * 60 * 1000; // 30 minutes
    private static final String SECRET = "GITHUB_PLACEHOLDER_SECRET_DO_NOT_USE_IN_PRODUCTION_1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdef";
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "access_token");
        return buildToken(claims, userDetails.getUsername(), ACCESS_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "refresh_token");
        return buildToken(claims, userDetails.getUsername(), REFRESH_TOKEN_VALIDITY);
    }

    public boolean isRefreshToken(String token) {
        return getTokenType(token).equals("refresh_token");
    }

    public boolean isAccessToken(String token) {
        return getTokenType(token).equals("access_token");
    }

    private String getTokenType(String token) {
        Object tokenType = getClaims(token).get("token_type");
        return tokenType != null ? tokenType.toString() : "";
    }

    private String buildToken(Map<String, Object> claims, String subject, long validity) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key)
                .compact();
    }
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}