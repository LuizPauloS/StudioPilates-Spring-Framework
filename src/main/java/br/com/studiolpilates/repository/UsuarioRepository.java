package br.com.studiolpilates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.studiolpilates.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsernameAndPassword(String username, String password);
}
