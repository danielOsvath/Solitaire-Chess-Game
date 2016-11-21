package model;
/**
 * Class for Knight chess piece
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */
public class Knight implements BoardPiece {

    private int x;
    private int y;
    private String name = "Knight";
    private String abbr = "N";

    /**
     *
     * @param x
     * @param y
     */
    public Knight(int x, int y) {
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
