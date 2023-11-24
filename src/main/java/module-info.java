module com.example.firstjavaproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.firstjavaproject to javafx.fxml;
    exports com.example.firstjavaproject;
}