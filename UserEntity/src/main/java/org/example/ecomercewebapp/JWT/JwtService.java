package org.example.ecomercewebapp.JWT;

import com.sun.source.doctree.TextTree;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = System.getenv("JWT_KEY_JAVA");
    public String generateToken(Map<String,Object> extraInfos , UserDetails userDetails){
        if(extraInfos == null){
            extraInfos = new HashMap<>();
        }
        if(userDetails instanceof org.springframework.security.core.userdetails.User){
            Collection<? extends GrantedAuthority> authorities = ((org.springframework.security.core.userdetails.User)userDetails).getAuthorities();
            for(GrantedAuthority authority:authorities){
                if(authority.getAuthority().startsWith("ROLE_")){
                    extraInfos.put("role",authority.getAuthority().substring(5));
                    break;
                }
            }
        }
        return Jwts.builder()
                .setClaims(extraInfos)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365)) // 1 year expiration
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public String getUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token , Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
