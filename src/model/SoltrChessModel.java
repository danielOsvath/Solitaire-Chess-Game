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

                    if(current.length() > 1) malformed();

                    BoardPiece piece = getPiece(i,j,current);
                    board[i][j] = piece;
                }
                else{
                    malformed();
                }
            }

        }

    }

    /**
     *
     */
    private void malformed(){
        System.out.println("Board in file is malformed.");
        System.exit(1);
    }

    /**
     *
     * @param x
     * @param y
     * @param type
     * @return
     */
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

    /**
     * Determine whether a piece can be moved to another location,
     * capturing the figure at the location.
     *
     * @param pieceX
     * @param pieceY
     * @param toX
     * @param toY
     * @return
     */
    public boolean canMovePieceTo(int pieceX, int pieceY, int toX, int toY){

        BoardPiece currentPiece = board[pieceX][pieceY];

        if (currentPiece.getName().equals(BLANK)){
            return false;

        }else{
            return (piecePresent(toX,toY) && currentPiece.canMoveTo(toX,toY));
        }
    }

    /**
     * Check if there is a piece at x,y of the board.
     *
     * @param atX
     * @param atY
     * @return
     */
    private boolean piecePresent(int atX, int atY){
        return !(board[atX][atY].getName().equals(BLANK));
    }

    /**
     * Move a piece on the board.
     *
     * Precondition: VALID MOVE.
     * Post Condition: Piece moved to new location replacing other piece.
     *
     * @param pieceX
     * @param pieceY
     * @param toX
     * @param toY
     */
    public void movePieceTo(int pieceX, int pieceY, int toX, int toY){

        BoardPiece piece = board[pieceX][pieceY];

        piece.moveTo(toX,toY);

        board[toX][toY] = piece;

        board[pieceX][pieceY] = new Blank(pieceX,pieceY);

    }

    public BoardPiece[][] getBoard() {
        return board;
    }
}
