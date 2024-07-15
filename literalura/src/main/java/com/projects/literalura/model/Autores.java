package com.projects.literalura.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeFallecimiento;
//    @OneToMany (mappedBy = "autores", cascade = CascadeType.ALL)
//    private List<Libros> libros;

    public Autores () {}

    public Autores (DatosAutores datosAutores){
        this.nombre = datosAutores.nombre();
        this.fechaDeNacimiento = datosAutores.fechaDeNacimiento();
        this.fechaDeFallecimiento = datosAutores.fechaDeFallecimiento();
    }

    @Override
    public String toString() {
        return  '\n' + "   Nombre: " +  nombre + '\n' +
                "   Fecha de nacimiento: " + fechaDeNacimiento + '\n' +
                "   Fecha de fallecimiento: " + fechaDeFallecimiento;
    }

//    public List<Libros> getLibros() {
//        return libros;
//    }
//
//    public void setLibros(List<Libros> libros) {
//        this.libros = libros;
//    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }
}
