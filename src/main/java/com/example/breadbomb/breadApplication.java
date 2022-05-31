package com.example.breadbomb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class breadApplication extends Application {
    private static Stage stage;
    private static boolean breadMode;
    private static boolean multiMode;
    private static FXMLLoader singleLoader = new FXMLLoader(breadApplication.class.getResource("singleplayer-view.fxml"));
    private static FXMLLoader multiLoader = new FXMLLoader(breadApplication.class.getResource("multiplayer-view.fxml"));
    private static FXMLLoader modeLoader = new FXMLLoader(breadApplication.class.getResource("mode-view.fxml"));
    private static FXMLLoader menuLoader = new FXMLLoader(breadApplication.class.getResource("menu-view.fxml"));

    private static FXMLLoader winLoader = new FXMLLoader(breadApplication.class.getResource("winScreen.fxml"));
    private static Scene menuScene;

    private static Scene winScene;
    private static Scene modeScene;
    private static Scene singleScene;
    private static Scene multiScene;
    static {
        try {
            menuScene = new Scene(menuLoader.load(), 1080, 700);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
           modeScene = new Scene(modeLoader.load(), 1080, 700);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            singleScene = new Scene(singleLoader.load(), 1080, 700);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            multiScene = new Scene(multiLoader.load(), 1080, 700);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            winScene = new Scene(winLoader.load(), 1080, 700);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int money;

    @Override
    public void start(Stage s) throws IOException {
        MenuController controller = menuLoader.getController();
        try {
            FileInputStream fileIn = new FileInputStream(System.getProperty("user.home") + "/.breadbomb/money.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            money = (Integer) in.readObject();
            in.close();
            fileIn.close();
            controller.setMoney(money);
        } catch (IOException i) {
            i.printStackTrace();
            controller.setMoney(0);
        } catch (ClassNotFoundException c) {
            System.out.println("Money file not found");
            c.printStackTrace();
            controller.setMoney(0);
        }
        stage = s;
        stage.setTitle("BreadBomb");
        stage.setScene(menuScene);
        stage.show();
        stage.setResizable(false);
    }

    public static void switchToMode() throws IOException {
        stage.setScene(modeScene);
    }

    public static void switchToGame() throws IOException {
        if (!multiMode) {
            singleplayerController controller = singleLoader.getController();
            controller.initialize(breadMode);
            stage.setScene(singleScene);
        } else {
            multiplayerController controller = multiLoader.getController();
            controller.initialize(breadMode);
            stage.setScene(multiScene);
        }

    }
    public static void switchToMain() throws IOException {
        stage.setScene(menuScene);
    }

    public static void win() {
        stage.setScene(winScene);
    }


    public static void setSingle() {
        multiMode = false;
    }

    public static void setMulti() {
        multiMode = true;
    }

    public static void setBreadMode(){
        breadMode = true;
    }

    public static void setClassic(){
        breadMode = false;
    }

    public static void main(String[] args) {
        launch();
    }
}