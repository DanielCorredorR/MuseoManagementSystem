package app;

import java.util.Scanner;
import model.Rol;
import model.Usuario;
import repository.ObraRepository;
import repository.RestauracionRepository;
import repository.UsuarioRepository;
import service.AuthService;
import service.ObraService;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ObraRepository obraRepository = new ObraRepository();
        RestauracionRepository restauracionRepository = new RestauracionRepository();

        AuthService authService = new AuthService(usuarioRepository);
        ObraService obraService = new ObraService(obraRepository, restauracionRepository);

        boolean sistemaActivo = true;

        while (sistemaActivo) {

            System.out.println("\n===== SISTEMA MUSEO =====");
            System.out.println("1. Iniciar Sesion");
            System.out.println("0. Salir");

            int opcionInicio = Integer.parseInt(scanner.nextLine());

            if (opcionInicio == 0) {
                sistemaActivo = false;
                continue;
            }

            if (opcionInicio != 1) {
                continue;
            }

            Usuario usuarioLogueado = null;

            while (usuarioLogueado == null) {

                System.out.println("\n=== LOGIN ===");

                System.out.print("Usuario: ");
                String username = scanner.nextLine();

                System.out.print("Password: ");
                String password = scanner.nextLine();

                usuarioLogueado = authService.login(username, password);

                if (usuarioLogueado == null) {
                    System.out.println("Credenciales incorrectas. Intente nuevamente.");
                }
            }

            System.out.println("Bienvenido " + usuarioLogueado.getRol());

            boolean sesionActiva = true;

            while (sesionActiva) {

                System.out.println("\n===== MENU PRINCIPAL =====");

                switch (usuarioLogueado.getRol()) {

                    case DIRECTOR:
                        System.out.println("1. Ver valor total del museo");
                        System.out.println("9. Cerrar Sesion");
                        break;

                    case RESTAURADOR:
                        System.out.println("1. Ejecutar restauraciones automaticas");
                        System.out.println("9. Cerrar Sesion");
                        break;

                    case CATALOGADOR:
                        System.out.println("1. Registrar obra");
                        System.out.println("9. Cerrar Sesion");
                        break;

                    case VISITANTE:
                        System.out.println("1. Mensaje de bienvenida");
                        System.out.println("9. Cerrar Sesion");
                        break;
                }

                int opcion = Integer.parseInt(scanner.nextLine());

                if (opcion == 9) {
                    sesionActiva = false;
                    System.out.println("Sesion cerrada.");
                    continue;
                }

                switch (usuarioLogueado.getRol()) {

                    case DIRECTOR:
                        if (opcion == 1) {
                            double total = obraService.calcularValorTotalMuseo();
                            System.out.println("Valor total del museo: " + total);
                        }
                        break;

                    case RESTAURADOR:
                        if (opcion == 1) {
                            obraService.verificarRestauracionesAutomaticas();
                            System.out.println("Proceso ejecutado.");
                        }
                        break;

                    case CATALOGADOR:
                        if (opcion == 1) {

                            System.out.print("Titulo: ");
                            String titulo = scanner.nextLine();

                            System.out.print("Autor: ");
                            String autor = scanner.nextLine();

                            System.out.print("Anio creacion: ");
                            int anio = Integer.parseInt(scanner.nextLine());

                            System.out.print("Valor economico: ");
                            double valor = Double.parseDouble(scanner.nextLine());

                            model.ObraArte obra =
                                    new model.ObraArte(titulo, autor, anio, valor);

                            obraService.registrarObra(obra);

                            System.out.println("Obra registrada correctamente.");
                        }
                        break;

                    case VISITANTE:
                        if (opcion == 1) {
                            System.out.println("Bienvenido al museo.");
                        }
                        break;
                }
            }
        }

        System.out.println("Sistema finalizado.");
    }
}