import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        //manejar excepciones
        try {
            Map<String, Integer> registros = new HashMap<>();//Crea un HashMap llamado registros que se utiliza para almacenar los nombres de los estudiantes y sus calificaciones.
            Scanner scanner = new Scanner(System.in);
            boolean continuar = true;

            while (continuar) {//menu
                System.out.println("************************************************");
                System.out.println("1: Ingresar estudiante");
                System.out.println("2. Ver estudiantes con calificación mayor que el promedio");
                System.out.println("3. Ver estudiantes con calificación menor o igual que el promedio");
                System.out.println("4. Salir");
                System.out.print("Elija una opción: ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("************************************************");
                        System.out.print("Ingrese el nombre del estudiante: ");
                        String nombre = scanner.next();
                        System.out.print("Ingrese la calificación del estudiante: ");
                        int calificacion = scanner.nextInt();
                        registros.put(nombre, calificacion);
                        guardarEstudianteEnArchivo("todos_los_estudiantes.txt", nombre, calificacion);//llama la funcion, y se le pasa el nombre del archivo txt, el nombre y calificacion
                        break;

                    case 2:
                        mostrarEstudiantesConCalificacionMayorPromedio("todos_los_estudiantes.txt");
                        break;

                    case 3:
                        mostrarEstudiantesConCalificacionMenorPromedio("todos_los_estudiantes.txt");
                        break;

                    case 4:
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo");
            e.printStackTrace();
        }
    }

    private static void guardarEstudianteEnArchivo(String fileName, String nombre, int calificacion) throws IOException {
        //try garantiza que los recursos se cierren automaticamenete
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {//crea un nuevo filewrite con el nombre filemname
            //el true indica que se abre el archivo en modo edicion
            bufferedWriter.write(nombre + "," + calificacion);
            bufferedWriter.newLine();// para agregar el nuevo dato en una linea diferente
        }
    }

    private static void mostrarEstudiantesConCalificacionMayorPromedio(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            Map<String, Integer> registros = new HashMap<>();
            String line;
            int sumaCalificaciones = 0;
            int cantidadEstudiantes = 0;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                String nombre = parts[0];
                int calificacion = Integer.parseInt(parts[1]);
                registros.put(nombre, calificacion);
                sumaCalificaciones += calificacion;
                cantidadEstudiantes++;
            }

            if (cantidadEstudiantes > 0) {
                double promedio = (double) sumaCalificaciones / cantidadEstudiantes;
                System.out.printf("Promedio de calificaciones: %.2f%n", promedio);
                for (Map.Entry<String, Integer> entry : registros.entrySet()) {
                    if (entry.getValue() > promedio) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                    }
                }
            } else {
                System.out.println("No hay estudiantes registrados.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
    }

    private static void mostrarEstudiantesConCalificacionMenorPromedio(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            Map<String, Integer> registros = new HashMap<>();
            String line;
            int sumaCalificaciones = 0;
            int cantidadEstudiantes = 0;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                String nombre = parts[0];
                int calificacion = Integer.parseInt(parts[1]);
                registros.put(nombre, calificacion);
                sumaCalificaciones += calificacion;
                cantidadEstudiantes++;
            }

            if (cantidadEstudiantes > 0) {
                double promedio = (double) sumaCalificaciones / cantidadEstudiantes;
                System.out.printf("Promedio de calificaciones: %.2f%n", promedio);
                for (Map.Entry<String, Integer> entry : registros.entrySet()) {
                    if (entry.getValue() <= promedio) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                    }
                }
            } else {
                System.out.println("No hay estudiantes registrados.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
    }
}
