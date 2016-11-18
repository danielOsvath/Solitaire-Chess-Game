package model;
/**
 * Class for Bishop chess piece
 */
public class Bishop implements BoardPiece {
    private int x;
    private int y;
    private String name = "Bishop";
    private String abbr = "B";

    public Bishop(int x, int y)
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
