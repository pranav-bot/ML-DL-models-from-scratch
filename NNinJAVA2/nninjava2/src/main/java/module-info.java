module com.nninjava2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.nninjava2 to javafx.fxml;
    exports com.nninjava2;
}
