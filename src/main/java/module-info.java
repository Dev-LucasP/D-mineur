module com.beginsecure.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.beginsecure.minesweeper to javafx.fxml;
    exports com.beginsecure.minesweeper;
    exports com.beginsecure.minesweeper.controller;
    opens com.beginsecure.minesweeper.controller to javafx.fxml;
}