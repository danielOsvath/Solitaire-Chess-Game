package model.pieces;

import model.BoardPiece;

import java.util.Arrays;

/**
 * Class for Pawn chess piece
 */
public class Pawn extends BoardPiece {

    public Pawn(int x, int y) {
        name = "Pawn";
        abbr = "P";

        //In this game we must capture pieces with every move.
        // Pawn can only capture diagonally.
        moveTypes = Arrays.asList("DIAGONAL");

        this.x = x;
        this.y = y;
    }

    @Override
    public boolean canMoveTo(int toX, int toY) {
        //limit pawn capture distance to one spot.
        int xChange = Math.abs(x - toX);
        int yChange = Math.abs(y - toY);

        return ((xChange == 1) && (yChange == 1));

    }

}
