package com.example.breadbomb;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class AddPlayerController {
    private Player[] players = new Player[4];
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
    @FXML
    private Button backButton;
    private Timeline buttonTimeline;

    public void start() {
        int pCount = 0;
        players[0] = new Player(playerOneName.getText(), 3);
        players[1] = new Player(playerTwoName.getText(), 3);
        players[2] = new Player(playerThreeName.getText(), 3);
        players[3] = new Player(playerFourName.getText(), 3);

        for (Player p : players) {
            if (!p.getName().replace(" ", "").equals("")) {
                pCount++;
            }
        }
        if (pCount >= 2) {
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
    public void back() {
        try {
            breadApplication.switchToMode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
