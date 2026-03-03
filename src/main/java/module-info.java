module com.example.coasterapiproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires java.desktop;
    requires javafx.graphics;


    opens com.example.coasterapiproject to javafx.fxml;
    exports com.example.coasterapiproject;
}