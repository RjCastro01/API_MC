module com.mc.apimc {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires okhttp3;


    opens com.mc.apimc to javafx.fxml;
    exports com.mc.apimc;
}