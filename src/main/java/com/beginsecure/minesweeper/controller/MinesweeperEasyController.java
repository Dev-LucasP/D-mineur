package com.beginsecure.minesweeper.controller;

import com.beginsecure.minesweeper.MinesweeperApplication;
import com.beginsecure.minesweeper.modele.Game;
import com.beginsecure.minesweeper.modele.Tile;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MinesweeperEasyController {

    private Stage stage;
    private Scene scene;

    private boolean gameLost = false;
    private boolean gameStarted = false;

    private Game game;

    private long startTime;
    private AnimationTimer timer;

    private final int IMAGE_SIZE = 50;

    private final Image MINE_IMAGE = new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/mine.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image MINE_EXPLODED_IMAGE = new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/mine-exploded.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image FLAG_IMAGE = new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/flag.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image HIDDEN_IMAGE = new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/hidden.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image EMPTY_IMAGE = new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/empty.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true);
    private final Image[] VALUES_IMAGES = new Image[] {
            new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/one.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true),
            new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/two.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true),
            new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/three.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true),
            new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/four.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true),
            new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/five.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true),
            new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/six.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true),
            new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/seven.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true),
            new Image(getClass().getResource("/com/beginsecure/minesweeper/view/images/eight.png").toExternalForm(), IMAGE_SIZE, IMAGE_SIZE, true, true)
    };

    @FXML
    private GridPane mainGrid;

    @FXML
    private Label msgEndGame;

    @FXML
    private Label nbMineLabel;

    @FXML
    private Label timerLabel;

    @FXML
    void onClickBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApplication.class.getResource("view/MinesweeperMenu.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), 1600, 900);
        stage.setTitle("Minesweeper");
        stage.setScene(newScene);
        MinesweeperMenuController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
    }

    @FXML
    void onClickReset(ActionEvent event) {
        game.reset();
        mainGrid.getChildren().clear();
        ImageView imageView;
        for(int row = 0 ; row < game.getGrid().getHeight() ; row++) {
            for(int column = 0 ; column < game.getGrid().getWidth() ; column++) {
                imageView = new ImageView(HIDDEN_IMAGE);
                imageView.setOnMouseClicked(this::handleTileClick);
                mainGrid.add(imageView, column, row);
            }
        }
        gameLost = false;
        gameStarted = false;
        msgEndGame.setText("");
        nbMineLabel.setText(Integer.toString(game.getGrid().getNbMine()));
        refresh();
        resetTimer();
    }

    @FXML
    public void initialize() {
        game = new Game(Game.Level.EASY);
        mainGrid.getChildren().clear();
        mainGrid.setMaxWidth(game.getGrid().getWidth() * IMAGE_SIZE);
        mainGrid.setMaxHeight(game.getGrid().getHeight() * IMAGE_SIZE);
        ImageView imageView;
        for(int row = 0 ; row < game.getGrid().getHeight() ; row++) {
            for(int column = 0 ; column < game.getGrid().getWidth() ; column++) {
                imageView = new ImageView(HIDDEN_IMAGE);
                imageView.setOnMouseClicked(this::handleTileClick);
                mainGrid.add(imageView, column, row);
            }
        }
        msgEndGame.setText("");
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedMillis = (now - startTime) / 1_000_000;
                long elapsedSeconds = elapsedMillis / 1000;
                long minutes = elapsedSeconds / 60;
                long seconds = elapsedSeconds % 60;
                timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
            }
        };
        nbMineLabel.setText(Integer.toString(game.getGrid().getNbMine()));
        resetTimer();
    }

    public void startTimer() {
        startTime = System.nanoTime();
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void resetTimer() {
        timer.stop();
        timerLabel.setText("00:00");
    }

    private void handleTileClick(MouseEvent event) {
        if (gameLost) {
            return;
        }
        if (!gameStarted) {
            gameStarted = true;
            startTimer();
        }
        ImageView clickedImageView = (ImageView) event.getSource();
        int row = GridPane.getRowIndex(clickedImageView);
        int column = GridPane.getColumnIndex(clickedImageView);

        if (event.getButton() == MouseButton.PRIMARY) {
            Tile.Type tileType = game.getGrid().checkValue(row, column);
            if (tileType == Tile.Type.MINE) {
                gameLost = true;
                game.getGrid().revealAllMines();
                refresh(row, column);
            } else {
                game.getGrid().reveal(row, column);
                refresh();
            }
        } else if (event.getButton() == MouseButton.SECONDARY) {
            if (game.getGrid().checkStatue(row, column).equals(Tile.Type.FLAG)) {
                game.getGrid().removeFlag(row, column);
            } else if (game.getGrid().checkStatue(row, column).equals(Tile.Type.HIDDEN)) {
                game.getGrid().addFlag(row, column);
            }
            refresh();
        }
    }

    private void refresh(){
        nbMineLabel.setText(Integer.toString(game.getGrid().getNbMine() - game.getGrid().getFlagPlaced()));
        mainGrid.getChildren().clear();
        ImageView imageView;
        for (int row = 0; row < game.getGrid().getHeight(); row++) {
            for (int column = 0; column < game.getGrid().getWidth(); column++) {
                Tile tile = game.getGrid().getTile(row, column);
                switch (game.getGrid().getTile(row, column).getStatue()) {
                    case Tile.Type.MINE:
                        imageView = new ImageView(MINE_IMAGE);
                        break;
                    case Tile.Type.EMPTY:
                        imageView = new ImageView(EMPTY_IMAGE);
                        break;
                    case Tile.Type.NUMBER:
                        int tileNumber = tile.getNumber();
                        imageView = new ImageView(VALUES_IMAGES[tileNumber - 1]);
                        break;
                    case Tile.Type.FLAG:
                        imageView = new ImageView(FLAG_IMAGE);
                        break;
                    default:
                        imageView = new ImageView(HIDDEN_IMAGE);
                        break;
                }
                if(!gameLost){
                    if(game.getGrid().isWin()){
                        msgEndGame.setText("You win !");
                        msgEndGame.setStyle("-fx-font-size: 25px; -fx-text-fill: green;");
                        stopTimer();
                    }
                    else {
                        imageView.setOnMouseClicked(this::handleTileClick);
                    }
                }
                mainGrid.add(imageView, column, row);
            }
        }
    }

    private void refresh(int i, int j){
        if(((game.getGrid().getNbMine() - game.getGrid().getFlagPlaced()) - 1) >= 0){
            nbMineLabel.setText(Integer.toString((game.getGrid().getNbMine() - game.getGrid().getFlagPlaced()) - 1));
        }
        else {
            nbMineLabel.setText(Integer.toString(0));
        }
        mainGrid.getChildren().clear();
        ImageView imageView;
        for (int row = 0; row < game.getGrid().getHeight(); row++) {
            for (int column = 0; column < game.getGrid().getWidth(); column++) {
                Tile tile = game.getGrid().getTile(row, column);
                switch (game.getGrid().getTile(row, column).getStatue()) {
                    case Tile.Type.MINE:
                        if(row == i && column == j){
                            imageView = new ImageView(MINE_EXPLODED_IMAGE);
                        }
                        else {
                            imageView = new ImageView(MINE_IMAGE);
                        }
                        break;
                    case Tile.Type.EMPTY:
                        imageView = new ImageView(EMPTY_IMAGE);
                        break;
                    case Tile.Type.NUMBER:
                        int tileNumber = tile.getNumber();
                        imageView = new ImageView(VALUES_IMAGES[tileNumber - 1]);
                        break;
                    case Tile.Type.FLAG:
                        imageView = new ImageView(FLAG_IMAGE);
                        break;
                    default:
                        imageView = new ImageView(HIDDEN_IMAGE);
                        break;
                }
                msgEndGame.setText("You lost !");
                msgEndGame.setStyle("-fx-font-size: 25px; -fx-text-fill: red;");
                stopTimer();
                mainGrid.add(imageView, column, row);
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
