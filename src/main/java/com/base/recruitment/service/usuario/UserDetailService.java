package com.base.recruitment.service.usuario;

import com.base.recruitment.model.Usuario;
import com.base.recruitment.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioRepository.findByUsername(username)
                .orElseThrow((() -> new UsernameNotFoundException("Usuario no registrado")));
        return new User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
    }
}
