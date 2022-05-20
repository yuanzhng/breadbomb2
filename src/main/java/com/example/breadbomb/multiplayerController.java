package com.example.breadbomb;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.*;

public class multiplayerController {
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
    private Label namelbl1;

    @FXML
    private Label namelbl2;

    @FXML
    private Label namelbl3;

    @FXML
    private Label namelbl4;

    private Label[] prevlbls = {prevlbl1, prevlbl2, prevlbl3, prevlbl4};


    @FXML
    private TextField inputfld;

    private String prompt;

    private ArrayList<String> typed = new ArrayList<String>();

    private ArrayList<String> possiblePrompts = new ArrayList<String>();

    private ArrayList<String> possibleOrders = new ArrayList<String>();

    private ArrayList<String> dictionary = new ArrayList<String>();

    private ArrayList<Player> activePlayers = new ArrayList<Player>();

    private int playerTurn;

    public void initialize(boolean breadMode) {
        activePlayers.add(new Player("yuan"));
        activePlayers.add(new Player("zhuang"));
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

    public void updateLives() {

    }

    public void cycleTurn() {
        if (playerTurn == activePlayers.size()) {
            playerTurn = 0;
        } else {
            playerTurn++;
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
            activePlayers.get(playerTurn).addScore(1);
            newPrompt();
            cycleTurn();
            typed.add(ipt);
        } else if (typed.contains(ipt)) {
            inputfld.setText("");
        } else if (!ipt.toLowerCase().contains(prompt.toLowerCase(Locale.ROOT))){
            inputfld.setText("");
        } else {
            inputfld.setText("");
        }
        updateLives();
    }
}