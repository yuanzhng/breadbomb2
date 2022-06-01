package com.example.breadbomb;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class MenuController {
    @FXML
    private Button single;

    @FXML
    private Button multi;

    @FXML
    private Label moneyLabel;

    public void singlePressed() {
        breadApplication.setSingle();
        try {
            breadApplication.switchToMode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void multiPressed() {
        breadApplication.setMulti();
        try {
            breadApplication.switchToMode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setMoney(int i) {
        moneyLabel.setText("Money:" + "\n" + "$" + i);
    }
}
