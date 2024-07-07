package com.projects.literalura.repositorio;

import com.projects.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libros,Long> {
}
