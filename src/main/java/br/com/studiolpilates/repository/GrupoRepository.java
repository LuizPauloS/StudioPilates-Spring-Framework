package br.com.studiolpilates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.studiolpilates.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}
