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

        // ===== REPOSITORIES =====
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ObraRepository obraRepository = new ObraRepository();
        RestauracionRepository restauracionRepository = new RestauracionRepository();

        // ===== SERVICES =====
        AuthService authService = new AuthService(usuarioRepository);
        ObraService obraService = new ObraService(obraRepository, restauracionRepository);

        // ===== LOGIN =====
        System.out.println("===== SISTEMA MUSEO =====");
        System.out.println("=== LOGIN ===");

        System.out.print("Usuario: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Usuario usuario = authService.login(username, password);

        if (usuario == null) {
            System.out.println("Credenciales incorrectas");
            return;
        }

        System.out.println("Bienvenido " + usuario.getRol());

        // ===== MENU SEGUN ROL =====
        boolean salir = false;

        while (!salir) {

            System.out.println("\n===== MENU PRINCIPAL =====");

            switch (usuario.getRol()) {

                case DIRECTOR:
                    System.out.println("1. Ver valor total del museo");
                    System.out.println("0. Salir");

                    int opcionDirector = Integer.parseInt(scanner.nextLine());

                    if (opcionDirector == 1) {
                        double total = obraService.calcularValorTotalMuseo();
                        System.out.println("Valor total del museo: " + total);
                    } else if (opcionDirector == 0) {
                        salir = true;
                    }
                    break;

                case RESTAURADOR:
                    System.out.println("1. Verificar restauraciones automaticas");
                    System.out.println("0. Salir");

                    int opcionRestaurador = Integer.parseInt(scanner.nextLine());

                    if (opcionRestaurador == 1) {
                        obraService.verificarRestauracionesAutomaticas();
                        System.out.println("Proceso ejecutado.");
                    } else if (opcionRestaurador == 0) {
                        salir = true;
                    }
                    break;

                case CATALOGADOR:
                    System.out.println("1. Registrar obra");
                    System.out.println("0. Salir");

                    int opcionCatalogador = Integer.parseInt(scanner.nextLine());

                    if (opcionCatalogador == 1) {

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
                    } else if (opcionCatalogador == 0) {
                        salir = true;
                    }
                    break;

                case VISITANTE:
                    System.out.println("1. Ver mensaje de bienvenida");
                    System.out.println("0. Salir");

                    int opcionVisitante = Integer.parseInt(scanner.nextLine());

                    if (opcionVisitante == 1) {
                        System.out.println("Bienvenido al museo.");
                    } else if (opcionVisitante == 0) {
                        salir = true;
                    }
                    break;
            }
        }

        System.out.println("Sesion finalizada.");
    }
}