package model.pieces;

import model.BoardPiece;

import java.util.Arrays;

/**
 * Class for Pawn chess piece
 */
public class Pawn extends BoardPiece {

    /**
     * Create a Pawn Object with the coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    public Pawn(int x, int y) {
        name = "Pawn";
        abbr = "P";

        //In this game we must capture pieces with every move.
        // Pawn can only capture diagonally.
        moveTypes = Arrays.asList("DIAGONAL");

        this.x = x;
        this.y = y;
    }

    /**
     * Checks if the current Pawn object can move to the supplied coordinates
     * @param toX x value
     * @param toY y value
     * @return
     */
    @Override
    public boolean canMoveTo(int toX, int toY) {
        //limit pawn capture distance to one spot.
        int xChange = Math.abs(x - toX);
        int yChange = Math.abs(y - toY);

        return ((xChange == 1) && (yChange == 1));

    }

    /**
     * Creates a Clone of the current Pawn Object
     * @return the clone
     */
    public Pawn clone(){
        return new Pawn(this.x,this.y);
    }

}
