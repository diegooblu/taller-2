/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package cl.ucn.disc.pa.bibliotech.model;

/**
 * Clase que representa un Libro.
 *
 * @author Programacion Avanzada.
 */
public final class Libro {

    /**
     * The ISBN.
     */
    private String isbn;

    /**
     * The Titulo.
     */
    private String titulo;

    /**
     * The Author.
     */
    private String autor;

    /**
     * The Categoria
     */
    private String categoria;

    /**
     * The calificacion
     */
    private double calificacion;

    /**
     * the sumatoria de las calificaciones.
     */
    private double sumatoriaCalificaciones;

    /**
     * the cantidad de las calificaciones.
     */
    private double CantCalificaciones;

    /**
     * the disponibilidad del libro.
     */
    private boolean disponible;

    /**
     * The Constructor.
     *
     * @param isbn      del libro.
     * @param titulo    del libro.
     * @param autor     del libro
     * @param categoria del libro.
     */
    public Libro(final String isbn, final String titulo, final String autor, final String categoria) {
        // TODO: agregar validacion de ISBN
        if (isbn == null || isbn.length() == 0 || isbn.length() > 10) {
            throw new IllegalArgumentException("ISBN no valido!");
        }
        if (soloNumeros(isbn) == false) {
            throw new IllegalArgumentException("ISBN no valido!");
        }
        this.isbn = isbn;

        // validacion del titulo
        if (titulo == null || titulo.length() == 0) {
            throw new IllegalArgumentException("Titulo no valido!");
        }
        this.titulo = titulo;

        // TODO: Agregar validacion
        if (autor == null || autor.length() == 0) {
            throw new IllegalArgumentException("Nombre de Autor no valido!");
        }
        if (noNumeros(autor) == false) {
            throw new IllegalArgumentException("Nombre del Autor no puede contener numeros!");
        }
        this.autor = autor;

        // TODO: Agregar validacion
        if (categoria == null || categoria.length() == 0) {
            throw new IllegalArgumentException("Categoria no valida!");
        }
        if (noNumeros(categoria) == true) {
            throw new IllegalArgumentException("Categoria no puede contener numeros!");
        }

        this.categoria = categoria;

        this.CantCalificaciones = 0;

        this.disponible = true;
    }

    /**
     * @return the ISBN.
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * @return the titulo.
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * @return the autor.
     */
    public String getAutor() {
        return this.autor;
    }

    /**
     * @return the categoria.
     */
    public String getCategoria() {
        return this.categoria;
    }

    /**
     * @return cantidad de calificaciones.
     */
    public double getCantCalificaciones() {
        return CantCalificaciones;
    }

    /**
     * Modifica la cantidad de calificaciones del libro.
     *
     * @param cantCalificaciones nueva del libro.
     */
    public void setCantCalificaciones(double cantCalificaciones) {
        CantCalificaciones = cantCalificaciones;
    }

    /**
     * @return disponibilidad del libro.
     */
    public boolean getDisponible() {
        return disponible;
    }

    /**
     * Modifica la disponibilidad de un libro.
     *
     * @param disponible de un libro.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * @return la sumatoria de calificaciones.
     */
    public double getSumatoriaCalificaciones() {
        return sumatoriaCalificaciones;
    }

    /**
     * Setter que modifica la sumatorio de las calificaciones.
     *
     * @param sumatoriaCalificaciones nueva del libro.
     */
    public void setSumatoriaCalificaciones(double sumatoriaCalificaciones) {
        this.sumatoriaCalificaciones = sumatoriaCalificaciones;
    }

    /**
     * metodo para buscar numeros en un string y si los encuentra retorna un falso, se ocupa para encontrar
     * numeros no deseados en un String
     *
     * @param string
     * @return
     */
    public static boolean noNumeros(String string) {
        return string.matches("^[^0-9]*$");
    }

    /**
     * metodo para buscar numeros en un string y si los encuentra al menos una letra retorna un falso, se ocupa
     * para encontrar letras no deseadas en un String
     *
     * @param string
     * @return
     */
    public boolean soloNumeros(String string) {
        return string.matches("[0-9]+");
    }
}
