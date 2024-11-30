package com.beginsecure.minesweeper.controller;

import com.beginsecure.minesweeper.MinesweeperApplication;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MinesweeperMenuController {

    private Stage stage;
    private Scene scene;

    @FXML
    void onClickEasy(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApplication.class.getResource("view/MinesweeperEasy.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), 1600, 900);
        stage.setTitle("Minesweeper easy level");
        stage.setScene(newScene);
        MinesweeperEasyController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
    }

    @FXML
    void onClickMedium(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApplication.class.getResource("view/MinesweeperMedium.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), 1600, 900);
        stage.setTitle("Minesweeper medium level");
        stage.setScene(newScene);
        MinesweeperMediumController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
    }

    @FXML
    void onClickHard(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApplication.class.getResource("view/MinesweeperHard.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), 1600, 900);
        stage.setTitle("Minesweeper hard level");
        stage.setScene(newScene);
        MinesweeperHardController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
    }

    public void initialize() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}