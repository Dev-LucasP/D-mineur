package com.beginsecure.minesweeper.modele;

import java.util.Random;

public class Grid {
    private final int height, width, nbMine;
    private int flagPlaced;

    private Tile[][] allTiles;

    private int[][] mineLocation;

    public Grid(int height, int width, int nbMine) {
        this.height = height;
        this.width = width;
        this.nbMine = nbMine;
        flagPlaced = 0;
        allTiles = new Tile[height][width];
        mineLocation = new int[nbMine][2];
        initialize();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() { return width; }

    public int getNbMine() {
        return nbMine;
    }

    public int getFlagPlaced() {
        return flagPlaced;
    }

    public void initialize(){
        createTiles();
        shuffleTiles();
        foundMineLocation();
        placedNumber();
    }

    public void reset() {
        allTiles = new Tile[height][width];
        mineLocation = new int[nbMine][2];
        createTiles();
        shuffleTiles();
        foundMineLocation();
        placedNumber();
    }

    private void createTiles(){
        int nbMinePlaced = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(nbMinePlaced < nbMine){
                    allTiles[i][j] = new Tile(Tile.Type.MINE);
                    nbMinePlaced++;
                }
                else {
                    allTiles[i][j] = new Tile(Tile.Type.EMPTY);
                }
            }
        }
    }

    private void shuffleTiles(){
        Random random = new Random();
        int rows = height;
        int cols = width;

        for (int i = height * width - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            int currentRow = i / width;
            int currentCol = i % width;
            int randomRow = j / width;
            int randomCol = j % width;
            Tile temp = allTiles[currentRow][currentCol];
            allTiles[currentRow][currentCol] = allTiles[randomRow][randomCol];
            allTiles[randomRow][randomCol] = temp;
        }
    }

    private void foundMineLocation(){
        int nbMineFound = 0;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(allTiles[i][j].getValue() == Tile.Type.MINE){
                    mineLocation[nbMineFound][0] = i;
                    mineLocation[nbMineFound][1] = j;
                    nbMineFound++;
                }
            }
        }
    }

    private void placedNumber(){
        for (int i = 0; i < nbMine; i++) {
            int row = mineLocation[i][0];
            int col = mineLocation[i][1];
            if (row - 1 >= 0) {
                if (allTiles[row - 1][col].getValue() == Tile.Type.EMPTY || allTiles[row - 1][col].getValue() == Tile.Type.NUMBER) {
                    allTiles[row - 1][col].setValue(Tile.Type.NUMBER);
                    allTiles[row - 1][col].setNumber(allTiles[row - 1][col].getNumber() + 1);
                }
                if (col - 1 >= 0 && (allTiles[row - 1][col - 1].getValue() == Tile.Type.EMPTY || allTiles[row - 1][col - 1].getValue() == Tile.Type.NUMBER)) {
                    allTiles[row - 1][col - 1].setValue(Tile.Type.NUMBER);
                    allTiles[row - 1][col - 1].setNumber(allTiles[row - 1][col - 1].getNumber() + 1);
                }
                if (col + 1 < width && (allTiles[row - 1][col + 1].getValue() == Tile.Type.EMPTY || allTiles[row - 1][col + 1].getValue() == Tile.Type.NUMBER)) {
                    allTiles[row - 1][col + 1].setValue(Tile.Type.NUMBER);
                    allTiles[row - 1][col + 1].setNumber(allTiles[row - 1][col + 1].getNumber() + 1);
                }
            }

            if (row + 1 < height) {
                if (allTiles[row + 1][col].getValue() == Tile.Type.EMPTY || allTiles[row + 1][col].getValue() == Tile.Type.NUMBER) {
                    allTiles[row + 1][col].setValue(Tile.Type.NUMBER);
                    allTiles[row + 1][col].setNumber(allTiles[row + 1][col].getNumber() + 1);
                }
                if (col - 1 >= 0 && (allTiles[row + 1][col - 1].getValue() == Tile.Type.EMPTY || allTiles[row + 1][col - 1].getValue() == Tile.Type.NUMBER)) {
                    allTiles[row + 1][col - 1].setValue(Tile.Type.NUMBER);
                    allTiles[row + 1][col - 1].setNumber(allTiles[row + 1][col - 1].getNumber() + 1);
                }
                if (col + 1 < width && (allTiles[row + 1][col + 1].getValue() == Tile.Type.EMPTY || allTiles[row + 1][col + 1].getValue() == Tile.Type.NUMBER)) {
                    allTiles[row + 1][col + 1].setValue(Tile.Type.NUMBER);
                    allTiles[row + 1][col + 1].setNumber(allTiles[row + 1][col + 1].getNumber() + 1);
                }
            }

            if (col - 1 >= 0 && (allTiles[row][col - 1].getValue() == Tile.Type.EMPTY || allTiles[row][col - 1].getValue() == Tile.Type.NUMBER)) {
                allTiles[row][col - 1].setValue(Tile.Type.NUMBER);
                allTiles[row][col - 1].setNumber(allTiles[row][col - 1].getNumber() + 1);
            }
            if (col + 1 < width && (allTiles[row][col + 1].getValue() == Tile.Type.EMPTY || allTiles[row][col + 1].getValue() == Tile.Type.NUMBER)) {
                allTiles[row][col + 1].setValue(Tile.Type.NUMBER);
                allTiles[row][col + 1].setNumber(allTiles[row][col + 1].getNumber() + 1);
            }
        }
    }

    public Tile.Type checkValue(int row, int column) {
        return allTiles[row][column].getValue();
    }

    public Tile.Type checkStatue(int row, int column) {
        return allTiles[row][column].getStatue();
    }

    public Tile getTile(int row, int column) {
        return allTiles[row][column];
    }

    public void reveal(int row, int column) {
        if (!isValid(row, column)) return;
        Tile tile = allTiles[row][column];
        if (tile.getStatue() == Tile.Type.HIDDEN) {
            tile.setStatue(tile.getValue());
            if (tile.getValue() == Tile.Type.EMPTY) {
                revealAdjacentTiles(row, column);
            }
        }
    }

    private void revealAdjacentTiles(int row, int column) {
        int[] directions = {-1, 0, 1};

        for (int dx : directions) {
            for (int dy : directions) {
                if (dx == 0 && dy == 0) continue;
                int newRow = row + dx;
                int newCol = column + dy;
                if (isValid(newRow, newCol) && allTiles[newRow][newCol].getStatue() == Tile.Type.HIDDEN) {
                    reveal(newRow, newCol);
                }
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    public void revealAllMines() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (allTiles[i][j].getValue() == Tile.Type.MINE) {
                    allTiles[i][j].setStatue(Tile.Type.MINE);
                }
            }
        }
    }

    public void addFlag(int row, int column) {
        if(flagPlaced < nbMine){
            allTiles[row][column].setStatue(Tile.Type.FLAG);
            flagPlaced++;
        }
    }

    public void removeFlag(int row, int column) {
        allTiles[row][column].setStatue(Tile.Type.HIDDEN);
        flagPlaced--;
    }

    public boolean isWin(){
        int nbMineFound = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(allTiles[i][j].getStatue() == Tile.Type.FLAG && allTiles[i][j].getValue() == Tile.Type.MINE){
                    nbMineFound++;
                }
            }
        }
        return nbMineFound == nbMine;
    }

}
