/*
 * Bishop.java
 */

package model.pieces;

import model.BoardPiece;
import java.util.Arrays;

/**
 * Class for Bishop chess piece
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */
public class Bishop extends BoardPiece {

    /**
     * Initalize the Bishop with coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    public Bishop(int x, int y) {
        name = "Bishop";
        abbr = "B";
        moveTypes = Arrays.asList("DIAGONAL");

        this.x = x;
        this.y = y;
    }

    /**
     * Makes a clone of the Bishop
     * @return a clone
     */
    public Bishop clone(){
        return new Bishop(this.x,this.y);
    }
}
