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
     *
     * @param x
     * @param y
     */
    public Knight(int x, int y) {

        name = "Knight";
        abbr = "N";
        moveTypes = Arrays.asList("LSHAPE");

        this.x = x;
        this.y = y;
    }

}
