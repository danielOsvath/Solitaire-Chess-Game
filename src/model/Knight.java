package model;
/**
 * Class for Knight chess piece
 */
public class Knight implements ChessPiece{
    private int x;
    private int y;
    private String name = "Knight";
    private String abbr = "N";

    public Knight(int x, int y)
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
