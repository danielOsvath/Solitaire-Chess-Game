/*
 * BoardPiece.java
 */
package model;

import model.pieces.*;

import java.util.List;

//import for constants
import static model.SoltrChessModel.*;


/**
 * This interface is created to represent a Chess Piece.
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */
public class BoardPiece {

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
     * Move board piece to new location
     *
     * @param toX
     * @param toY
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

    public BoardPiece clone(){

        switch (this.getAbbr()){
            case PAWN: return new Pawn(this.x,this.y);
            case BISHOP: return new Bishop(this.x,this.y);
            case KING: return new King(this.x,this.y);
            case KNIGHT: return new Knight(this.x,this.y);
            case QUEEN: return new Queen(this.x,this.y);
            case ROOK: return new Rook(this.x,this.y);
            default: return new Blank(this.x,this.y);
        }
    }
}
