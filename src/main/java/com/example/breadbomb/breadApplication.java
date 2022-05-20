package com.example.breadbomb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.IOException;

public class breadApplication extends Application {
    private static Stage stage;
    private static boolean breadMode;
    private static boolean multiMode;

    private AudioClip clip;
    @Override
    public void start(Stage s) throws IOException {
        stage = s;
        FXMLLoader fxmlLoader = new FXMLLoader(breadApplication.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("BreadBomb");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
    }

    public static void switchToMode() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(breadApplication.class.getResource("mode-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    public static void switchToGame() throws IOException {
        if (!multiMode) {
            FXMLLoader fxmlLoader = new FXMLLoader(breadApplication.class.getResource("singleplayer-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            singleplayerController controller = fxmlLoader.getController();
            controller.initialize(breadMode);
            stage.setScene(scene);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(breadApplication.class.getResource("multiplayer-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            multiplayerController controller = fxmlLoader.getController();
            controller.initialize(breadMode);
            stage.setScene(scene);
        }

    }
    public static void switchToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(breadApplication.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
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