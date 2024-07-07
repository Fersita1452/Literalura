package com.projects.literalura.principal;

import com.projects.literalura.model.Datos;
import com.projects.literalura.model.DatosLibros;
import com.projects.literalura.model.Libros;
import com.projects.literalura.repositorio.LibroRepository;
import com.projects.literalura.service.ConsumoAPI;
import com.projects.literalura.service.ConvierteDatos;
import jakarta.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private LibroRepository repositorio;

    public Principal(LibroRepository repository) {
        this.repositorio = repository;
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
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
//                case 3:
//                        mostrarAutoresRegistrados();
//                        break;
//                    case 4:
//                        mostrarAutoresVivos();
//                        break;
//                    case 5:
//                        buscarLibrosPorIdioma();
//                        break;
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
        System.out.println(json);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        DatosLibros datosLib = datos.resultados().get(0);
        return datosLib;
    }

    @Transactional
    private void buscarLibroPorTitulo() {
        DatosLibros datos = getDatosLibros();
        Libros libros = new Libros(datos);
        System.out.println(libros);
        repositorio.save(libros);
        System.out.println(datos);
    }

    private void mostrarLibrosBuscados() {
        List<Libros> libros = repositorio.findAll();
        libros.stream().forEach(System.out::println);
    }

    //Top 10 libros mas descargados
//        System.out.println("Top 10 libros mas descargados");
//        datos.resultados().stream()
//                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
//                .limit(10)
//                .map(l -> l.titulo().toUpperCase())
//                .forEach(System.out::println);

    //busqueda de libros por nombre
//        System.out.println("Ingrese el nombre del libro que desea buscar");
//        var tituloLibro = teclado.nextLine();
//        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
//        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
//        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
//                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
//                .findFirst();
//        if (libroBuscado.isPresent()) {
//            System.out.println("Libro encontrado");
//            System.out.println(libroBuscado.get());
//        } else {
//            System.out.println("Libro no encontrado");
//        }
//
//        //Trabajando con estadisticas
//        DoubleSummaryStatistics est = datosBusqueda.resultados().stream()
//                .filter(d -> d.numeroDeDescargas() > 0)
//                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
//        System.out.println("Cantidad media de descargas: " + est.getAverage());
//        System.out.println("Cantidad maxima de descargas: " + est.getMax());
//        System.out.println("Cantidad minima de descargas: " + est.getMin());
//        System.out.println("Cantidad de registros evaluados para calcular las estadisticas: " + est.getCount());


}
