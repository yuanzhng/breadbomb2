package com.example.breadbomb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class breadApplication extends Application {
    private static Stage stage;
    private static boolean breadMode;
    private static boolean multiMode;
    @Override
    public void start(Stage s) throws IOException {
        stage = s;
        FXMLLoader fxmlLoader = new FXMLLoader(breadApplication.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 700);
        stage.setTitle("BreadBomb");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void switchToMode() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(breadApplication.class.getResource("mode-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    public static void switchToGame() throws IOException {
        FXMLLoader fxmlLoader;
        if (multiMode) {
            fxmlLoader = new FXMLLoader(breadApplication.class.getResource("sandwichcontroller-view.fxml"));
            sandwichController controller = fxmlLoader.getController();
        } else {
            fxmlLoader = new FXMLLoader(breadApplication.class.getResource("breadcontroller-view.fxml"));
            breadController controller = fxmlLoader.getController();
        }
        Scene scene = new Scene(fxmlLoader.load());
        breadController controller = fxmlLoader.getController();
        controller.initialize(breadMode);
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