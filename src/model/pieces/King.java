package model.pieces;

import model.BoardPiece;

/**
 * Class for King chess piece
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */

public class King implements BoardPiece {

    private int x;
    private int y;
    private String name = "King";
    private String abbr = "K";

    /**
     *
     * @param x
     * @param y
     */
    public King(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    @Override
    public String getAbbr() {
        return abbr;
    }

    /**
     *
     * @param x x value
     * @param y y value
     * @return
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.name + " at (" + x + ", " + y + ")";
    }
}
