package model.pieces;

import model.BoardPiece;
import java.util.Arrays;

/**
 * Class for Rook chess piece
 */
public class Rook extends BoardPiece {

    /**
     * Creates a Rook object with the supplied coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Rook(int x, int y) {
        name = "Rook";
        abbr = "R";
        moveTypes = Arrays.asList("HORIZONTAL", "VERTICAL");

        this.x = x;
        this.y = y;
    }

    /**
     * Creates a clone of the Rook object
     * @return a clone
     */
    public Rook clone(){
        return new Rook(this.x,this.y);
    }
}
