package model.pieces;

import model.BoardPiece;

import java.util.Arrays;

/**
 * Class for Queen chess piece
 */
public class Queen extends BoardPiece {

    /**
     * Creates a new Queen object with the supplied coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Queen(int x, int y) {
        name = "Queen";
        abbr = "Q";
        moveTypes = Arrays.asList("HORIZONTAL","VERTICAL","DIAGONAL");

        this.x = x;
        this.y = y;
    }

    /**
     * Creates a clone of the Queen object
     * @return a clone
     */
    public Queen clone(){
        return new Queen(this.x,this.y);
    }

}
