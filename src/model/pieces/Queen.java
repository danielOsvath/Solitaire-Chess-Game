package model.pieces;

import model.BoardPiece;

import java.util.Arrays;

/**
 * Class for Queen chess piece
 */
public class Queen extends BoardPiece {

    public Queen(int x, int y) {
        name = "Queen";
        abbr = "Q";
        moveTypes = Arrays.asList("HORIZONTAL","VERTICAL","DIAGONAL");

        this.x = x;
        this.y = y;
    }

}
