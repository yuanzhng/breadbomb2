package com.example.breadbomb;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.*;

import static java.lang.Integer.decode;

public class multiplayerController {
    Timeline autoPlayTimeline;
    Timeline updateTimerTimeline;
    Timeline sandwichShowTime;
    private double startAngle=180;

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
    private Label currentPlayerlbl;
    @FXML
    private Button giveupbtn;
    @FXML
    private Label orderlbl;
    @FXML
    private Label sandwichDisplay;
    @FXML
    private Label scorefld;

    @FXML
    private Label availlbl;
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
    private ImageView arrow;
    @FXML
    private TextField inputfld;

    private String prompt;

    private ArrayList<String> currentSandwich = new ArrayList<String>();

    private ArrayList<String> typed = new ArrayList<String>();

    private ArrayList<String> possiblePrompts = new ArrayList<String>();

    private ArrayList<String> possibleOrders = new ArrayList<String>();

    private ArrayList<String> dictionary = new ArrayList<String>();

    private ArrayList<Player> activePlayers = new ArrayList<Player>();

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

    private int stress = 1;

    public void initialize(boolean bread) {
        startSandwich = true;
        sandwichDone = false;
        sandwichLength = 0;
        idealSandwichLength = 1;
        if (bread) {
            System.out.println("BreadMode enabled...");
            readFile("orders.txt", possibleOrders);
            breadMode = true;
        }
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
            for (Label i : prevlbls) {
                i.setText("");
            }
            for (int i = 0; i < 4; i++) {
                promptNewPlayer(i);
            }
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
            startTime = System.currentTimeMillis();
        Arc path= new Arc();
        path.setCenterX(170);
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
        path.setCenterX(170);
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
            updateLives();
            System.out.println(currentPlayer().getName() + " Lives: " + currentPlayer().getLives());
            if (checkZeroLives()) {
                giveDeath();
            }
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
        activePlayers.remove(currentTurn);
        liveslbls.remove(currentTurn);
        if (currentTurn == 0) {
            currentTurn = activePlayers.size() - 1;

        }
        else {
            currentTurn--;


        }




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
        } else {
            availlbl.setText("" + totalSeconds);
        }
        if (checkZeroLives()) {
            giveDeath();

        }
    }

    public void damageCurrent(int i) {
        currentPlayer().removeLives(i);
        cycleTurn();
    }

    public void updateLives() {
        for (int i = 0; i < activePlayers.size(); i++) {
            liveslbls.get(i).setText(Integer.toString(activePlayers.get(i).getLives()));
        }
    }
    public void updateCurrentPlayerLabel () {
            currentPlayerlbl.setText(currentPlayer().getName());
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
        stress++;
        if (stress >= activePlayers.size()) {
            newPrompt();
            stress = 1;
        }
        startTime = System.currentTimeMillis();

        startTimer();
    }

    public void newPrompt() {
        stress = 1;
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
                System.out.println("Order: bread");
                orderlbl.setText("Order: BREAD");
                startSandwich = false;
            } else if (sandwichLength < idealSandwichLength) {
                int g = (int) (Math.random() * (possibleOrders.size()) - 1);
                order = possibleOrders.get(g);
                System.out.println("Order: " + order);
                orderlbl.setText("Order: " + order.toUpperCase());
                sandwichLength++;
            } else {
                order = "bread";
                System.out.println("Order: bread");
                orderlbl.setText("Order: BREAD");
                startSandwich = true;
            }
            currentSandwich.add(order);
        } else {
            order = "abcdefghijklmnopqrstuvwxyz";
            System.out.println("Order: " + order);
            orderlbl.setText("Order: " + order.toUpperCase());
        }
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
                            order = order.replaceFirst(ipt.substring(i, i + 1), "");
                        }
                    }
                    if (order.replaceAll(" ", "").equals("")) {
                        if (breadMode) {
                            if (sandwichLength == idealSandwichLength && startSandwich) {
                                scorefld.setText("Sandwich complete! +1 life");
                                sandwichDone = true;
                                currentPlayer().addLives(1);
                                idealSandwichLength++;
                                newOrder();
                                updateLives();
                            }
                        } else {
                            scorefld.setText("Order complete! +1 life");
                            currentPlayer().addLives(1);
                            newOrder();
                            updateLives();
                        }
                    }
                    orderlbl.setText("Order: " + order.toUpperCase());
                    currentPlayer().addScore(1);
                    typed.add(ipt);
                    prevlbls.get(currentTurn).setText(ipt.toUpperCase());
                    makeGrace();
                } else if (typed.contains(ipt)) {
                    inputfld.setText("");
                } else if (!ipt.toLowerCase().contains(prompt.toLowerCase(Locale.ROOT))) {
                    inputfld.setText("");
                } else {
                    inputfld.setText("");
                }
            }
    }
    public void win()
    {
        //endscreen
    }

}
