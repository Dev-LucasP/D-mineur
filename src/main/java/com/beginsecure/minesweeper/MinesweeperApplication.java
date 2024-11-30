package com.beginsecure.minesweeper;

import com.beginsecure.minesweeper.controller.MinesweeperMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MinesweeperApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApplication.class.getResource("view/MinesweeperMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        MinesweeperMenuController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}