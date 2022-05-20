package com.example.breadbomb;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.*;

public class multiplayerController {
    Timeline autoPlayTimeline;
    Timeline updateTimerTimeline;
    @FXML
    private Label promptlbl;
    @FXML
    private Label prevlbl1;
    @FXML
    private Label prevlbl2;
    @FXML
    private Label prevlbl3;
    @FXML
    private Label prevlbl4;
    @FXML
    private Label liveslbl1;
    @FXML
    private Label liveslbl2;
    @FXML
    private Label liveslbl3;
    @FXML
    private Label liveslbl4;

    private ArrayList<Label> liveslbls = new ArrayList<Label>();
    private ArrayList<Label> prevlbls = new ArrayList<Label>();

    @FXML
    private TextField inputfld;

    private String prompt;

    private ArrayList<String> typed = new ArrayList<String>();

    private ArrayList<String> possiblePrompts = new ArrayList<String>();

    private ArrayList<String> possibleOrders = new ArrayList<String>();

    private ArrayList<String> dictionary = new ArrayList<String>();

    private ArrayList<Player> activePlayers = new ArrayList<Player>();

    private int currentTurn = 0;

    private long timeAvailable = 30000;
    private long startTime;
    private long totalSeconds;
    private long startGameTime = System.currentTimeMillis();

    public void initialize(boolean breadMode) {
        prevlbls.add(prevlbl1);
        prevlbls.add(prevlbl2);
        prevlbls.add(prevlbl3);
        prevlbls.add(prevlbl4);

        liveslbls.add(liveslbl1);
        liveslbls.add(liveslbl2);
        liveslbls.add(liveslbl3);
        liveslbls.add(liveslbl4);
        for (Label i : prevlbls) {
            i.setText("");
        }
        activePlayers.add(new Player("Player 1", 3));
        activePlayers.add(new Player("Player 2", 3));
        activePlayers.add(new Player("Player 3", 3));
        activePlayers.add(new Player("Player 4", 3));
        updateLives();
        try {
            File dictionaryObj = new File(breadApplication.class.getResource("dict.txt").getFile());
            Scanner dictionaryReader = new Scanner(dictionaryObj);
            while (dictionaryReader.hasNextLine()) {
                String data = dictionaryReader.nextLine();
                dictionary.add(data.toLowerCase());
            }
            dictionaryReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        readFile("dict.txt", dictionary);
        readFile("prompts.txt", possiblePrompts);
        if (breadMode) {
            System.out.println("BreadMode enabled...");
            readFile("orders.txt", possibleOrders);
        } else {
            possibleOrders.add("abcdefghijklmnopqrstuvwxyz");
        }
        newPrompt();
    }

    public void startTimer() {
        updateTimer();
        if (updateTimerTimeline == null) {
            updateTimerTimeline = new Timeline(new KeyFrame(
                    Duration.millis(1),
                    ae -> updateTimer()));
        }
        updateTimerTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimerTimeline.play();
    }

    @FXML
    public void giveUp() {
        timeAvailable = 30000;
        newPrompt();
        currentPlayer().removeLives(1);
        updateLives();
        if (checkGameOver()) {
            giveGameOver();
            return;
        }
        cycleTurn();
    }

    public Player currentPlayer() {
        return activePlayers.get(currentTurn);
    }

    public boolean checkGameOver() {
        if (currentPlayer().getLives() <= 0) {
            return true;
        }
        return false;
    }

    public void giveGameOver() {
        activePlayers.remove(currentTurn);
        return;
    }

    public void updateTimer() {
        totalSeconds = timeAvailable/1000 - (((System.currentTimeMillis() - startTime)) / 1000);

        if (totalSeconds <= 0) {
            check();
        } else {
            //availlbl.setText("" + totalSeconds);
        }
        if (checkGameOver()) {
            giveGameOver();

        }
    }
    public void updateLives() {
        for (int i = 0; i < activePlayers.size(); i++) {
            liveslbls.get(i).setText(Integer.toString(activePlayers.get(i).getLives()));
        }
    }

    public void cycleTurn() {
        if (currentTurn == activePlayers.size() - 1) {
            currentTurn = 0;
        } else {
            currentTurn++;
        }
    }

    public void newPrompt() {
        inputfld.setText("");
        int i = (int) (Math.random() * (possiblePrompts.size()));
        this.prompt = possiblePrompts.get(i).toUpperCase();
        promptlbl.setText(prompt);
    }

    public boolean isInDictionary(String s) {
        for (String c : dictionary) {
            if (s.toLowerCase().equals(c)) {
                return true;
            }
        }
        return false;
    }

    public String rawIpt(String s) {
        String j;
        j = s.replaceAll("[^A-Za-z]+", "");
        j = j.toLowerCase();
        return j;
    }

    public void readFile(String s, ArrayList<String> a) {
        try {
            File dictionaryObj = new File(breadApplication.class.getResource(s).getFile());
            Scanner dictionaryReader = new Scanner(dictionaryObj);
            while (dictionaryReader.hasNextLine()) {
                String data = dictionaryReader.nextLine();
                a.add(data.toLowerCase());
            }
            dictionaryReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void check() {
        String ipt = inputfld.getText();
        ipt = rawIpt(ipt);
        if (ipt.toLowerCase().contains(prompt.toLowerCase(Locale.ROOT)) && isInDictionary(ipt) && !typed.contains(ipt)) {
            currentPlayer().addScore(1);
            newPrompt();
            typed.add(ipt);
            prevlbls.get(currentTurn).setText(ipt.toUpperCase());
            cycleTurn();
        } else if (typed.contains(ipt)) {
            inputfld.setText("");
        } else if (!ipt.toLowerCase().contains(prompt.toLowerCase(Locale.ROOT))){
            inputfld.setText("");
        } else {
            inputfld.setText("");
        }
        updateLives();
    }
    public boolean gameIsWon()
    {
        return (activePlayers.size()==1);
    }
    public void win()
    {
        //endscreen
    }

}