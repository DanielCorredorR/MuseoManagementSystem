package app;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Museo;
import model.ObraArte;
import model.Usuario;
import model.Sala;
import model.Rol;

import repository.UsuarioRepository;
import repository.ObraRepository;
import repository.RestauracionRepository;
import repository.MuseoRepository;
import repository.CesionRepository;
import repository.SalaRepository;

import service.AuthService;
import service.ObraService;
import service.CesionService;
import service.RestauracionService;
import service.SalaService;

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
        SalaRepository salaRepository = new SalaRepository();

        // =========================
        // SERVICES
        // =========================
        AuthService authService = new AuthService(usuarioRepository);

        ObraService obraService =
                new ObraService(obraRepository, restauracionRepository);

        CesionService cesionService =
                new CesionService(cesionRepository, obraRepository);

        RestauracionService restauracionService =
                new RestauracionService(obraRepository);

        SalaService salaService =
                new SalaService(salaRepository, obraRepository);

        // =========================
        // DATOS INICIALES (SALAS)
        // =========================
        Sala sala1 = new Sala("Sala Renacimiento", "Obras siglo XV");
        Sala sala2 = new Sala("Sala Moderna", "Obras siglo XX");

        salaRepository.save(sala1);
        salaRepository.save(sala2);

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

        switch (usuario.getRol()) {

            case DIRECTOR:
                menuDirector(scanner, museoRepository, cesionService,
                        cesionRepository, obraService);
                break;

            case VISITANTE:
                menuVisitante(scanner, salaRepository, salaService);
                break;

            case RESTAURADOR:
                menuRestaurador(scanner, restauracionService);
                break;

            case CATALOGADOR:
                System.out.println("Menu catalogador pendiente de implementar");
                break;

            default:
                System.out.println("Rol no reconocido");
        }

        System.out.println("Sistema finalizado");
    }

    // ==========================================================
    // ======================= MENU DIRECTOR =====================
    // ==========================================================
    private static void menuDirector(Scanner scanner,
                                     MuseoRepository museoRepository,
                                     CesionService cesionService,
                                     CesionRepository cesionRepository,
                                     ObraService obraService) {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n=== MENU DIRECTOR ===");
            System.out.println("1. Registrar museo");
            System.out.println("2. Listar museos");
            System.out.println("3. Ceder obra");
            System.out.println("4. Ver cesiones por obra");
            System.out.println("5. Ver valor total museo");
            System.out.println("0. Salir");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {

                case 1:
                    System.out.print("Nombre museo: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Ciudad: ");
                    String ciudad = scanner.nextLine();

                    museoRepository.save(new Museo(nombre, ciudad));
                    System.out.println("Museo registrado");
                    break;

                case 2:
                    museoRepository.findAll()
                            .forEach(System.out::println);
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
                    double importe =
                            Double.parseDouble(scanner.nextLine());

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
            }
        }
    }

    // ==========================================================
    // ======================= MENU VISITANTE ====================
    // ==========================================================
    private static void menuVisitante(Scanner scanner,
                                      SalaRepository salaRepository,
                                      SalaService salaService) {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n=== MENU VISITANTE ===");
            System.out.println("1. Ver salas");
            System.out.println("2. Ver obras por sala");
            System.out.println("0. Salir");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {

                case 1:
                    salaRepository.findAll()
                            .forEach(System.out::println);
                    break;

                case 2:
                    System.out.print("ID Sala: ");
                    String idSala = scanner.nextLine();

                    List<ObraArte> obras =
                            salaService.listarObrasPorSala(idSala);

                    if (obras.isEmpty()) {
                        System.out.println("No hay obras en esta sala");
                    } else {
                        obras.forEach(System.out::println);
                    }
                    break;

                case 0:
                    salir = true;
                    break;
            }
        }
    }

    // ==========================================================
    // ======================= MENU RESTAURADOR ==================
    // ==========================================================
    private static void menuRestaurador(Scanner scanner,
                                        RestauracionService restauracionService) {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n=== MENU RESTAURADOR ===");
            System.out.println("1. Enviar obra a restauracion");
            System.out.println("2. Finalizar restauracion");
            System.out.println("3. Ver restauraciones de obra");
            System.out.println("0. Salir");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {

                case 1:
                    System.out.print("ID Obra: ");
                    String idObra = scanner.nextLine();

                    System.out.print("Tipo restauracion: ");
                    String tipo = scanner.nextLine();

                    restauracionService.enviarARestauracion(idObra, tipo);
                    break;

                case 2:
                    System.out.print("ID Obra: ");
                    String idFinalizar = scanner.nextLine();

                    restauracionService.finalizarRestauracion(idFinalizar);
                    break;

                case 3:
                    System.out.print("ID Obra: ");
                    String idConsulta = scanner.nextLine();

                    restauracionService
                            .obtenerRestauracionesOrdenadas(idConsulta)
                            .forEach(System.out::println);
                    break;

                case 0:
                    salir = true;
                    break;
            }
        }
    }
}