/*
 * BoardPiece.java
 */
package model;

import java.util.List;

/**
 * This interface is created to represent a Chess Piece.
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */
public abstract class BoardPiece {

    public List<String> moveTypes;
    public int x;
    public int y;

    /**
     * Gets the name of the chess piece
     * @return chess piece name
     */
    public abstract String getName();

    /**
     * Gets the abbreviation of the chess piece
     * @return the abbreviation
     */
    public abstract String getAbbr();

    /**
     * Determines if chess piece can move to that coordinate.
     *
     * Assumes destination coordinates are within bounds,
     * and not the same as the current position.
     * @param toX x value
     * @param toY y value
     * @return true: if can move to that spot, false: if cannot move to that spot
     */
    public boolean canMoveTo(int toX, int toY) {
        return moveTypes.contains(typeOfMove(x,y,toX,toY));
    }

    /**
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    public String typeOfMove(int fromX, int fromY, int toX, int toY) {
        //NOTE: X and Y coordinates are reversed due to 2D array!

        int xChange = Math.abs(fromX - toX);
        int yChange = Math.abs(fromY - toY);

        if(fromX == toX){
            return "HORIZONTAL";

        } else if(fromY == toY){
            return "VERTICAL";

        }else if(yChange == xChange){
            return "DIAGONAL";

        }else if( ((yChange == 1) && (xChange == 2)) ||
                ((yChange == 2) && (xChange == 1)) ){
            return "LSHAPE";

        }else{
            return "INVALID";
        }
    }

    /**
     * Returns a String of the Chess Piece
     * @return The String
     */
    public abstract String toString();


}
