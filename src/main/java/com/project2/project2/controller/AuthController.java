

package com.project2.project2.controller;

import com.project2.project2.dao.RolDao;
import com.project2.project2.dao.UsuarioDao;
import com.project2.project2.dto.LoginDto;
import com.project2.project2.dto.RegisterDto;
import com.project2.project2.entity.Rol;
import com.project2.project2.entity.Usuario;
import com.project2.project2.security.JwtAuthResponseDto;
import com.project2.project2.security.JwtTokenProvider;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UsuarioDao UsuarioDao;
    
    @Autowired
    private RolDao rolDao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> authenticateUser(@RequestBody LoginDto loginDto) {
        
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        //OBTENEMOS EL TOKEN DEL JWTTOKENPROVIDER
        String token = jwtTokenProvider.generateToken(authentication);
        
        return ResponseEntity.ok(new JwtAuthResponseDto(token));
        
    
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody RegisterDto registerDto){
        
        if(UsuarioDao.existsByUsername(registerDto.getUsername())){
            
            return new ResponseEntity<>("Ese usuario ya existe",HttpStatus.BAD_REQUEST);
        }
        
         if(UsuarioDao.existsByEmail(registerDto.getEmail())){
            
            return new ResponseEntity<>("Ese Email ya existe",HttpStatus.BAD_REQUEST);
        }
        
    Usuario usuario = new Usuario();
    usuario.setNombre(registerDto.getNombre());
    usuario.setUsername(registerDto.getUsername());
    usuario.setEmail(registerDto.getEmail());
    usuario.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    
    Rol roles = rolDao.findByNombre("ROLE_ADMIN").get();
    usuario.setRoles(Collections.singleton(roles));
    
    UsuarioDao.save(usuario);
    
    return new ResponseEntity<>("Usuario Registrado Correctamente",HttpStatus.OK);
    
    }
    
    
    
    

}
