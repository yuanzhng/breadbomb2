package com.example.breadbomb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javafx.animation.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.util.*;

import static java.lang.Integer.decode;

public class multiplayerController {
    Timeline sandwichScaleTimeline;

    Timeline autoPlayTimeline;
    Timeline updateTimerTimeline;
    Timeline sandwichShowTime;
    private double startAngle=180;
    private static Scene winScene;
    @FXML
    private Label masterorderlbl;
    @FXML
    private Shape circle;
    @FXML
    private Button restartbtn;
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
    @FXML
    private Label orderlbl1;
    @FXML
    private Label orderlbl2;
    @FXML
    private Label orderlbl3;
    @FXML
    private Label orderlbl4;

    @FXML
    private Label grandorderlbl;
    private int confirmQuit = 0;
    Timeline quitTime;
    @FXML
    private Label currentPlayerlbl;
    @FXML
    private Button giveupbtn;

    @FXML
    private Button quitButton;
    @FXML
    private Label orderlbl;
    @FXML
    private Label sandwichDisplay;
    @FXML
    private Label scorefld;

    @FXML
    private Label availlbl;
    @FXML
    private Label infolbl;
    @FXML
    private Label namelbl1;
    @FXML
    private Label namelbl2;
    @FXML
    private Label namelbl3;
    @FXML
    private Label namelbl4;

    private ArrayList<Label> namelbls = new ArrayList<Label>();
    private ArrayList<Label> liveslbls = new ArrayList<Label>();
    private ArrayList<Label> prevlbls = new ArrayList<Label>();
    @FXML
    private ImageView sandwich;
    @FXML
    private ImageView arrow;
    private ArrayList<Label> orderlbls = new ArrayList<Label>();

    @FXML
    private TextField inputfld;

    private String prompt;

    private ArrayList<String> currentSandwich = new ArrayList<String>();

    private ArrayList<String> typed = new ArrayList<String>();

    private ArrayList<String> possiblePrompts = new ArrayList<String>();

    private ArrayList<String> possibleOrders = new ArrayList<String>();

    private ArrayList<String> dictionary = new ArrayList<String>();

    private ArrayList<Player> activePlayers = new ArrayList<Player>();
    private ScaleTransition scaleUp=new ScaleTransition(Duration.millis(500),sandwich);

    private ScaleTransition scaleDown=new ScaleTransition(Duration.millis(500),sandwich);
    private String order;

    private boolean breadMode;
    private boolean startSandwich;
    private boolean sandwichDone;
    private int sandwichLength;
    private int idealSandwichLength;

    private boolean isGrace;
    private int currentTurn = 0;
    private int turns = 0;

    private long timeAvailable = 30000;
    private long startTime;
    private long totalSeconds;
    private long startGameTime = System.currentTimeMillis();

    private static boolean enoughPlayers;

    public void initialize(boolean bread, Player[] players) {
        startSandwich = true;
        sandwichDone = false;
        sandwichLength = 0;
        idealSandwichLength = 1;

        namelbls.add(namelbl1);
        namelbls.add(namelbl2);
        namelbls.add(namelbl3);
        namelbls.add(namelbl4);
        prevlbls.add(prevlbl1);
        prevlbls.add(prevlbl2);
        prevlbls.add(prevlbl3);
        prevlbls.add(prevlbl4);
        liveslbls.add(liveslbl1);
        liveslbls.add(liveslbl2);
        liveslbls.add(liveslbl3);
        liveslbls.add(liveslbl4);
        orderlbls.add(orderlbl1);
        orderlbls.add(orderlbl2);
        orderlbls.add(orderlbl3);
        orderlbls.add(orderlbl4);
        for (Label l : prevlbls) {
            l.setText("");
        }
        for (Label i : liveslbls) {
            i.setText("");
        }
        for (int i = 0; i < players.length; i++) {
            if (!players[i].getName().replaceAll(" ", "").equals("")) {
                activePlayers.add(players[i]);
            }
        }
        namelbl1.setText(players[0].getName());
        namelbl2.setText(players[1].getName());
        namelbl3.setText(players[2].getName());
        namelbl4.setText(players[3].getName());
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
        if (bread) {
            breadMode = true;
            System.out.println("BreadMode enabled...");
            readFile("orders.txt", possibleOrders);
        }
        startTime = System.currentTimeMillis();
        Arc path= new Arc();
        path.setCenterX(175);
        path.setCenterY(29);
        path.setRadiusX(98);
        path.setRadiusY(98);



        if(activePlayers.get(currentTurn).getId()==0)
            startAngle=180;
        if(activePlayers.get(currentTurn).getId()==1)
            startAngle=90;
        if(activePlayers.get(currentTurn).getId()==2)
            startAngle=0;
        if(activePlayers.get(currentTurn).getId()==3)
            startAngle=270;

        path.setStartAngle(startAngle);
        path.setLength(0.0001);
        PathTransition move= new PathTransition();
        move.setPath(path);
        move.setInterpolator(Interpolator.LINEAR);
        move.setNode(arrow);
        move.setDuration(Duration.seconds(0.001));
        //move.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        RotateTransition rt = new RotateTransition(Duration.millis(5), arrow);

        rt.setByAngle(.0001);
        rt.setInterpolator(Interpolator.LINEAR);
        /*RotateTransition initial= new RotateTransition(Duration.millis(5),arrow);
        initial.setByAngle(startAngle);
        initial.setInterpolator(Interpolator.LINEAR);
        initial.play();

         */
        rt.play();
        move.play();
        newPrompt();
        newOrder();
        startTimer();
        infolbl.setText("");
        pump(sandwich);
    }
    public void pump(ImageView sandwich)
    {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.4), sandwich);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);

        scaleTransition.setToX(1.3);
        scaleTransition.setToY(1.3);

        scaleTransition.setInterpolator(Interpolator.EASE_OUT);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.play();
    }
    public void rotateSandwich()
    {
        RotateTransition rotate=new RotateTransition(Duration.millis(500),sandwich);
        rotate.setByAngle(360);
        rotate.setInterpolator(Interpolator.EASE_OUT);
        rotate.play();
    }
    public void rotateArrow()
    {
        double angle = 0;
        if (nextPlayer().getId()==0)
            angle=180;
        if (nextPlayer().getId()==1)
            angle=90;
        if (nextPlayer().getId()==2)
            angle=0;
        if (nextPlayer().getId()==3)
            angle=270;
        /*Rotate rotate = new Rotate();
        rotate.setPivotX(170);
        rotate.setPivotY(29);
        rotate.setAngle(90);
        arrow.getTransforms().addAll(rotate);*/
        Arc path= new Arc();
        path.setCenterX(175);
        path.setCenterY(29);
        path.setRadiusX(98);
        path.setRadiusY(98);
       /* double startAngle=45;
        if(activePlayers.get(currentTurn).getId()==0)
            startAngle=180;
        if(activePlayers.get(currentTurn).getId()==1)
            startAngle=90;
        if(activePlayers.get(currentTurn).getId()==2)
            startAngle=0;
        if(activePlayers.get(currentTurn).getId()==3)
            startAngle=270;


        */

        path.setStartAngle(startAngle);
        double length=angle-startAngle;
        if (length>0)
            length=length-360;

        path.setLength(length);
        PathTransition move= new PathTransition();
        move.setPath(path);
        move.setInterpolator(Interpolator.LINEAR);
        move.setNode(arrow);
        move.setDuration(Duration.seconds(0.25));
//move.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        RotateTransition rt = new RotateTransition(Duration.millis(250), arrow);
        rt.setByAngle(-length);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
        move.play();
        rotateSandwich();
        startAngle=angle;
    }
        public void startTimer () {
            updateTimer();
            if (updateTimerTimeline == null) {
                updateTimerTimeline = new Timeline(new KeyFrame(
                        Duration.millis(1000),
                        ae -> updateTimer()));
            }
            updateTimerTimeline.setCycleCount(Timeline.INDEFINITE);
            updateTimerTimeline.play();
        }
        public void pauseTimer() {
            updateTimerTimeline.pause();
        }
        @FXML
        public void giveUp () {
            timeAvailable = 30000;
            currentPlayer().removeLives(1);
            lifeLossAnimation();
            updateLives();
            updateOrders();
            System.out.println(currentPlayer().getName() + " Lives: " + currentPlayer().getLives());
            if (checkZeroLives()) {
                giveDeath();
            }
            showInfo();
            newPrompt();
            cycleTurn();
        }

        public Player currentPlayer() {
            return activePlayers.get(currentTurn);
        }

        public Player nextPlayer() {
            return activePlayers.get((currentTurn + 1) % activePlayers.size());
        }

        public boolean checkGameOver () {
            if (activePlayers.size() == 1) {
                currentPlayerlbl.setText(currentPlayer().getName() + " wins!");
                giveupbtn.setDisable(true);
                return true;
            }
            return false;
        }

        public boolean playerCheck() {
            return enoughPlayers;
        }

        public boolean checkZeroLives () {
            if (currentPlayer().getLives() <= 0) {
                return true;
            }
            return false;
        }

        public void promptNewPlayer(int i) {
            TextInputDialog in = new TextInputDialog("");
            in.setTitle("Add PLAYER " + (i + 1));
            in.setHeaderText("What is PLAYER " + (i + 1) + "'s name? (Leave blank if not playing)");
            in.setContentText("Name:");

            Optional<String> b = in.showAndWait();
            String out = b.get();
            if (!out.equals("")) {
                Player n = new Player(out, 3, i);
                activePlayers.add(n);
            }
            namelbls.get(i).setText(out);
        }

    public void giveDeath() {
        namelbls.get(currentTurn).setText("");
        liveslbls.get(currentTurn).setText("");
        prevlbls.get(currentTurn).setText("");
        orderlbls.get(currentTurn).setText("");
        namelbls.remove(currentTurn);
        activePlayers.remove(currentTurn);
        liveslbls.remove(currentTurn);
        prevlbls.remove(currentTurn);
        orderlbls.remove(currentTurn);
        if (currentTurn == 0) {
            currentTurn = activePlayers.size() - 1;
            return;
        }
        currentTurn--;
        currentPlayerlbl.setText("Time's up!");
        makeGrace();
        return;
    }

    public void updateTimer() {
        totalSeconds = timeAvailable/1000 - (((System.currentTimeMillis() - startTime)) / 1000);

        if (totalSeconds <= 0) {
            damageCurrent(1);
            check();
            updateLives();
            showInfo();
        } else {
            availlbl.setText(totalSeconds + "s");
        }
        if (checkZeroLives()) {
            giveDeath();

        }
    }

    public void damageCurrent(int i) {
        currentPlayer().removeLives(i);
        timeAvailable = 30000;
        lifeLossAnimation();

        cycleTurn();
    }
    public void lifeLossAnimation()
    {
        Color start=Color.web("ff3d3d");
        Color end=Color.web("5c9b9f");

        FillTransition fill=new FillTransition(Duration.millis(300),circle);
        fill.setInterpolator(Interpolator.EASE_OUT);
        fill.setFromValue(start);
        fill.setToValue(end);
        fill.setAutoReverse(true);
      fill.setCycleCount(1);
        fill.play();

    }
    public void lifeGainAnimation()
    {
        Color start=Color.web("4cff3d");
        Color end=Color.web("5c9b9f");

        FillTransition fill=new FillTransition(Duration.millis(300),circle);
        fill.setInterpolator(Interpolator.EASE_OUT);
        fill.setFromValue(start);
        fill.setToValue(end);
        fill.setAutoReverse(true);
        fill.setCycleCount(1);
        fill.play();

    }

    public void updateLives() {
        for (int i = 0; i < activePlayers.size(); i++) {
            String s = "";
            for (int j = 0; j < activePlayers.get(i).getLives(); j++) {
                s += "❤";
            }
            liveslbls.get(i).setText(s);
        }
    }
    public void updateCurrentPlayerLabel() {
        currentPlayerlbl.setText(currentPlayer().getName());
    }

    public void updateOrders() {
        if (breadMode) {
            for (Player p : activePlayers) {
                p.setOrder(order);
            }
            for (Label l : orderlbls) {
                l.setText("");
            }
            masterorderlbl.setText(order);
        } else {
            orderlbls.get(currentTurn).setText(currentPlayer().getOrder());
        }
    }

    public void cycleTurn() {
        rotateArrow();

        if (!checkGameOver()) {
            turns++;
            currentTurn++;
            if (currentTurn >= activePlayers.size()) {
                currentTurn = 0;
            }
            updateCurrentPlayerLabel();
        }
        infolbl.setText("");

        cycleOrder();
        showOrder();
        startTime = System.currentTimeMillis();
        startTimer();
    }

    public void showOrder() {
        grandorderlbl.setText(currentPlayer().getOrder());
    }

    public void newPrompt() {
        inputfld.setText("");
        int coinFlip = (int) (Math.random() * 2);
        int i = (int) (Math.random() * (dictionary.size() - 1));
        int j;
        if (coinFlip > 0) {
            j = (int) (Math.random() * (dictionary.get(i).length() - 2));
            this.prompt = dictionary.get(i).substring(j, j + 2);
        } else {
            while (dictionary.get(i).length() < 3) {
                i = (int) (Math.random() * (dictionary.size() - 1));
            }
            j = (int) (Math.random() * (dictionary.get(i).length() - 3));
            this.prompt = dictionary.get(i).substring(j, j + 3);
        }
        promptlbl.setText(prompt);
        startTime = System.currentTimeMillis();
        startTimer();
    }

    public void updateSandwich() {
        if (breadMode) {
            if (!currentSandwich.isEmpty()) {
                String temp = new String();
                for (int i = 0; i < currentSandwich.size(); i++) {
                    temp = temp + "\n" + currentSandwich.get(i);
                }
                sandwichDisplay.setText(temp);
                if (startSandwich) {
                    sandwichShowTime = new Timeline(new KeyFrame(
                            Duration.millis(200),
                            ae -> sandwichDisplay.setText("")));
                    currentSandwich.clear();
                    sandwichShowTime.play();
                }
            } else {
                sandwichDisplay.setText("");
            }
        } else {
            sandwichDisplay.setText("Bread mode is off.");
        }
    }

    public void newOrder() {
        updateSandwich();
        if (breadMode) {
            if (startSandwich) {
                sandwichLength = 0;
                order = "bread";
                startSandwich = false;
            } else if (sandwichLength < idealSandwichLength) {
                int g = (int) (Math.random() * (possibleOrders.size()) - 1);
                order = possibleOrders.get(g);
                sandwichLength++;
            } else {
                order = "bread";
                System.out.println("Order: bread");
                startSandwich = true;
            }
            for (Player p : activePlayers) {
                p.setOrder(order);
            }
            System.out.println("Order: " + order);
            orderlbl.setText("Order: " + order.toUpperCase());
            currentSandwich.add(order);
        } else {
            order = "abcdefghijklmnopqrstuvwxyz";
            currentPlayer().setOrder(order);
            System.out.println("Order: " + currentPlayer().getOrder());
            orderlbl.setText("Order: " + currentPlayer().getOrder());
        }
        updateOrders();
    }

    public void cycleOrder() {
        if (!breadMode && turns < 4) {
            newOrder();
        }
        System.out.println("Order: " + currentPlayer().getOrder());
        orderlbl.setText("Order: " + currentPlayer().getOrder().toUpperCase());
    }

    public boolean isInDictionary(String s) {
        for (String c : dictionary) {
            if (s.toLowerCase().equals(c)) {
                return true;
            }
        }
        return false;
    }

    public void restartGame() {
        for (Player p : activePlayers) {
            p.setLives(3);
        }
        restartbtn.setDisable(true);
        inputfld.setDisable(false);
        giveupbtn.setDisable(false);


        infolbl.setText("");
        startGameTime = System.currentTimeMillis();
        typed.clear();
        currentTurn = 0;
        updateLives();
        newPrompt();
        newOrder();
    }
    public String rawIpt(String s) {
        String j;
        j = s.replaceAll("[^A-Za-z]+", "");
        j = j.toLowerCase();
        return j;
    }

        public void readFile (String s, ArrayList < String > a){
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

    public void makeGrace() {
        inputfld.setText("");
        promptlbl.setText("---");
        inputfld.setPromptText("Press enter when you are ready...");
        isGrace = true;
        pauseTimer();
    }

    public int calcScore(String s) {
        int k = 0;
        for (int i = 0; i < s.length(); i++) {
            String d = s.substring(i, i + 1).toLowerCase();
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
                case "u":
                    k++;
                    break;
                case "d":
                case "g":
                    k += 2;
                    break;
                case "b":
                case "c":
                case "m":
                case "p":
                    k += 3;
                    break;
                case "f":
                case "h":
                case "v":
                case "w":
                case "y":
                    k += 4;
                    break;
                case "k":
                    k += 5;
                    break;
                case "j":
                case "x":
                    k += 8;
                    break;
                case "q":
                case "z":
                    k += 10;
                    break;
            }
        }
        return k;
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

    public void quit() {
        quitButton.setText("Really?" + (15-confirmQuit));
        confirmQuit++;
        if (confirmQuit == 15) {
            quitButton.setText("Quit");
            confirmQuit = 0;
            try {
                breadApplication.switchToMain();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            quitTime = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    ae -> quitButton.setText("Quit")),
                    new KeyFrame(
                            Duration.seconds(3),
                            ae -> confirmQuit--));
            quitTime.play();
        }
    }
    public void check () {
        if (isGrace) {
            isGrace = false;
            newPrompt();
            cycleTurn();
        } else {
            String ipt = inputfld.getText();
            ipt = rawIpt(ipt);
            if (ipt.toLowerCase().contains(prompt.toLowerCase(Locale.ROOT)) && isInDictionary(ipt) && !typed.contains(ipt)) {
                for (int i = 0; i < ipt.length(); i++) {
                    if (order.contains(ipt.substring(i, i + 1))) {
                        if (breadMode) {
                            order = order.replaceFirst(ipt.substring(i, i + 1), "");
                            if (breadMode) {
                                masterorderlbl.setText(order);
                            } else {
                                for (Label l : orderlbls) {
                                    l.setText(order);
                                }
                            }
                        } else {
                            currentPlayer().setOrder(currentPlayer().getOrder().replaceFirst(ipt.substring(i, i + 1), ""));
                        }
                    }
                }
                updateOrders();
                if (currentPlayer().getOrder().replaceAll(" ", "").equals("")) {
                    if (breadMode) {
                        if (sandwichLength == idealSandwichLength && startSandwich) {
                            scorefld.setText("Sandwich complete! +1 life");
                            sandwichDone = true;
                            currentPlayer().addLives(1);
                            idealSandwichLength++;
                            lifeGainAnimation();
                            updateLives();
                        }
                    } else {
                        scorefld.setText("Order complete! +1 life");
                        currentPlayer().addLives(1);
                    }
                    newOrder();
                    updateLives();
                }
                if (!breadMode) {
                    orderlbl.setText("Order: " + currentPlayer().getOrder().toUpperCase());
                }

                currentPlayer().addScore(1);
                typed.add(ipt);
                prevlbls.get(currentTurn).setText(ipt.toUpperCase());
                makeGrace();
                currentPlayer().addScore(calcScore(ipt));
                if (timeAvailable >= 5000) {
                    timeAvailable *= 0.90;
                }
            } else if (typed.contains(ipt)) {
                inputfld.setText("");
                scorefld.setText("Word already used!");
            } else if (!ipt.toLowerCase().contains(prompt.toLowerCase(Locale.ROOT))) {
                inputfld.setText("");
                scorefld.setText("Doesn't contain prompt!");
            } else {
                inputfld.setText("");
                scorefld.setText("Invalid word!");
            }
        }
    }
    public void win()
    {
        //endscreen

    }

}