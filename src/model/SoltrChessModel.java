/*
 * SoltrChessModel.java
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

/**
 *  Definition for the model of the Solitaire Chess Game.
 *
 *  @author Nathan Cassata
 *  @author Daniel Osvath Londono
 *
 */
public class SoltrChessModel extends Observable {

    /**
     * The default size of the board
     */
    public static int DIMENSION = 4;

    public static final String BLANK = "-";
    public static final String BISHOP = "B";
    public static final String KING = "K";
    public static final String KNIGHT = "N";
    public static final String PAWN = "P";
    public static final String QUEEN = "Q";
    public static final String ROOK = "R";

    /**
     * The Chess Solitaire Board
     */
    private ArrayList<BoardPiece> board;

    /**
     * Construct a SoltrChessModel
     */
    public SoltrChessModel(String filename)
    {
        File file = new File(filename);

        try {
            Scanner sc = new Scanner(file);
            board = new ArrayList<>();
            int iterAmt = 0;
            int x = 0;
            int y = DIMENSION;

            while (iterAmt<(DIMENSION*DIMENSION)&&sc.hasNext())
            {
                String current = sc.next();
                if(current.equals(BLANK)) {
                    board.add(new Blank(x, y));
                } else if(current.equals(BISHOP)) {
                    board.add(new Bishop(x,y));
                } else if(current.equals(KING)) {
                    board.add(new King(x,y));
                } else if(current.equals(KNIGHT)) {
                    board.add(new Knight(x,y));
                } else if(current.equals(PAWN)) {
                    board.add(new Pawn(x,y));
                } else if(current.equals(QUEEN)) {
                    board.add(new Queen(x,y));
                } else if(current.equals(ROOK)) {
                    board.add(new Rook(x,y));
                }
                x++;
                if(x == DIMENSION)
                {
                    x = 0;
                    y--;
                }

                iterAmt++;
            }

        } catch (FileNotFoundException e)
        {
            System.out.println("File Not Found");
            System.exit(1);
        }


    }

    public ArrayList<BoardPiece> getBoard() {
        return board;
    }
}
