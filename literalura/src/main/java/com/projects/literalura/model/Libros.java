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
    private  String idiomas;
    private  Double numeroDeDescargas;
    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Autores autores;

    public Libros(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.autores = new Autores(datosLibros.autor().get(0));
        this.idiomas = datosLibros.idiomas().get(0);
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    public Libros (){}

    @Override
    public String toString() {
        return "------------------------------------------------------------" + '\n' +
                "Título: " + titulo + '\n' +
                "Autor: " + autores + '\n' +
                "Idiomas: " + idiomas + '\n' +
                "Número de descargas: " + numeroDeDescargas + '\n' +
                "------------------------------------------------------------";
    }

    public Autores getAutores() {
        return autores;
    }

    public void setAutores(Autores autores) {
        this.autores = autores;
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
