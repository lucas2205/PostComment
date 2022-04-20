

package com.project2.project2.security;

import com.project2.project2.dao.UsuarioDao;
import com.project2.project2.entity.Rol;
import com.project2.project2.entity.Usuario;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService{
    
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       
        Usuario usuario = usuarioDao.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese username o email"));
        
        return new User(usuario.getEmail(),usuario.getPassword(),mapearRoles(usuario.getRoles()));
    
    }
    
    private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles){
        
        return roles.stream().map(rol-> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
    }

}
