package com.example.breadbomb;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.ArrayList

public class AddPlayerController {
    private ArrayList<Player> players;
    @FXML
    private TextField playerOneName;
    @FXML
    private TextField playerTwoName;
    @FXML
    private TextField playerThreeName;
    @FXML
    private TextField playerFourName;
    @FXML
    private Button startButton;

    public void start() {
        if (playerOneName.getText().replaceAll(" ", "") != "") {
            players.add(new Player(playerOneName.getText()));
        }
        if (playerTwoName.getText().replaceAll(" ", "") != "") {
            players.add(new Player(playerTwoName.getText()));
        }
        if (playerThreeName.getText().replaceAll(" ", "") != "") {
            players.add(new Player(playerThreeName.getText()));
        }
        if (playerFourName.getText().replaceAll(" ", "") != "") {
            players.add(new Player(playerFourName.getText()));
        }
        try {
            breadApplication.switchToGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
