package com.example.breadbomb;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class AddPlayerController {
    private ArrayList<Player> players = new ArrayList<Player>();
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
    private Timeline buttonTimeline;

    public void start() {
        if (!playerOneName.getText().replaceAll(" ", "").equals("")) {
            players.add(new Player(playerOneName.getText()));
        }
        if (!playerTwoName.getText().replaceAll(" ", "").equals("")) {
            players.add(new Player(playerTwoName.getText()));
        }
        if (!playerThreeName.getText().replaceAll(" ", "").equals("")) {
            players.add(new Player(playerThreeName.getText()));
        }
        if (!playerFourName.getText().replaceAll(" ", "").equals("")) {
            players.add(new Player(playerFourName.getText()));
        }
        if (!players.isEmpty()) {
            try {
                breadApplication.switchToGame(players);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            startButton.setText("Not Enough Players!");
            buttonTimeline = new Timeline(new KeyFrame(
                    Duration.seconds(0.5),
                    ae -> startButton.setText("Start")));
            buttonTimeline.play();
        }
    }
}
