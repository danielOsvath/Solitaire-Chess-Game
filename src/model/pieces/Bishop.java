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
     *
     * @param x
     * @param y
     */
    public Bishop(int x, int y) {
        name = "Bishop";
        abbr = "B";
        moveTypes = Arrays.asList("DIAGONAL");

        this.x = x;
        this.y = y;
    }
}
