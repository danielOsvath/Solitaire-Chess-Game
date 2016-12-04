/*
 * Blank.java
 */
package model.pieces;

import model.BoardPiece;

/**
 * Class for Blank spot
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 * */
public class Blank extends BoardPiece {

    /**
     * Creates a Blank Piece
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public Blank(int x, int y) {
        name = "Blank";
        abbr = "-";

        this.x = x;
        this.y = y;
    }


    /**
     * Checks if the current piece can move to
     * @param x x value
     * @param y y value
     * @return
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        return false;
    }

    /**
     * Creates a clone of the blank object with coordinates
     * @return a clone
     */
    public Blank clone(){
        return new Blank(this.x,this.y);
    }

}
