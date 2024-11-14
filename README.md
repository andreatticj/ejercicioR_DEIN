# Proyecto `eu.andreatt.ejercicior_dein` (NO FUNCIONA)

## Descripción
Esta aplicación es una aplicación de temporizador con JavaFX. Incluye un temporizador en la interfaz gráfica que desactiva botones una vez finalizado.

## Estructura
- `application/R.java`: Clase principal que lanza la aplicación.
- `controllers/RController.java`: Controlador que maneja los eventos de la interfaz.
- `fxml/R.fxml`: Define la interfaz de usuario.
- `module-info.java`: Configura los módulos requeridos para Java y JavaFX.

## Requisitos
- Java 11 o superior
- JavaFX configurado en el `module-path`

## Configuración y Ejecución
### Compilar
1. Asegúrate de que todas las dependencias de JavaFX están en el `module-path`.
2. Ejecuta:
   ```sh
   javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -d out src/eu/andreatt/ejercicior_dein/**/*.java
