import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MainAWS {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Creación de la tabla y carga de datos desde el CSV
        crearTablaYBBDD();

        // Mostrar todas las batallas
        System.out.println("\n¿Quieres ver todas las batallas registradas? (s/n):");
        String respuesta = input.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            seleccionarTodasLasBatallas();
        }

        // Eliminar batallas de una casa específica
        System.out.println("\nDime el nombre de una casa para eliminar sus batallas:");
        String nombreCasa = input.nextLine();
        borrarBatallasPorCasa(nombreCasa);
    }

    public static void crearTablaYBBDD() {
        try (Connection connection = ConexionBBDD.getAWSConnection()) {
            System.out.println("Conexión OK");
            String crearSchema = "CREATE SCHEMA IF NOT EXISTS `BatallasGoT`";
            try (PreparedStatement pstmt = connection.prepareStatement(crearSchema)) {
                pstmt.executeUpdate();
            }

            crearTabla(connection);
            cargarCSV("ficheros/datos_got_completo.csv");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void crearTabla(Connection connection) {
        // Borrar la tabla si existe
        String borrarTabla = "DROP TABLE IF EXISTS BatallasGoT.Batallas";
        try (PreparedStatement pstmt = connection.prepareStatement(borrarTabla)) {
            pstmt.executeUpdate();
            System.out.println("Tabla 'BatallasGoT.Batallas' eliminada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Crear la tabla
        String crearTabla = "CREATE TABLE IF NOT EXISTS BatallasGoT.Batallas (" +
                "personaje_id INT, " +
                "nombre_personaje VARCHAR(100), " +
                "edad INT, " +
                "rol VARCHAR(50), " +
                "estado VARCHAR(20), " +
                "casa_id INT, " +
                "territorio VARCHAR(100), " +
                "nombre_casa VARCHAR(100), " +
                "lema VARCHAR(100), " +
                "escudo VARCHAR(50), " +
                "batalla_id INT, " +
                "nombre VARCHAR(100), " +
                "fecha INT, " +
                "lugar VARCHAR(100), " +
                "reino VARCHAR(100), " +
                "resultado VARCHAR(50), " +
                "clima VARCHAR(50)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

        try (PreparedStatement pstmt = connection.prepareStatement(crearTabla)) {
            pstmt.executeUpdate();
            System.out.println("Tabla 'BatallasGoT.Batallas' creada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cargarCSV(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            System.out.println("Introduciendo datos...");
            String linea = br.readLine(); // Ignorar encabezado
            linea = br.readLine();
            while (linea != null) {
                String[] leido = linea.split(",");
                aniadirBatalla(Integer.parseInt(leido[0]), leido[1], Integer.parseInt(leido[2]), leido[3], leido[4],
                        Integer.parseInt(leido[5]), leido[6], leido[7], leido[8], leido[9], Integer.parseInt(leido[10]),
                        leido[11], Integer.parseInt(leido[12]), leido[13], leido[14], leido[15], leido[16]);
                linea = br.readLine();
            }
            System.out.println("Datos introducidos correctamente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void aniadirBatalla(int personaje_id, String nombre_personaje, int edad, String rol, String estado,
                                      int casa_id, String territorio, String nombre_casa, String lema, String escudo,
                                      int batalla_id, String nombre, int fecha, String lugar, String reino,
                                      String resultado, String clima) {
        String insertar = "INSERT INTO BatallasGoT.Batallas VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConexionBBDD.getAWSConnection();
             PreparedStatement pstmt = connection.prepareStatement(insertar)) {
            pstmt.setInt(1, personaje_id);
            pstmt.setString(2, nombre_personaje);
            pstmt.setInt(3, edad);
            pstmt.setString(4, rol);
            pstmt.setString(5, estado);
            pstmt.setInt(6, casa_id);
            pstmt.setString(7, territorio);
            pstmt.setString(8, nombre_casa);
            pstmt.setString(9, lema);
            pstmt.setString(10, escudo);
            pstmt.setInt(11, batalla_id);
            pstmt.setString(12, nombre);
            pstmt.setInt(13, fecha);
            pstmt.setString(14, lugar);
            pstmt.setString(15, reino);
            pstmt.setString(16, resultado);
            pstmt.setString(17, clima);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void seleccionarTodasLasBatallas() {
        String select = "SELECT * FROM BatallasGoT.Batallas";
        try (Connection connection = ConexionBBDD.getAWSConnection();
             PreparedStatement pstmt = connection.prepareStatement(select);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("\nListado de todas las batallas:");
            boolean hayResultados = false;
            while (rs.next()) {
                System.out.println(rs.getInt("personaje_id") + ", " +
                        rs.getString("nombre_personaje") + ", " +
                        rs.getInt("edad") + ", " +
                        rs.getString("rol") + ", " +
                        rs.getString("estado") + ", " +
                        rs.getString("nombre_casa") + ", " +
                        rs.getString("nombre") + ", " +
                        rs.getInt("fecha") + ", " +
                        rs.getString("lugar") + ", " +
                        rs.getString("resultado") + ", " +
                        rs.getString("clima"));
                hayResultados = true;
            }
            if (!hayResultados) {
                System.out.println("No hay batallas registradas en la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void borrarBatallasPorCasa(String nombreCasa) {
        String delete = "DELETE FROM BatallasGoT.Batallas WHERE nombre_casa = ?";
        try (Connection connection = ConexionBBDD.getAWSConnection();
             PreparedStatement pstmt = connection.prepareStatement(delete)) {
            pstmt.setString(1, nombreCasa);
            int filasEliminadas = pstmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Todas las batallas de la casa " + nombreCasa + " han sido eliminadas correctamente.");
            } else {
                System.out.println("No se encontraron batallas de la casa " + nombreCasa + " para eliminar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
