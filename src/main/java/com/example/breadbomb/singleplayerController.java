package com.example.breadbomb;



import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


import java.util.*;

public class singleplayerController {
    private int confirmQuit = 0;
    Timeline quitTime;
    Timeline autoPlayTimeline;
    Timeline updateTimerTimeline;

    @FXML
    private Label promptlbl;

    @FXML
    private Label prevlbl;

    @FXML
    private Label orderlbl;

    @FXML
    private Label timelbl;

    @FXML
    private Label availlbl;

    @FXML
    private Label liveslbl;

    @FXML
    private Label infolbl;

    @FXML
    private TextField inputfld;

    @FXML
    private Label scorefld;

    @FXML
    private Button giveUpBtn;

    @FXML
    private Button restartbtn;

    @FXML
    private Button quitButton;

    private int score = 0;
    private int lives = 3;

    private String prompt;

    private ArrayList<String> alphabet;

    private ArrayList<String> typed = new ArrayList<String>();

    private ArrayList<String> possiblePrompts = new ArrayList<String>();

    private ArrayList<String> possibleOrders = new ArrayList<String>();

    private ArrayList<String> dictionary = new ArrayList<String>();

    private String order;

    private long timeAvailable = 30000;
    private long startTime;
    private long totalSeconds;
    private long startGameTime = System.currentTimeMillis();

    private boolean startSandwich = true;
    private boolean breadMode;
    private int sandwichLength = 0;
    private int idealSandwichLength = 0;
    private int orderCount = 0;

    public void initialize(boolean bread) {
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
        if (bread) {
            System.out.println("BreadMode enabled...");
            readFile("orders.txt", possibleOrders);
            breadMode = true;

        } else {
            possibleOrders.add("abcdefghijklmnopqrstuvwxyz");
        }
        restartbtn.setDisable(true);
        newPrompt();
        newOrder();
        timelbl.setText("");
        infolbl.setText("");

    }

    public void updateLives() {
        String s = "";
        for (int i = 0; i < lives; i++) {
            s += "<3 ";
        }
        liveslbl.setText(s);
    }

    public void newPrompt() {
        inputfld.setText("");
        int i = (int) (Math.random() * (possiblePrompts.size()));
        this.prompt = possiblePrompts.get(i).toUpperCase();
        promptlbl.setText(prompt);
        startTime = System.currentTimeMillis();
        startTimer();
    }

    public void updateScore() {
        scorefld.setText("Score: " + score);
    }

    public int calcScore(String s) {
        int k = 0;
        for (int i = 0; i < s.length(); i++) {
            String d = s.substring(i, i+1).toLowerCase();
            switch (d) {
                case "a":
                case "e":
                case "i":
                case "l":
                case "n":
                case "o":
                case "r":
                case "s":
                case "t":
                case "u": k++; break;
                case "d":
                case "g": k += 2; break;
                case "b":
                case "c":
                case "m":
                case "p": k += 3; break;
                case "f":
                case "h":
                case "v":
                case "w":
                case "y": k += 4; break;
                case "k": k += 5; break;
                case "j":
                case "x": k += 8; break;
                case "q":
                case "z": k += 10; break;
            }
        }
        return k;
    }

    public void updateTimeTaken() {
        int s = calcScore(rawIpt(inputfld.getText()));
        timelbl.setText("Took " + timeElapsed()/1000 + " seconds\n" +
                rawIpt(inputfld.getText().toUpperCase()) + "\n+" + s);
        System.out.println("word score: " + s);
    }

    public void newOrder() {
        if (breadMode) {
            if (startSandwich) {
                order = "bread";
                System.out.println("Order: bread");
                orderlbl.setText("Order: BREAD");
                startSandwich = false;
            } else if (sandwichLength <= idealSandwichLength) {
                int g = (int) (Math.random() * (possibleOrders.size()) - 1);
                order = possibleOrders.get(g);
                System.out.println("Order: " + order);
                orderlbl.setText("Order: " + order.toUpperCase());
                sandwichLength++;
            } else {
                order = "bread";
                System.out.println("Order: bread");
                orderlbl.setText("Order: BREAD");
                sandwichLength = 0;
                startSandwich = true;
            }
        } else {
            int g = (int) (Math.random() * (possibleOrders.size()) - 1);
            order = possibleOrders.get(g);
            System.out.println("Order: " + order);
            orderlbl.setText("Order: " + order.toUpperCase());
        }
    }

    @FXML
    public void giveUp() {
        lives--;
        timeAvailable = 30000;
        updateLives();
        showInfo();
        if (checkGameOver()) {
            giveGameOver();
            return;
        }
    }

    public void showInfo() {
        String info = "";
        System.out.println("prompt skipped: " + prompt);
        ArrayList<String> possible = new ArrayList<>();
        for (String c : dictionary) {
            if (c.toLowerCase().contains(prompt.toLowerCase())) {
                possible.add(c);
            }
        }
        for (int i = 0; i < 10; i++) {
            int j = ((int) (Math.random() * (possible.size() - 1)));
            info += possible.get(j) + "\n";
            possible.remove(j);
        }
        infolbl.setText("Words containing " + prompt.toUpperCase() + ":\n" + info);
        newPrompt();

    }

    public boolean checkGameOver() {
        if (lives <= 0) {
            return true;
        }
        return false;
    }

    public void giveGameOver() {
        updateTimerTimeline.stop();
        scorefld.setText("");
        timelbl.setText("Time survived: " + ((System.currentTimeMillis() - startGameTime)/1000) + " seconds");
        availlbl.setText("Final score: " + score);
        orderlbl.setText("");
        promptlbl.setText("---");
        inputfld.setDisable(true);
        giveUpBtn.setDisable(true);
        restartbtn.setDisable(false);
        return;
    }

    public void restartGame() {
        score = 0;
        lives = 3;
        restartbtn.setDisable(true);
        inputfld.setDisable(false);
        giveUpBtn.setDisable(false);
        timelbl.setText("");
        infolbl.setText("");
        startGameTime = System.currentTimeMillis();
        typed.clear();
        updateScore();
        updateLives();
        newPrompt();
        newOrder();
    }

    public long timeElapsed() {
        return System.currentTimeMillis() - startTime;
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
    public void nextOrder() {
        inputfld.setText("");
        int i = (int) (Math.random() * (possiblePrompts.size()));
        this.prompt = possiblePrompts.get(i).toUpperCase();
        promptlbl.setText(prompt);
        startTime = System.currentTimeMillis();
        startTimer();
    }
    public void check() {
        if (totalSeconds<=0) {
            scorefld.setText("Time's up!");
            lives--;
            updateLives();
            timeAvailable = 30000;
            showInfo();
            newPrompt();
            return;
        }
        infolbl.setText("");
        String ipt = inputfld.getText();
        ipt = rawIpt(ipt);
        if (ipt.toLowerCase().contains(prompt.toLowerCase(Locale.ROOT)) && isInDictionary(ipt) && !typed.contains(ipt)) {
            for (int i = 0; i < ipt.length(); i++) {
                if (order.contains(ipt.substring(i,i+1))) {
                    order = order.replaceFirst(ipt.substring(i,i+1), "");
                }
            }
            if (order.replaceAll(" ", "").equals("")) {
                if (breadMode) {
                    if (orderCount == idealSandwichLength + 2) {
                        scorefld.setText("Sandwich complete! +1 life");
                        lives++;
                        orderCount = 0;
                        idealSandwichLength++;
                    } else {
                        orderCount++;
                    }
                } else {
                    scorefld.setText("Order complete! +1 life");
                    lives++;
                }
                newOrder();
            }
            score += calcScore(ipt);
            updateScore();
            orderlbl.setText("Order: " + order.toUpperCase());
            updateTimeTaken();
            if (timeAvailable >= 5000) {
                timeAvailable *= 0.90;
            }
            newPrompt();
            typed.add(ipt);
        } else if (typed.contains(ipt)) {
            inputfld.setText("");
            scorefld.setText("Word already used!");
        } else if (!ipt.toLowerCase().contains(prompt.toLowerCase(Locale.ROOT))){
            inputfld.setText("");
            scorefld.setText("Doesn't contain prompt!");
        } else {
            inputfld.setText("");
            scorefld.setText("Invalid word!");
        }
        updateLives();
        if (checkGameOver()) {
            giveGameOver();
            return;
        }
    }
    public void startTimer() {
        updateTimer();
        if (updateTimerTimeline == null) {
            updateTimerTimeline = new Timeline(new KeyFrame(
                    Duration.millis(1000),
                    ae -> updateTimer()));
        }
        updateTimerTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimerTimeline.play();
    }

    public void updateTimer() {
        totalSeconds = timeAvailable/1000 - (((System.currentTimeMillis() - startTime)) / 1000);

        if (totalSeconds <= 0) {
            check();
    } else {
            availlbl.setText("" + totalSeconds);
        }
        if (checkGameOver()) {
            giveGameOver();

        }
    }

    public void quit() {
        quitButton.setText("Really?");
        confirmQuit++;
        if (confirmQuit == 2) {
            try {
                breadApplication.switchToMain();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
             quitTime = new Timeline(new KeyFrame(
                    Duration.seconds(5),
                    ae -> quitButton.setText("Quit")),
                     new KeyFrame(
                            Duration.seconds(5),
                     ae -> confirmQuit--));
             quitTime.play();
        }
    }
}