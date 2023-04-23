/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package cl.ucn.disc.pa.bibliotech.services;

import cl.ucn.disc.pa.bibliotech.model.Libro;
import cl.ucn.disc.pa.bibliotech.model.Socio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.princeton.cs.stdlib.StdOut;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Sistema.
 *
 * @author Programacion Avanzada.
 */
public final class Sistema {

    /**
     * Procesador de JSON.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * The list of Socios.
     */
    private Socio[] socios;

    /**
     * The list of Libros.
     */
    private Libro[] libros;

    /**
     * Socio en el sistema.
     */
    private Socio socio;

    /**
     * The Sistema.
     */
    public Sistema() throws IOException {

        // no hay socio logeado.
        this.socios = new Socio[0];
        this.libros = new Libro[0];
        this.socio = null;

        // carga de los socios y libros.
        try {
            this.cargarInformacion();
        } catch (FileNotFoundException ex) {
            // no se encontraron datos, se agregar los por defecto.

            // creo un socio
            this.socios = Utils.append(this.socios, new Socio("John", "Doe", "john.doe@ucn.cl", 1, "john123"));

            // creo un libro y lo agrego al arreglo de libros.
            this.libros = Utils.append(this.libros, new Libro("1491910771", "Head First Java: A Brain-Friendly Guide", " Kathy Sierra", "Programming Languages"));

            // creo otro libro y lo agrego al arreglo de libros.
            this.libros = Utils.append(this.libros, new Libro("1491910771", "Effective Java", "Joshua Bloch", "Programming Languages"));

        } finally {
            // guardo la informacion.
            this.guardarInformacion();
        }

    }

    /**
     * Activa (inicia sesion) de un socio en el sistema.
     *
     * @param numeroDeSocio a utilizar.
     * @param contrasenia   a validar.
     */
    public void iniciarSession(final int numeroDeSocio, final String contrasenia) {

        // el numero de socio siempre es positivo.
        if (numeroDeSocio <= 0) {
            throw new IllegalArgumentException("El numero de socio no es valido!");
        }

        // TODO: buscar el socio dado su numero.
        int posicionSocio = -1;
        for (int i = 0; i < socios.length; i++) {
            if (numeroDeSocio == (socios[i].getNumeroDeSocio())) {
                posicionSocio = i;
            }
        }
        if (posicionSocio == -1) {
            throw new IllegalArgumentException("No se ha encontrado al socio!");
        }

        // TODO: verificar su clave.
        String contraseniaVerificar = socios[posicionSocio].getContrasenia();
        if (!contrasenia.equals(contraseniaVerificar)) {
            throw new IllegalArgumentException("Contraseña ingresada incorrecta!");
        }

        // TODO: asignar al atributo socio el socio encontrado.
        this.socio = socios[posicionSocio];
    }

    public void registrarSocio(String nombre, String apellido, String correoElectronico, String contrasenia, int numeroSocio) {
        Socio nuevoSocio = new Socio(nombre, apellido, correoElectronico, numeroSocio, contrasenia);
        socios[numeroSocio - 1] = nuevoSocio;
    }

    /**
     * Cierra la session del Socio.
     */
    public void cerrarSession() {
        this.socio = null;
    }

    /**
     * Metodo que mueve un libro de los disponibles y lo ingresa a un Socio.
     *
     * @param isbn del libro a prestar.
     */
    public void realizarPrestamoLibro(final String isbn) throws IOException {
        // el socio debe estar activo.
        if (this.socio == null) {
            throw new IllegalArgumentException("Socio no se ha logeado!");
        }

        // busco el libro.
        Libro libro = this.buscarLibro(isbn);

        // si no lo encontre, lo informo.
        if (libro == null) {
            StdOut.println("Libro con isbn " + isbn + " no existe o no se encuentra disponible.");
            StdOut.println("Intente de nuevo con otro isbn.");
        } else {
            // TODO: eliminar el libro de los disponibles
            if (libro.getDisponible() == false) {
                StdOut.println("Libro no esta disponible");
            } else if (libro.getDisponible() == true) {
                // agrego el libro al socio.
                this.socio.agregarLibro(libro);
                libro.setDisponible(false);
            }

            // se actualiza la informacion de los archivos
            this.guardarInformacion();
        }
    }

    /**
     * Obtiene un String que representa el listado completo de libros disponibles.
     *
     * @return the String con la informacion de los libros disponibles.
     */
    public String obtegerCatalogoLibros() {
        int posicion = 0;
        StringBuilder sb = new StringBuilder();
        for (Libro libro : this.libros) {
            sb.append("Titulo    : ").append(libro.getTitulo()).append("\n");
            sb.append("Autor     : ").append(libro.getAutor()).append("\n");
            sb.append("ISBN      : ").append(libro.getIsbn()).append("\n");
            sb.append("Categoria : ").append(libro.getCategoria()).append("\n");
            sb.append("\n");

            posicion++;
        }

        return sb.toString();
    }

    /**
     * Metodo que busca un libro en los libros disponibles.
     *
     * @param isbn a buscar.
     * @return el libro o null si no fue encontrado.l
     */
    public Libro buscarLibro(final String isbn) {
        // recorro el arreglo de libros.
        for (Libro libro : this.libros) {
            // si lo encontre, retorno el libro.
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        // no lo encontre, retorno null.
        return null;
    }

    /**
     * Lee los archivos libros.json y socios.json.
     *
     * @throws FileNotFoundException si alguno de los archivos no se encuentra.
     */
    private void cargarInformacion() throws FileNotFoundException {

        // trato de leer los socios y los libros desde el archivo.
        this.socios = GSON.fromJson(new FileReader("socios.json"), Socio[].class);
        this.libros = GSON.fromJson(new FileReader("libros.json"), Libro[].class);
    }

    /**
     * Guarda los arreglos libros y socios en los archivos libros.json y socios.json.
     *
     * @throws IOException en caso de algun error.
     */
    private void guardarInformacion() throws IOException {

        // guardo los socios.
        try (FileWriter writer = new FileWriter("socios.json")) {
            GSON.toJson(this.socios, writer);
        }

        // guardo los libros.
        try (FileWriter writer = new FileWriter("libros.json")) {
            GSON.toJson(this.libros, writer);
        }

    }

    public String obtenerDatosSocioLogeado() {
        if (this.socio == null) {
            throw new IllegalArgumentException("No hay un Socio logeado");
        }

        return "Nombre: " + this.socio.getNombreCompleto() + "\n"
                + "Correo Electronico: " + this.socio.getCorreoElectronico();
    }

    public void nuevoNombreCompleto(String NombreNuevo, String Apellidonuevo) {
        socio.setNombre(NombreNuevo);
        socio.setApellido(Apellidonuevo);
    }

    /**
     * metodo con el cual cambiaremos la contraseña del usuario.
     *
     * @param contrasenia la cual seria la nueva contraseña del usuario
     */
    public void nuevaContrasenia(String contraseniaNueva) {
        socio.setContrasenia(contraseniaNueva);
    }

    /**
     * metodo para modificar el correo del socio.
     *
     * @param correoNuevo nuevo correo que ingreso el socio.
     */
    public void nuevoCorreo(String correoNuevo) {
        socio.setCorreoElectronico(correoNuevo);
    }


    /**
     * Metodo para dar y cambiar las calificaciones de los libros.
     *
     * @param calificacionEntregada calificacion dada por el socio para el libro.
     * @param posicionLibro         posicion del libro para poder modificar su calificacion.
     */
    public void cambiarCalificacion(double calificacionEntregada, int posicionLibro) {
        libros[posicionLibro].setCantCalificaciones(libros[posicionLibro].getCantCalificaciones() + 1);
        //La calificacion de libro es asignada a una variable para su posterior cambio.
        double calificacionAnterior = libros[posicionLibro].getSumatoriaCalificaciones();
        double nuevaCalificacion;
        nuevaCalificacion = calificacionEntregada + calificacionAnterior;
        //Se consigue la nueva calificacion.
        StdOut.println("La nueva calificacion del libro es " + (nuevaCalificacion / libros[posicionLibro].getCantCalificaciones()));
        //Se guarda la calificacion.
        libros[posicionLibro].setSumatoriaCalificaciones(nuevaCalificacion);
    }

    /**
     * Metodo creado para verificar si el libro ingresado por el socio existe.
     *
     * @param isbn del libro para corroborar que exista.
     * @return la posicion del libro o -1 si es que no encuentra el libro.
     */
    public int VerificarLibro(String isbn) {
        int posicion;
        int i = 0;
        // recorro el arreglo de libros.
        for (Libro libro : this.libros) {
            // si lo encontre, retorno el libro.
            if (libro.getIsbn().equals(isbn)) {
                posicion = i;
                return posicion;
            }
            i++;
        }
        // no lo encontre, retorno -1.
        return -1;
    }

    /**
     * Metodo que le asigna a un socio un numero de socio para logearse.
     *
     * @return -1 si no hay mas espacio para agregar socio, sino regresa el numero de socio asignado.
     */
    public int asignarNumeroSocio() {
        int nuevoNumeroSocio = -1;
        //Se recorre el arreglo de socios.
        for (int i = 0; i < socios.length; i++) {
            //Si se encuentra una posicion vacia, retorno la posicion.
            if (socios[i] == null) {
                nuevoNumeroSocio = i + 1;
                return nuevoNumeroSocio;
            }
        }
        //Si no hay espacio retorno, nuevoNumeroSocio = -1.
        return nuevoNumeroSocio;
    }
}
