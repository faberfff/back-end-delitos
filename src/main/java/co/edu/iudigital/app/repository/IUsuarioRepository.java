package co.edu.iudigital.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.iudigital.app.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

}
