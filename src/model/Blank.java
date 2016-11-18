package model;
/**
 * Class for Blank spot
 * */
public class Blank implements BoardPiece {
    private int x;
    private int y;
    private String name = "Blank";
    private String abbr = "-";

    public Blank(int x, int y)
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
