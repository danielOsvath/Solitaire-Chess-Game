package model.pieces;

import model.BoardPiece;
import java.util.Arrays;

/**
 * Class for Rook chess piece
 */
public class Rook extends BoardPiece {


    public Rook(int x, int y) {
        name = "Rook";
        abbr = "R";
        moveTypes = Arrays.asList("HORIZONTAL", "VERTICAL");

        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return
     */
    public Rook clone(){
        return new Rook(this.x,this.y);
    }
}
