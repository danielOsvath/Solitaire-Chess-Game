package model;
/**
 * Class for Queen chess piece
 */
public class Queen implements ChessPiece{
    private int x;
    private int y;
    private String name = "Queen";
    private String abbr = "Q";

    public Queen(int x, int y)
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
