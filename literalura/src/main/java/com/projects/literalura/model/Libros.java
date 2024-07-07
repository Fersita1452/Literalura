package com.projects.literalura.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ElementCollection(targetClass =  DatosAutor.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "datos_autores", joinColumns = @JoinColumn(name = "autor_id"))
    @Column(name = "autor", nullable = false)
    private List<DatosAutor> autor;
    private  String idiomas;
    private  Double numeroDeDescargas;

    public Libros(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.autor = datosLibros.autor();
        this.idiomas = datosLibros.idiomas().get(0);
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    public Libros (){}

    @Override
    public String toString() {
        return "Título: '" + titulo + '\'' +
                ", Autor: " + autor +
                ", Idiomas: " + idiomas +
                ", Número de descargas: " + numeroDeDescargas;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<DatosAutor> getAutor() {
        return autor;
    }

    public void setAutor(List<DatosAutor> autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
