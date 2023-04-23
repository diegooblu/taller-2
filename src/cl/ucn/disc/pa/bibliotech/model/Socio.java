/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package cl.ucn.disc.pa.bibliotech.model;

import cl.ucn.disc.pa.bibliotech.services.Utils;

/**
 * Clase que representa a un Socio.
 *
 * @author Programacion Avanzada.
 */
public final class Socio {

    /**
     * Numero maximo de libros que puede tener el Socio.
     */
    private static final int NUMERO_LIBROS_MAXIMO = 5;

    /**
     * Nombre del socio.
     */
    private String nombre;

    /**
     * Apellido del socio.
     */
    private String apellido;

    /**
     * Email del socio.
     */
    private String correoElectronico;

    /**
     * Numero del socio.
     */
    private int numeroDeSocio;

    /**
     * Contrasenia del socio.
     */
    private String contrasenia;

    /**
     * Libros que el Socio tiene en prestamo (maximo 10).
     */
    private Libro[] librosEnPrestamo = new Libro[10];

    /**
     * The Constructor.
     *
     * @param nombre            del socio.
     * @param apellido          del socio.
     * @param correoElectronico del socio.
     * @param numeroDeSocio     del socio.
     * @param contrasenia       del socio.
     */
    public Socio(String nombre, String apellido, String correoElectronico, int numeroDeSocio, String contrasenia) {

        // TODO: agregar validacion
        if (noNumeros(nombre) == false) {
            throw new IllegalArgumentException("Nombre no puede contener numeros");
        }
        this.nombre = nombre;

        // TODO: agregar validacion
        if (noNumeros(apellido) == false) {
            throw new IllegalArgumentException("Apellido no puede contener numeros");
        }
        this.apellido = apellido;

        // metodo estatico para validacion de email.
        Utils.validarEmail(correoElectronico);
        this.correoElectronico = correoElectronico;

        // TODO: agregar validacion
        if (numeroDeSocio <= 0) {
            throw new IllegalArgumentException("Debe ingresar un numero mayor a cero");
        }
        String numeroSocioString = Integer.toString(numeroDeSocio);
        if (soloNumeros(numeroSocioString) == false) {
            throw new IllegalArgumentException("Numero de socio no puede contener letras");
        }

        this.numeroDeSocio = numeroDeSocio;

        // TODO: agregar validacion
        this.contrasenia = contrasenia;
    }

    /**
     * @return el nombre del Socio.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * @return el apellido del Socio.
     */
    public String getApellido() {
        return this.apellido;
    }

    /**
     * @return el nombre completo del Socio.
     */
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    /**
     * @return el correo electronico del Socio.
     */
    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    /**
     * @return el numero del Socio.
     */
    public int getNumeroDeSocio() {
        return this.numeroDeSocio;
    }

    /**
     * @return la contrasenia del Socio.
     */
    public String getContrasenia() {
        return this.contrasenia;
    }

    /**
     * Modifica la contraseña del socio.
     *
     * @param contrasenia nueva del socio.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Modifica el correo electronico del socio.
     *
     * @param correoElectronico nuevo del socio.
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Agrega un libro en prestamo al Socio.
     *
     * @param libro a agregar.
     */
    public void agregarLibro(final Libro libro) {
        // validacion
        if (this.librosEnPrestamo.length == NUMERO_LIBROS_MAXIMO) {
            throw new IllegalArgumentException("El Socio ya tiene la maxima cantidad de libros en prestamo: " + NUMERO_LIBROS_MAXIMO);
        }
        // agrego el libro
        Utils.append(this.librosEnPrestamo, libro);
    }

    /**
     * Metodo para revisar si existen numeros en un String y si existen
     * retorna un falso
     *
     * @param string
     * @return
     */

    public static boolean noNumeros(String string) {
        return string.matches("^[^0-9]*$");
    }

    /**
     * Metodo para revisar si existen letras en un String y si existen
     * retorna un falso
     *
     * @param string
     * @return
     */
    public boolean soloNumeros(String string) {
        return string.matches("[0-9]+");
    }
}
