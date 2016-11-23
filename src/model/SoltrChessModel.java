/*
 * SoltrChessModel.java
 */
package model;

import model.pieces.*;

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
    public static final int DIMENSION = 4;

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
    private BoardPiece[][] board;

    /**
     * Construct a SoltrChessModel
     */
    public SoltrChessModel(String filename) {
        board = new BoardPiece[DIMENSION][DIMENSION];

        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            readBoardFromfile(sc);

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(1);
        }

    }

    /**
     *
     * @param sc
     */
    private void readBoardFromfile(Scanner sc){

        for (int i = 0; i < DIMENSION; i++){

            for (int j = 0; j < DIMENSION; j++) {

                if(sc.hasNext()){
                    String current = sc.next();
                    BoardPiece piece = getPiece(i,j,current);
                    board[i][j] = piece;
                }
                else{
                    System.out.println("Board in file is malformed.");
                    System.exit(1);
                }
            }

        }

    }

    private BoardPiece getPiece(int x, int y, String type){
        switch (type){
            case BISHOP: return new Bishop(x,y);
            case KING: return new King(x,y);
            case KNIGHT: return new Knight(x,y);
            case PAWN: return new Pawn(x,y);
            case QUEEN: return new Queen(x,y);
            case ROOK: return new Rook(x,y);
            default: return new Blank(x,y);
        }
    }

    public BoardPiece[][] getBoard() {
        return board;
    }
}
