module com.example.viewcontrollermemegenerator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.viewcontrollermemegenerator to javafx.fxml;
    exports com.example.viewcontrollermemegenerator;
}