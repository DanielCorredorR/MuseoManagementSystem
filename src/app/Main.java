package app;

import service.ObraService;
import java.util.Scanner;
import repository.ObraRepository;
import repository.RestauracionRepository;
import model.ObraArte;

public class Main {

    public static void main(String[] args) {

        ObraRepository obraRepository = new ObraRepository();
        RestauracionRepository restauracionRepository = new RestauracionRepository();

        ObraService obraService =
                new ObraService(obraRepository, restauracionRepository);

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MUSEO MANAGEMENT SYSTEM ===");
            System.out.println("1. Registrar obra");
            System.out.println("2. Calcular valor total del museo");
            System.out.println("3. Verificar restauraciones automaticas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    scanner.nextLine(); // limpiar buffer

                    System.out.print("Titulo: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Autor: ");
                    String autor = scanner.nextLine();

                    System.out.print("Anio de creacion: ");
                    int anio = scanner.nextInt();

                    System.out.print("Valor economico: ");
                    double valor = scanner.nextDouble();

                    ObraArte obra = new ObraArte(titulo, autor, anio, valor);

                    obraService.registrarObra(obra);

                    System.out.println("Obra registrada correctamente.");
                    break;
                    

                case 2:
                    double total = obraService.calcularValorTotalMuseo();
                    System.out.println("Valor total del museo: " + total);
                    break;

                case 3:
                    obraService.verificarRestauracionesAutomaticas();
                    System.out.println("Verificacion completada.");
                    break;

                case 4:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 4);
    }
}