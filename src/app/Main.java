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
                        menuRestaurador(scanner,obraService, restauracionService);
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
    private static void menuRestaurador(Scanner scanner,ObraService obraService,
                                        RestauracionService restauracionService) {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n=== MENU RESTAURADOR ===");
            System.out.println("1. Enviar obra a restauracion");
            System.out.println("2. Finalizar restauracion");
            System.out.println("3. Ver restauraciones de obra");
            System.out.println("4. Ver obras en restauracion");
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
                case 4:

                     List<ObraArte> obras =
                        obraService.obrasEnRestauracion();

                    if(obras.isEmpty()){
                    System.out.println("No hay obras en restauracion");
                    break;
                        }

                    for(ObraArte obra : obras){
                    
                        System.out.println(obra);

                        obra.getRestauraciones().forEach(r -> {

                        System.out.println(
                             "   Inicio: " + r.getFechaInicio()
                            + " | Fin: " + r.getFechaFin()
                        );

                    });

                    }

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
        System.out.println("1. Registrar CUADRO");
        System.out.println("2. Registrar ESCULTURA");
        System.out.println("3. Registrar OBJETO");
        System.out.println("4. Listar obras");
        System.out.println("0. Volver");

        int opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {

            // ==============================
            // REGISTRAR CUADRO
            // ==============================
            case 1:

                System.out.println("Registro de CUADRO");

                System.out.print("Titulo: ");
                String tituloC = scanner.nextLine();

                System.out.print("Autor: ");
                String autorC = scanner.nextLine();

                System.out.print("Periodo: ");
                String periodoC = scanner.nextLine();

                System.out.print("Valor (USD): ");
                double valorC = Double.parseDouble(scanner.nextLine());

                System.out.print("Fecha creacion (YYYY-MM-DD): ");
                java.time.LocalDate fechaCreacionC =
                        java.time.LocalDate.parse(scanner.nextLine());

                System.out.print("Fecha entrada museo (YYYY-MM-DD): ");
                java.time.LocalDate fechaEntradaC =
                        java.time.LocalDate.parse(scanner.nextLine());

                System.out.print("Estilo: ");
                String estilo = scanner.nextLine();

                System.out.print("Tecnica: ");
                String tecnica = scanner.nextLine();

                model.Cuadro cuadro = new model.Cuadro(
                        tituloC,
                        autorC,
                        periodoC,
                        valorC,
                        fechaCreacionC,
                        fechaEntradaC,
                        estilo,
                        tecnica
                );

                obraService.registrarObra(cuadro);

                System.out.println("CUADRO registrado con ID: " + cuadro.getId());
                break;


            // ==============================
            // REGISTRAR ESCULTURA
            // ==============================
            case 2:

                System.out.println("Registro de ESCULTURA");

                System.out.print("Titulo: ");
                String tituloE = scanner.nextLine();

                System.out.print("Autor: ");
                String autorE = scanner.nextLine();

                System.out.print("Periodo: ");
                String periodoE = scanner.nextLine();

                System.out.print("Valor (USD): ");
                double valorE = Double.parseDouble(scanner.nextLine());

                System.out.print("Fecha creacion (YYYY-MM-DD): ");
                java.time.LocalDate fechaCreacionE =
                        java.time.LocalDate.parse(scanner.nextLine());

                System.out.print("Fecha entrada museo (YYYY-MM-DD): ");
                java.time.LocalDate fechaEntradaE =
                        java.time.LocalDate.parse(scanner.nextLine());

                System.out.print("Estilo: ");
                String estiloE = scanner.nextLine();

                System.out.print("Material: ");
                String material = scanner.nextLine();

                model.Escultura escultura = new model.Escultura(
                        tituloE,
                        autorE,
                        periodoE,
                        valorE,
                        fechaCreacionE,
                        fechaEntradaE,
                        estiloE,
                        material
                );

                obraService.registrarObra(escultura);

                System.out.println("ESCULTURA registrada con ID: " + escultura.getId());
                break;


            // ==============================
            // REGISTRAR OBJETO
            // ==============================
            case 3:

                System.out.println("Registro de OBJETO");

                System.out.print("Titulo: ");
                String tituloO = scanner.nextLine();

                System.out.print("Autor: ");
                String autorO = scanner.nextLine();

                System.out.print("Periodo: ");
                String periodoO = scanner.nextLine();

                System.out.print("Valor (USD): ");
                double valorO = Double.parseDouble(scanner.nextLine());

                System.out.print("Fecha creacion (YYYY-MM-DD): ");
                java.time.LocalDate fechaCreacionO =
                        java.time.LocalDate.parse(scanner.nextLine());

                System.out.print("Fecha entrada museo (YYYY-MM-DD): ");
                java.time.LocalDate fechaEntradaO =
                        java.time.LocalDate.parse(scanner.nextLine());

                model.Objeto objeto = new model.Objeto(
                        tituloO,
                        autorO,
                        periodoO,
                        valorO,
                        fechaCreacionO,
                        fechaEntradaO
                );

                obraService.registrarObra(objeto);

                System.out.println("OBJETO registrado con ID: " + objeto.getId());
                break;


            // ==============================
            // LISTAR OBRAS/BUSCAR
            // ==============================
            case 4:

                obraService.listarTodas()
                        .forEach(System.out::println);

                break;
            case 5:

            System.out.print("Titulo de la obra a buscar: ");
            String tituloBusqueda = scanner.nextLine();

            List<ObraArte> resultados =
                    obraService.buscarPorTitulo(tituloBusqueda);

            if(resultados.isEmpty()){
                System.out.println("No se encontraron obras");
                    }else{
                resultados.forEach(System.out::println);
                }

                break;

            case 0:
                salir = true;
                break;
        }
    }
}
}