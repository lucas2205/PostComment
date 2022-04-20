

package com.project2.project2.security;

import com.project2.project2.exceptions.AppException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    
    @Value("{app.jwt-secret}")
    private String jwtSecret;
    
    @Value("{app.jwt-expiration-milliseconds}")
    private int jwtExpiration;  
    
    
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        
        Date fechaActual = new Date();
        Date fechaExpiration = new Date(fechaActual.getTime() + jwtExpiration);
        
        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiration)
                        .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        
        return token;     
                
    }
    
    
    public String obtenerUsernameDelJwt(String token){
        
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        
        return claims.getSubject();
    }
    
    public boolean validarToken(String token){
        
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch(SignatureException ex){
            throw new AppException(HttpStatus.BAD_REQUEST,"Firma JWT no valida");
        }catch(MalformedJwtException ex){
            throw new AppException(HttpStatus.BAD_REQUEST,"Token JWT no valida");
        }catch(ExpiredJwtException ex){
            throw new AppException(HttpStatus.BAD_REQUEST,"Token JWT caducado");
        }catch(UnsupportedJwtException ex){
            throw new AppException(HttpStatus.BAD_REQUEST,"Token JWT no compatible");
        }catch(IllegalArgumentException ex){
            throw new AppException(HttpStatus.BAD_REQUEST,"Cadena Claims esta vacia");
        }
        
    }

}
