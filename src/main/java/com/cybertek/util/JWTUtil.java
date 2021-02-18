package com.cybertek.util;

import com.cybertek.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {


   // @Value("${security.jwt.secret-key}")
    private String secret="cybertek";





    private Claims extractAllClaims(String token){

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private <T> T extraClaim(String token, Function<Claims,T> claimsResolver){

        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);

    }
    public String extractUsername(String token){


        return extraClaim(token,Claims::getSubject);
    }

    public Date  extractExpiration(String token){

        return extraClaim(token,Claims::getExpiration);

    }
    private Boolean isTokenExpired(String token) {


    return extractExpiration(token).before(new Date());

    }

    public String generateToken(User user){

        // payload = claims
        Map<String,Object> claims=new HashMap<>();
        claims.put("username",user.getUserName());
        claims.put("email",user.getEmail());

        return createToken(claims,user.getUserName());


    }

    private String createToken(Map<String, Object> claims, String username) {



        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)  // this part comes from Security Service it can ID most common one
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //When it is created
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*60*10)) // 10 hours validity
                //When it is expired
                .signWith(SignatureAlgorithm.HS256,secret).compact();
                // Secret word can change according to company
    }

    public Boolean validateToken(String token,UserDetails userDetails){

        final String username=extractUsername(token);
        // userDetails.getUsername()
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }




}
