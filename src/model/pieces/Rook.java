package model.pieces;

import model.BoardPiece;

/**
 * Class for Rook chess piece
 */
public class Rook extends BoardPiece {

    private String name = "Rook";
    private String abbr = "R";

    public Rook(int x, int y)
    {
        this.x = x;
        this.y= y;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAbbr() {
        return abbr;
    }


    @Override
    public String toString() {
        return this.name + " at (" + x + ", " + y + ")";
    }
}
