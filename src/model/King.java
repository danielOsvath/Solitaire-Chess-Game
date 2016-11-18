package model;
/**
 * Class for King chess piece
 */
public class King implements BoardPiece {
    private int x;
    private int y;
    private String name = "King";
    private String abbr = "K";

    public King(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAbbr() {
        return abbr;
    }

    @Override
    public boolean canMove(int x, int y) {
        return false;
    }

    @Override
    public String toString() {
        return this.name + " at (" + x + ", " + y + ")";
    }
}
