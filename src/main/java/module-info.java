module eu.andreatt.ejercicior_dein {
    requires eu.andreatt.ejercicioq_dein;
    requires javafx.fxml;
    requires javafx.controls;

    // Exporta el paquete de la aplicación para que JavaFX pueda acceder a él
    exports eu.andreatt.ejercicior_dein.application;
    exports eu.andreatt.ejercicior_dein.controllers; // Si tienes un controlador, también es recomendable exportarlo

    opens eu.andreatt.ejercicior_dein.application to javafx.fxml;
    opens eu.andreatt.ejercicior_dein.controllers to javafx.fxml; // Esto permite la reflexión en FXML
}