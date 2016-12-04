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

    /* Types of moves a figure can make. */
    public List<String> moveTypes;

    /* X coordinate of the figure, first coordinate in 2D array. */
    public int x;

    /* Y coordinate of the figure, second coordinate in 2D array. */
    public int y;

    /* Name pf the figure */
    public String name;

    /* Abbreviation of the figure */
    public String abbr;

    /* If piece is selected for GUI */
    public boolean selected = false;

    /**
     * Gets the name of the chess piece
     * @return chess piece name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the abbreviation of the chess piece
     * @return the abbreviation
     */
    public String getAbbr(){
        return abbr;
    }

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
     * Determines what type of move is trying to be made
     * @param fromX the current x value
     * @param fromY the current y value
     * @param toX the desired x value
     * @param toY the desired y value
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
     * Move board piece to new location
     * @param toX move to this x value
     * @param toY move to this y value
     */
    public void moveTo(int toX, int toY){
        this.x = toX;
        this.y = toY;
    }
    /**
     * Returns a String of the Chess Piece
     * @return The String
     */
    public  String toString(){
        return this.name + " at (" + x + ", " + y + ")";
    }

    /**
     * Makes a clone of the board piece
     * @return the clone
     */
    public abstract BoardPiece clone();
}
