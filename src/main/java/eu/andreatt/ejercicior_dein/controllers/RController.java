package eu.andreatt.ejercicior_dein.controllers;

import eu.andreatt.ejercicioq_dein.Temporizador;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class RController {

    @FXML
    private Button btnEnviar1;

    @FXML
    private Button btnEnviar2;

    @FXML
    private Pane temporizadorPane; // Pane para insertar el Temporizador

    private Temporizador temporizador;

    @FXML
    public void initialize() {
        temporizador = new Temporizador();
        temporizador.setTiempo(1); // Establece el tiempo de cuenta atrás en minutos
        temporizador.iniciarCuentaAtras();

        // Añadir el temporizador a la interfaz
        temporizadorPane.getChildren().add(temporizador);

        // Listener para deshabilitar botones cuando el temporizador termine
        temporizador.finProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
            if (newVal) {
                btnEnviar1.setDisable(true);
                btnEnviar2.setDisable(true);
            }
        });
    }

    @FXML
    void enviar(ActionEvent event) {
        // Acción al presionar los botones Enviar
    }
}
