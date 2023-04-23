/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package cl.ucn.disc.pa.bibliotech;

import cl.ucn.disc.pa.bibliotech.services.Sistema;
import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

import java.io.IOException;
import java.util.Objects;

/**
 * The Main.
 *
 * @author Programacion Avanzada.
 */
public final class Main {

    /**
     * The main.
     *
     * @param args to use.
     * @throws IOException en caso de un error.
     */
    public static void main(final String[] args) throws IOException {

        // inicializacion del sistema.
        Sistema sistema = new Sistema();

        String opcion = null;
        while (!Objects.equals(opcion, "2")) {

            //Intentamos añadir una opcion para poder añadir a un nuevo socio.
            StdOut.println("""
                    [*] Bienvenido a BiblioTech [*]
                                    
                    [1] Iniciar Sesion
                    [2] Registrarse
                    [3] Salir
                    """);
            StdOut.print("Escoja una opcion: ");
            opcion = StdIn.readLine();

            switch (opcion) {
                case "1" -> iniciarSesion(sistema);
                case "2" -> agregarSocio(sistema);
                case "3" -> StdOut.println("¡Hasta Pronto!");
                default -> StdOut.println("Opcion no valida, intente nuevamente");
            }
        }
    }

    /**
     * Inicia la sesion del Socio en el Sistema.
     *
     * @param sistema a utilizar.
     */
    private static void iniciarSesion(final Sistema sistema) {
        StdOut.println("[*] Iniciar sesion en BiblioTech [*]");
        StdOut.print("Ingrese su numero de socio: ");
        int numeroSocio = StdIn.readInt();
        StdIn.readLine();

        StdOut.print("Ingrese su contrasenia: ");
        String contrasenia = StdIn.readLine();

        // intento el inicio de session
        try {
            sistema.iniciarSession(numeroSocio, contrasenia);
        } catch (IllegalArgumentException ex) {
            StdOut.println("Ocurrio un error: " + ex.getMessage());
            return;
        }

        // mostrar menu principal
        menuPrincipal(sistema);
    }

    /**
     * Parte del menu de inicio de sesion para poder agregar mas socios.
     *
     * @param sistema a utilizar.
     */
    public static void agregarSocio(final Sistema sistema) {
        StdOut.println("[*] Registrarse como socio en BiblioTech [*]");
        StdOut.println("Ingrese los siguientes datos al sistema.");
        StdOut.print("");
        StdOut.print("Nombre: ");
        String nombre = StdIn.readString();
        StdOut.print("Apellido: ");
        String apellido = StdIn.readString();
        StdOut.print("Correo electronico: ");
        String correoElectronico = StdIn.readString();
        StdOut.print("Contraseña: ");
        String contrasenia = StdIn.readString();
        int numeroSocio = sistema.asignarNumeroSocio();
        if (numeroSocio == -1) {
            StdOut.println("Lo sentimos pero no podemos registrar mas socios!");
        } else {
            StdOut.println("Su numero de socio es: " + numeroSocio + ", Por favor recuerde este numero.");
            sistema.registrarSocio(nombre, apellido, correoElectronico, contrasenia, numeroSocio);
        }
    }

    /**
     * Menu donde navegara el socio registrado para hacer uso de las distintas opciones.
     *
     * @param sistema a utilizar.
     */
    private static void menuPrincipal(final Sistema sistema) {
        int opcion = 0;
        while (opcion != 4) {
            StdOut.println("""
                    [*] BiblioTech [*]
                                        
                    [1] Prestamo de un libro
                    [2] Editar información
                    [3] Calificar libro
                                                    
                    [4] Cerrar sesion
                    """);

            StdOut.print("Escoja una opcion: ");
            opcion = StdIn.readInt();

            switch (opcion) {
                case 1 -> menuPrestamo(sistema);
                case 2 -> editarInformacion(sistema);
                //TODO: Crear metodo de calificar libro.S
                case 3 -> calificarLibro(sistema);
                case 4 -> sistema.cerrarSession();
                default -> StdOut.println("Opcion no valida, intente nuevamente");
            }
        }
    }

    /**
     * Menu para que un socio pueda realizar el prestamo de un libro.
     *
     * @param sistema a utilizar
     */
    private static void menuPrestamo(Sistema sistema) {
        StdOut.println("[*] Préstamo de un Libro [*]");
        StdOut.println(sistema.obtegerCatalogoLibros());

        StdOut.print("Ingrese el ISBN del libro a tomar prestado: ");
        String isbn = StdIn.readString();

        try {
            sistema.realizarPrestamoLibro(isbn);
        } catch (IOException ex) {
            StdOut.println("Ocurrio un error, intente nuevamente: " + ex.getMessage());
        }
    }

    /**
     * Parte del menu donde el socio podra realizar los cambios de su informacion si asi lo desea.
     *
     * @param sistema a utilizar.
     */
    private static void editarInformacion(Sistema sistema) {

        String opcion = null;
        while (!Objects.equals(opcion, "3")) {

            StdOut.println("[*] Editar Perfil [*]");
            StdOut.println(sistema.obtenerDatosSocioLogeado());
            StdOut.println("""               
                    [1] Editar correo Electronico
                    [2] Editar Contraseña
                                        
                    [3] Volver atrás
                    """);
            StdOut.print("Escoja una opción: ");
            opcion = StdIn.readLine();

            switch (opcion) {
                case "1" -> editarCorreo(sistema);
                case "2" -> cambiarContrasenia(sistema);
                case "3" -> StdOut.println("Volviendo al menú anterior...");
                default -> StdOut.println("Opcion no valida, intente nuevamente");
            }
        }
    }

    /**
     * Metodo con el cual el socio podra cambiar su contraseña.
     *
     * @param sistema a utilizar.
     */
    private static void cambiarContrasenia(Sistema sistema) {
        // TODO: implementar este metodo
        StdOut.print("Ingrese la nueva contraseña para el usuario: ");
        String contraseniaNueva = StdIn.readString();
        //La contraseña ingresada es mandada al sistema para su cambio.
        sistema.nuevaContrasenia(contraseniaNueva);
        StdOut.println("la contrasenia fue cambiada exitosamente!");
    }

    /**
     * Metodo con el cual el socio podra editar su correo.
     *
     * @param sistema a utilizar.
     */
    private static void editarCorreo(Sistema sistema) {
        // TODO: implementar este metodo
        StdOut.print("Ingrese el nuevo correo electronico que quiera usar: ");
        String nuevoCorreoElectronico = StdIn.readLine();
        //Se le manda al sistema el nuevo correo electronico del socio para su cambio.
        sistema.nuevoCorreo(nuevoCorreoElectronico);
        StdOut.println("EL correo fue modificado correctamente!");
    }

    /**
     * Metodo para poder realizar las calificaciones a los libros.
     *
     * @param sistema a utilizar.
     */
    private static void calificarLibro(Sistema sistema) {
        StdOut.print("Ingrese el isbn del libro a calificar");
        String libroCalificacion = StdIn.readString();
        //Se verifica que el isbn ingresado exista mediante subprogramas en el sistema.
        int numeroVerificar = sistema.VerificarLibro(libroCalificacion);
        //Ciclo para poder verificar que el usuario pueda ingresar de manera correcta los datos.
        while (true) {
            if (numeroVerificar == -1) {
                StdOut.println("No se ha encontrado el libro, intente con otro isbn!");
                StdOut.print("Ingrese el isbn del libro a calificar");
                libroCalificacion = StdIn.readString();
                numeroVerificar = sistema.VerificarLibro(libroCalificacion);
            } else {
                StdOut.print("Ingrese la calificaion del libro. (Esta debe ser de 0.0 a 5.0): ");
                double calificaionDada = StdIn.readDouble();
                //Ciclo para poder verificar que el usuario pueda ingresar de manera correcta los datos.
                while (true) {
                    if (calificaionDada < 0.0 || calificaionDada > 5.0) {
                        StdOut.println("Calificacion no valida, debe ser entre 0.0 y 5.0, intente nuevamente.");
                        StdOut.print("Ingrese la calificaion del libro. (Esta debe ser de 0.0 a 5.0): ");
                        calificaionDada = StdIn.readDouble();
                    } else {
                        break;
                    }
                }
                //Se le manda al sistema la calificacion dada por el socio para poder modificar la calificacion del libro.
                sistema.cambiarCalificacion(calificaionDada, numeroVerificar);
                StdOut.println("Gracias por calificar este libro!");
                break;
            }
        }
    }

}