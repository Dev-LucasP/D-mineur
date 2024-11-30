package com.beginsecure.minesweeper.modele;

public class Game {
    public enum Level {EASY, MEDIUM, HARD};
    private Level level;
    private Grid grid;

    public Game(Level level) {
        this.level = level;
        if (level == Level.EASY) {
            grid = new Grid(8, 10, 10);
        }
        else if (level == Level.MEDIUM) {
            grid = new Grid(12, 18, 40);
        }
        else if (level == Level.HARD) {
            grid = new Grid(13, 24, 99);
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public void reset(){
        if (level == Level.EASY) {
            grid = new Grid(8, 10, 10);
        }
        else if (level == Level.MEDIUM) {
            grid = new Grid(12, 18, 40);
        }
        else if (level == Level.HARD) {
            grid = new Grid(13, 24, 99);
        }
    }
}
