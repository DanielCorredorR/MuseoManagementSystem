package service;

import model.Usuario;
import repository.UsuarioRepository;

public class AuthService {

    private UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario login(String username, String password) {

        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            return null;
        }

        if (!usuario.getPassword().equals(password)) {
            return null;
        }

        return usuario;
    }
}