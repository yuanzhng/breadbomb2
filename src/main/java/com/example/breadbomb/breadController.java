package com.example.breadbomb;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Scanner;

import java.util.*;

public class breadController {
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

    private int score = 0;
    private int lives = 3;

    private String prompt;

    private String prev;

    private ArrayList<String> alphabet;

    private ArrayList<String> typed = new ArrayList<String>();

    private ArrayList<String> possiblePrompts = new ArrayList<String>();

    private ArrayList<String> possibleOrders = new ArrayList<String>();

    private ArrayList<String> dictionary = new ArrayList<String>();

    private String order;

    private long timeAvailable = 30000;
    private long startTime;

    private long startGameTime = System.currentTimeMillis();

    public void initialize() {
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
        readFile("orders.txt", possibleOrders);
        restartbtn.setDisable(true);
        newPrompt();
        newOrder();
    }

    public void updateLives() {
        String s = "";
        for (int i = 0; i < lives; i++) {
            s += "<3 ";
        }
        liveslbl.setText("HP: " + s);
    }

    public void newPrompt() {
        inputfld.setText("");
        int i = (int) (Math.random() * (possiblePrompts.size()));
        this.prompt = possiblePrompts.get(i).toUpperCase();
        promptlbl.setText(prompt);
        startTime = System.currentTimeMillis();
        availlbl.setText("Time available: "+ timeAvailable/1000 + " seconds");
    }

    public void updateScore() {
        scorefld.setText("Score: " + score);
    }

    public void newOrder() {
        int g = (int) (Math.random() * (possibleOrders.size()) - 1);
        order = possibleOrders.get(g);
        System.out.println("order: " + order);
        orderlbl.setText("Order: " + order.toUpperCase());
    }

    @FXML
    public void giveUp() {
        lives--;
        timeAvailable = 30000;
        updateLives();
        showInfo();
        checkGameOver();
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
            int j = ((int) (Math.random() * possible.size()));
            info += possible.get(j) + "\n";
            possible.remove(j);
        }
        infolbl.setText("Words containing " + prompt.toUpperCase() + ":\n" + info);
        newPrompt();

    }

    public void checkGameOver() {
        if (lives <= 0) {
            scorefld.setText("GAME OVER");
            timelbl.setText("Time survived: " + ((System.currentTimeMillis() - startGameTime)/1000) + " seconds");
            availlbl.setText("Prompts answered: " + score);
            orderlbl.setText("");
            promptlbl.setText("---");
            inputfld.setDisable(true);
            giveUpBtn.setDisable(true);
            restartbtn.setDisable(false);
            return;
        }
    }

    public void restartGame() {
        score = 0;
        lives = 3;
        restartbtn.setDisable(true);
        inputfld.setDisable(false);
        giveUpBtn.setDisable(false);
        infolbl.setText("");
        startGameTime = System.currentTimeMillis();
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
        checkGameOver();
        if (timeElapsed() > timeAvailable) {
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
        ipt = ipt.replaceAll("[^A-Za-z]+", "");
        if (ipt.toLowerCase().contains(prompt.toLowerCase(Locale.ROOT)) && isInDictionary(ipt) && !typed.contains(ipt)) {
            for (int i = 0; i < ipt.length(); i++) {
                if (order.contains(ipt.substring(i,i+1))) {
                    order = order.replaceFirst(ipt.substring(i,i+1), "");
                }
            }
            if (order.replaceAll(" ", "").equals("")) {
                scorefld.setText("Order complete! +1 life");
                lives++;
                newOrder();
            }
            score += ipt.length();
            updateScore();
            orderlbl.setText("Order: " + order.toUpperCase());
            timelbl.setText("Time taken: " + timeElapsed()/1000 + " seconds");
            if (timeAvailable >= 5000) {
                timeAvailable -= 1000;
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
    }


}