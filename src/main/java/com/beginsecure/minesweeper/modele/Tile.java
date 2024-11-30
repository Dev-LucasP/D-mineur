package com.beginsecure.minesweeper.modele;

public class Tile {
    public enum Type {MINE, EMPTY, HIDDEN, NUMBER, FLAG};
    private Type statue;
    private Type value;
    private int number;

    public Tile(Type value) {
        this.statue = Type.HIDDEN;
        this.value = value;
        this.number = 0;
    }

    public Type getStatue(){ return statue; }

    public Type getValue(){ return value; }

    public void setValue(Type value){ this.value = value; }

    public void setStatue(Type statue){ this.statue = statue; }

    public void setNumber(int number){ this.number = number; }

    public int getNumber(){ return number; }
}
