package com.projects.literalura.principal;

import com.projects.literalura.model.Datos;
import com.projects.literalura.model.Autores;
import com.projects.literalura.model.DatosLibros;
import com.projects.literalura.model.Libros;
import com.projects.literalura.repositorio.AutoresRepository;
import com.projects.literalura.repositorio.LibroRepository;
import com.projects.literalura.service.ConsumoAPI;
import com.projects.literalura.service.ConvierteDatos;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Stream;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private LibroRepository repositorioLibros;
    private AutoresRepository repositorioAutores;
    private List<Libros> libros;
    private List<Autores> autores;
    private String idiomas;

    public Principal(LibroRepository repository, AutoresRepository autoresRepository) {
        this.repositorioLibros = repository;
        this.repositorioAutores = autoresRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en un determinado año
                    5 - Mostrar libros por idioma
                    0 - Salir""";
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    try {
                        buscarLibroPorTitulo();
                    } catch (DataIntegrityViolationException e) {
                        System.out.println("Libro guardado previamente");
                    } catch (IndexOutOfBoundsException e){
                        System.out.println("Dato inválido, intenta de nuevo.");
                    }
                    break;
                case 2:
                    mostrarLibrosGuardados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivos();
                    break;
                case 5:
                   buscarLibrosPorIdioma();
                   break;
                case 0:
                    System.out.println("Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida, vuelva a intentar");
                    break;

            }
        }
    }


    private DatosLibros getDatosLibros() {
        System.out.println("Escribe el nombre del libro que desea buscar");
        //Busca datos generales del libro
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
//        System.out.println(json);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        DatosLibros datosLib = datos.resultados().get(0);
        return datosLib;
    }

    @Transactional
    private void buscarLibroPorTitulo() throws DataIntegrityViolationException{
        DatosLibros datos = getDatosLibros();
        Libros libros = new Libros(datos);
        System.out.println(libros);
        repositorioLibros.save(libros);
        System.out.println("Información guardada con éxito! " + datos);
    }

    @Transactional
    private void mostrarLibrosGuardados() {
        libros = repositorioLibros.findAll();
        libros.stream().forEach(System.out::println);
    }

    @Transactional
    private void mostrarAutoresRegistrados() {
        autores = repositorioAutores.findAll();
        autores.stream().forEach(System.out::println);
    }

    @Transactional
    private void mostrarAutoresVivos() {
        autores = repositorioAutores.findAll();
        System.out.println("A partir de qué año buscas el autor vivo?");
        int dato1 = Integer.valueOf(teclado.nextLine());
        System.out.println("Hasta qué año buscas el autor vivo?");
        int dato2 = Integer.valueOf(teclado.nextLine());
        List<Autores> autoresFiltrados = autores.stream().filter(a -> !(Integer.valueOf(a.getFechaDeNacimiento())>dato2 || Integer.valueOf(a.getFechaDeFallecimiento())<dato1)).toList();
        if (autoresFiltrados.isEmpty()){
            System.out.println("Aún no hay autores registrados entre esa fecha");
        } else {
            autoresFiltrados.forEach(System.out::println);
        }

    }

    @Transactional
    private void buscarLibrosPorIdioma(){
        System.out.println("""
                Escribe el idioma que deseas buscar:
                es = español
                en = inglés""");
        String idioma = teclado.nextLine();
        List<Libros> librosPorIdioma = repositorioLibros.findByIdiomas(idioma);
        System.out.println(librosPorIdioma);
    }

}


