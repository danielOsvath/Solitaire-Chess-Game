/*
 * Bishop.java
 */

package model.pieces;

import model.BoardPiece;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Class for Bishop chess piece
 *
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */
public class Bishop extends BoardPiece {

    private String name = "Bishop";
    private String abbr = "B";

    /**
     *
     * @param x
     * @param y
     */
    public Bishop(int x, int y) {
        moveTypes = Arrays.asList("VERTICAL", "HORIZONTAL");
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
     * @return
     */
    @Override
    public String toString() {
        return this.name + " at (" + x + ", " + y + ")";
    }
}
