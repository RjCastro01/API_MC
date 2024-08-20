package com.mc.apimc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Main_controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Mire la consola, abajo;");
    }
}