package com.example.breadbomb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class breadApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(breadApplication.class.getResource("breadcontroller-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setResizable(false);
        stage.setTitle("midbomb");
        stage.setScene(scene);
        stage.show();
        //shdfjhsjdfhjsdf
    }

    public static void main(String[] args) {
        launch();
    }
}