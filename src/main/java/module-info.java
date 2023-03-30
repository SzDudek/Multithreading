module lab05.lab05_pop {
    requires javafx.controls;
    requires javafx.fxml;


    opens lab05.lab05_pop to javafx.fxml;
    exports lab05.lab05_pop;
}