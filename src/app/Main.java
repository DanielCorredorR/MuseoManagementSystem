package app;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Museo;
import model.ObraArte;
import model.Usuario;
import model.Sala;

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

        // =========================
        // SALAS INICIALES
        // =========================
        Sala sala1 = new Sala("Sala Renacimiento", "Obras siglo XV");
        Sala sala2 = new Sala("Sala Moderna", "Obras siglo XX");

        salaRepository.save(sala1);
        salaRepository.save(sala2);

        // =========================
        // MENSAJE INICIAL
        // =========================
        System.out.println("=================================");
        System.out.println(" SISTEMA DE GESTION DEL MUSEO ");
        System.out.println(" Todos los valores se manejan en USD");
        System.out.println(" Las fechas deben ingresarse en formato YYYY-MM-DD");
        System.out.println("=================================");

        boolean sistemaActivo = true;

        while (sistemaActivo) {

            System.out.println("\n===== SISTEMA MUSEO =====");
            System.out.println("1. Iniciar sesion");
            System.out.println("0. Salir del sistema");

            System.out.print("Seleccione opcion: ");
            int opcionSistema = Integer.parseInt(scanner.nextLine());

            if (opcionSistema == 0) {
                sistemaActivo = false;
                break;
            }

            if (opcionSistema == 1) {

                System.out.println("\n=== LOGIN ===");

                System.out.print("Usuario: ");
                String username = scanner.nextLine();

                System.out.print("Password: ");
                String password = scanner.nextLine();

                Usuario usuario = authService.login(username, password);

                if (usuario == null) {
                    System.out.println("Credenciales incorrectas");
                    continue;
                }

                System.out.println("Bienvenido " + usuario.getRol());

                // restauraciones automaticas cada vez que entra al sistema
                restauracionService.verificarRestauracionesAutomaticas();

                switch (usuario.getRol()) {

                    case DIRECTOR:
                        menuDirector(scanner, museoRepository,
                                cesionService, cesionRepository, obraService);
                        break;

                    case VISITANTE:
                        menuVisitante(scanner, salaRepository, obraService);
                        break;

                    case RESTAURADOR:
                        menuRestaurador(scanner, restauracionService);
                        break;

                    case CATALOGADOR:
                        menuCatalogador(scanner, obraService, salaRepository);
                        break;

                    default:
                        System.out.println("Rol no reconocido");
                }
            }
        }

        System.out.println("Sistema cerrado correctamente");
    }

    // ==========================================================
    // MENU DIRECTOR
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
            System.out.println("0. Volver");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {

                case 1:

                    System.out.print("Nombre museo: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Ciudad: ");
                    String ciudad = scanner.nextLine();

                    Museo museo = new Museo(nombre, ciudad);
                    museoRepository.save(museo);

                    System.out.println("Museo registrado con ID: " + museo.getId());
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

                    System.out.print("Importe de cesion (USD): ");
                    double importe = Double.parseDouble(scanner.nextLine());

                    System.out.print("Fecha inicio (YYYY-MM-DD): ");
                    LocalDate inicio = LocalDate.parse(scanner.nextLine());

                    System.out.print("Fecha fin (YYYY-MM-DD): ");
                    LocalDate fin = LocalDate.parse(scanner.nextLine());

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

                    System.out.println("Valor total museo (USD): $" + total);
                    break;

                case 0:
                    salir = true;
                    break;
            }
        }
    }

    // ==========================================================
    // MENU VISITANTE
    // ==========================================================
    private static void menuVisitante(Scanner scanner,
                                      SalaRepository salaRepository,
                                      ObraService obraService) {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n=== MENU VISITANTE ===");
            System.out.println("1. Ver salas");
            System.out.println("2. Ver obras por sala");
            System.out.println("0. Volver");

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
                            obraService.listarPorSala(idSala);

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
    // MENU RESTAURADOR
    // ==========================================================
    private static void menuRestaurador(Scanner scanner,
                                        RestauracionService restauracionService) {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n=== MENU RESTAURADOR ===");
            System.out.println("1. Enviar obra a restauracion");
            System.out.println("2. Finalizar restauracion");
            System.out.println("3. Ver restauraciones de obra");
            System.out.println("0. Volver");

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

    // ==========================================================
    // MENU CATALOGADOR
    // ==========================================================
    private static void menuCatalogador(Scanner scanner,
                                        ObraService obraService,
                                        SalaRepository salaRepository) {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n=== MENU CATALOGADOR ===");
            System.out.println("1. Listar obras");
            System.out.println("0. Volver");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {

                case 1:
                    obraService.listarTodas()
                            .forEach(System.out::println);
                    break;

                case 0:
                    salir = true;
                    break;
            }
        }
    }
}