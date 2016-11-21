/*
 * BoardPiece.java
 */
package model;

/**
 * This interface is created to represent a Chess Piece.
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */
public interface BoardPiece {

    /**
     * Gets the name of the chess piece
     * @return chess piece name
     */
    public String getName();

    /**
     * Gets the abbreviation of the chess piece
     * @return the abbreviation
     */
    public String getAbbr();

    /**
     * Determines if chess piece can move to that coordinate
     * @param x x value
     * @param y y value
     * @return true: if can move to that spot, false: if cannot move to that spot
     */
    public boolean canMove(int x, int y);

    /**
     * Returns a String of the Chess Piece
     * @return The String
     */
    public String toString();

}
