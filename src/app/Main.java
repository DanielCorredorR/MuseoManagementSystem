package main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Museo;
import model.ObraArte;
import model.Usuario;

import repository.UsuarioRepository;
import repository.ObraRepository;
import repository.RestauracionRepository;
import repository.MuseoRepository;
import repository.CesionRepository;

import service.AuthService;
import service.ObraService;
import service.CesionService;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // =========================
        // REPOSITORIES
        // =========================
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ObraRepository obraRepository = new ObraRepository();
        RestauracionRepository restauracionRepository = new RestauracionRepository();
        MuseoRepository museoRepository = new MuseoRepository();
        CesionRepository cesionRepository = new CesionRepository();

        // =========================
        // SERVICES
        // =========================
        AuthService authService = new AuthService(usuarioRepository);
        ObraService obraService =
                new ObraService(obraRepository, restauracionRepository);
        CesionService cesionService =
                new CesionService(cesionRepository, obraRepository);

        // =========================
        // LOGIN
        // =========================
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

        // =========================
        // MENU DIRECTOR
        // =========================
        if (usuario.getRol().equals("DIRECTOR")) {

            boolean salir = false;

            while (!salir) {

                System.out.println("\n=== MENU DIRECTOR ===");
                System.out.println("1. Registrar museo");
                System.out.println("2. Listar museos");
                System.out.println("3. Ceder obra");
                System.out.println("4. Ver cesiones por obra");
                System.out.println("5. Ver valor total museo");
                System.out.println("0. Salir");

                System.out.print("Seleccione opcion: ");
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {

                    case 1:
                        System.out.print("Nombre museo: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Ciudad: ");
                        String ciudad = scanner.nextLine();

                        Museo museo = new Museo(nombre, ciudad);
                        museoRepository.save(museo);

                        System.out.println("Museo registrado");
                        break;

                    case 2:
                        List<Museo> museos = museoRepository.findAll();

                        for (Museo m : museos) {
                            System.out.println(m.getId() + " - " + m);
                        }
                        break;

                    case 3:
                        System.out.print("ID Obra: ");
                        String idObra = scanner.nextLine();

                        System.out.print("ID Museo destino: ");
                        String idMuseo = scanner.nextLine();

                        Museo museoDestino = museoRepository.findById(idMuseo);

                        if (museoDestino == null) {
                            System.out.println("Museo no encontrado");
                            break;
                        }

                        System.out.print("Importe: ");
                        double importe = Double.parseDouble(scanner.nextLine());

                        System.out.print("Fecha inicio (YYYY-MM-DD): ");
                        LocalDate inicio =
                                LocalDate.parse(scanner.nextLine());

                        System.out.print("Fecha fin (YYYY-MM-DD): ");
                        LocalDate fin =
                                LocalDate.parse(scanner.nextLine());

                        cesionService.cederObra(
                                idObra,
                                museoDestino,
                                importe,
                                inicio,
                                fin
                        );

                        System.out.println("Cesion registrada");
                        break;

                    case 4:
                        System.out.print("ID Obra: ");
                        String id = scanner.nextLine();

                        cesionRepository.findByObraId(id)
                                .forEach(System.out::println);
                        break;

                    case 5:
                        double total =
                                obraService.calcularValorTotalMuseo();

                        System.out.println("Valor total museo: $" + total);
                        break;

                    case 0:
                        salir = true;
                        break;

                    default:
                        System.out.println("Opcion invalida");
                }
            }
        }

        System.out.println("Sistema finalizado");
    }
}