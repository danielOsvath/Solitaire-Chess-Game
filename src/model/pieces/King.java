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
     * @param x
     * @param y
     */
    public King(int x, int y) {
        name = "King";
        abbr = "K";
        moveTypes = Arrays.asList("VERTICAL", "HORIZONTAL", "DIAGONAL");

        this.x = x;
        this.y = y;
    }

    /**
     * @param toX x value
     * @param toY y value
     * @return
     */
    @Override
    public boolean canMoveTo(int toX, int toY) {
        int xChange = Math.abs(x - toX);
        int yChange = Math.abs(y - toY);

        //king can only move one spot
        return (xChange <= 1) && (yChange <= 1) &&
                moveTypes.contains(typeOfMove(x, y, toX, toY));

    }
}
