package co.edu.iudigital.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.iudigital.app.model.Caso;

public interface ICasoRepository extends JpaRepository<Caso, Long>{

}
