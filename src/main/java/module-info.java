module com.example.breadbomb {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.controlsfx.controls;

    opens com.example.breadbomb to javafx.fxml;
    exports com.example.breadbomb;
}