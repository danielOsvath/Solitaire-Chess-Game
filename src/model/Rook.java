package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for Rook chess piece
 */
public class Rook implements BoardPiece {
    private int currX;
    private int currY;
    private String name = "Rook";
    private String abbr = "R";

    public Rook(int x, int y)
    {
        this.currX = x;
        this.currY= y;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAbbr() {
        return abbr;
    }

    /**
     * Generates a list of the available moves for this piece at it's current position
     * If the desired move is one of the available moves then true otherwise false
     * @param x x value
     * @param y y value
     * @return
     */
    @Override
    public boolean canMove(int x, int y) {

        ArrayList<int[]> availableMoves = new ArrayList<>();
        int[] desiredLoc = {x,y};

        //Finds available moves
        for(int i=0;i<SoltrChessModel.DIMENSION;i++) {
            for(int j=0;j<SoltrChessModel.DIMENSION;j++) {
                if((i==currY&&j!=currX)||(j==currX&&i!=currY)) {
                    int[] coord = new int [2];
                    coord[0] = j;
                    coord[1] = i;
                    availableMoves.add(coord);
                }
            }
        }

        int[] currentLoc = {x, y};
        boolean contains = false;

        for(int[] coord:availableMoves)
        {
            if(Arrays.equals(coord,currentLoc)){
                contains=true;
            }
        }
        return contains;
    }

    @Override
    public String toString() {
        return this.name + " at (" + currX + ", " + currY + ")";
    }
}
