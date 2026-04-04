package edu.eci.dosw.segundo_parcial.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.eci.dosw.segundo_parcial.entity.UserEntity;
import edu.eci.dosw.segundo_parcial.repository.UserRepository;

/**
 * Conecta los usuarios de la base de datos con Spring Security.
 *
 * Si cambias la entidad de usuario, aqui debes adaptar el mapeo de roles y credenciales.
 */
@Service
public class ApiUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApiUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Busca un usuario por correo para autenticarlo.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username.trim().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
