package repository;

import java.util.ArrayList;
import java.util.List;
import model.Rol;
import model.Usuario;

public class UsuarioRepository {

    private List<Usuario> usuarios;

    public UsuarioRepository() {

        usuarios = new ArrayList<>();

        // Usuarios precargados
        usuarios.add(new Usuario("director", "1234", Rol.DIRECTOR));
        usuarios.add(new Usuario("restaurador", "1234", Rol.RESTAURADOR));
        usuarios.add(new Usuario("catalogador", "1234", Rol.CATALOGADOR));
        usuarios.add(new Usuario("visitante", "1234", Rol.VISITANTE));
    }

    public Usuario findByUsername(String username) {

        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }

        return null;
    }
}