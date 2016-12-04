package model.pieces;

import model.BoardPiece;
import java.util.Arrays;

/**
 * Class for King chess piece
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */

public class King extends BoardPiece {

    /**
     * Creates King with the coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public King(int x, int y) {
        name = "King";
        abbr = "K";
        moveTypes = Arrays.asList("VERTICAL", "HORIZONTAL", "DIAGONAL");

        this.x = x;
        this.y = y;
    }

    /**
     * Checks if the current piece can moved to desired coordinates
     * @param toX x value
     * @param toY y value
     * @return true if it can move to that location, false if not
     */
    @Override
    public boolean canMoveTo(int toX, int toY) {
        int xChange = Math.abs(x - toX);
        int yChange = Math.abs(y - toY);

        //king can only move one spot
        return (xChange <= 1) && (yChange <= 1) &&
                moveTypes.contains(typeOfMove(x, y, toX, toY));

    }

    /**
     * Creates a clone of the current King object
     * @return the clone
     */
    public King clone(){
        return new King(this.x,this.y);
    }

}
