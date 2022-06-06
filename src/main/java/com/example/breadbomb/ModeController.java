package com.example.breadbomb;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class ModeController {
    @FXML
    private Button bread;

    @FXML
    private Button classic;

    @FXML
    private Button back;

    private boolean multi;

    public void initialize(boolean multiplayer) {
        if (multiplayer) {
            multi = true;
        } else {
            multi = false;
        }
    }

    public void breadPressed() {
        breadApplication.setBreadMode();
        if (multi) {
            try {
                breadApplication.switchToAddPlayer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                breadApplication.switchToGame(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void classicPressed() {
        breadApplication.setClassic();
        if (multi) {
            try {
                breadApplication.switchToAddPlayer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                breadApplication.switchToGame(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void backPressed() {
        try {
            breadApplication.switchToMain();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
