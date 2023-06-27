module com.example.lab4right {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab4right to javafx.fxml;
    exports com.example.lab4right;
}