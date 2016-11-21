/*
 * Blank.java
 */
package model;

/**
 * Class for Blank spot
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 * */
public class Blank implements BoardPiece {

    private int x;
    private int y;
    private String name = "Blank";
    private String abbr = "-";

    /**
     *
     * @param x
     * @param y
     */
    public Blank(int x, int y)
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
    public boolean canMove(int x, int y) {
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
