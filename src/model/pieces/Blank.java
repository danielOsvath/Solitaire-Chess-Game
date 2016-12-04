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
     *
     * @param x
     * @param y
     */
    public Blank(int x, int y) {
        name = "Blank";
        abbr = "-";

        this.x = x;
        this.y = y;
    }


    /**
     *
     * @param x x value
     * @param y y value
     * @return
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        return false;
    }

    /**
     *
     * @return
     */
    public Blank clone(){
        return new Blank(this.x,this.y);
    }

}
