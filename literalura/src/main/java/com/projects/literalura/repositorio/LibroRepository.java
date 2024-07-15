package com.projects.literalura.repositorio;

import com.projects.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libros,Long> {

    List<Libros> findByIdiomas(String idioma);
}
