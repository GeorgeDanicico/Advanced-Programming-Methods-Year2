module GUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires javatuples;

    opens GUI to javafx.fxml;
    exports GUI;
}