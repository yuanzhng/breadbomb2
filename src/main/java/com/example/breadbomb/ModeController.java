package com.example.breadbomb;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class ModeController {
    @FXML
    private Button bread;

    @FXML
    private Button classic;

    public void breadPressed() {
        breadApplication.setBreadMode();
        try {
            breadApplication.switchToGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void classicPressed() {
        breadApplication.setClassic();
        try {
            breadApplication.switchToGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
