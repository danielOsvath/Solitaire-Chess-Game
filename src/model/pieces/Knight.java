package model.pieces;

import model.BoardPiece;

import java.util.Arrays;

/**
 * Class for Knight chess piece
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */
public class Knight extends BoardPiece {

    /**
     * Creates a King object with the coordinates
     * @param x x value
     * @param y y value
     */
    public Knight(int x, int y) {

        name = "Knight";
        abbr = "N";
        moveTypes = Arrays.asList("LSHAPE");

        this.x = x;
        this.y = y;
    }

    /**
     * Creates a clone of the current King object
     * @return a clone
     */
    public Knight clone(){
        return new Knight(this.x,this.y);
    }

}
