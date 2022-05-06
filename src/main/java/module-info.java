module com.example.breadbomb {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.breadbomb to javafx.fxml;
    exports com.example.breadbomb;
}