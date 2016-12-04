/*
 * SoltrChessModel.java
 */
package model;

import backtracking.Backtracker;
import backtracking.Configuration;
import backtracking.SoltrChessConfig;
import javafx.scene.layout.Pane;
import model.pieces.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.MalformedInputException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    private Backtracker solver;
    public int solveStep;

    /**
     * Construct a SoltrChessModel
     * @param filename The file being supplied
     */
    public SoltrChessModel(String filename) throws FileNotFoundException, MalformedInputException{

        board = new BoardPiece[DIMENSION][DIMENSION];
        solver = new Backtracker();

        File file = new File(filename);

        Scanner sc = new Scanner(file);
        readBoardFromfile(sc);
    }

    /**
     * Creates a board from the file
     * @param sc the scanner to use with the file already in it.
     */
    private void readBoardFromfile(Scanner sc) throws MalformedInputException{

        for (int i = 0; i < DIMENSION; i++){

            for (int j = 0; j < DIMENSION; j++) {

                if(sc.hasNext()){
                    String current = sc.next();

                    if(current.length() > 1) throw new MalformedInputException(0);

                    BoardPiece piece = getPiece(i,j,current);
                    board[i][j] = piece;
                }
                else{
                    throw new MalformedInputException(0);
                }
            }

        }

    }


    /**
     * Creates the BoardPiece with the specific type and location
     * @param x x value
     * @param y y value
     * @param type type of BoardPiece
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
     * Detemines if a Board Piece can make desired move
     * @param fromX current x value
     * @param fromY current y value
     * @param toX desired x value
     * @param toY desired y value
     * @return true if move can be made, false otherwise
     */
    public boolean canMovePieceTo(int fromX, int fromY, int toX, int toY){

        return ValidateMove.canMovePieceTo(this.board,fromX,fromY,toX,toY);

    }
    /**
     * Move a piece on the board.
     *
     * Precondition: VALID MOVE.
     * Post Condition: Piece moved to the new location replacing other piece.
     *
     * @param pieceX curennt piece's X value
     * @param pieceY current piece's Y value
     * @param toX desired X value
     * @param toY desired Y value
     */
    public void movePieceTo(int pieceX, int pieceY, int toX, int toY){

        BoardPiece piece = board[pieceX][pieceY];

        piece.moveTo(toX,toY);

        board[toX][toY] = piece;

        board[pieceX][pieceY] = new Blank(pieceX,pieceY);

        setChanged();
        notifyObservers();
    }

    /**
     * Determines if the current board is the goal
     * @return true if is goal, false if not.
     */
    public boolean isGoal(){

        int countPiece = 0;

        for (BoardPiece[] row : board){
            for (BoardPiece element : row){
                if (!element.getAbbr().equals(BLANK)){
                    countPiece++;
                }
            }
        }

        return (countPiece == 1);
    }

    /**
     * Solve the puzzle, update the model along the way.
     * Works fo PTUI.
     * @return true if solved, false if not solvable
     */
    public boolean solve(){

        List<Configuration> steps = getSolveSteps();

        if(steps != null){

            for (int curStep = 1; curStep < steps.size(); curStep++){

                System.out.println("STEP: " + curStep);

                board = steps.get(curStep).getBoard();

                setChanged();
                notifyObservers();

                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){

                }

            }

            return true;

        }else {
            return false;
        }

    }

    public void setBoard(BoardPiece[][] toSet, int sovestep){
        this.board = toSet;
        this.solveStep = sovestep;
        setChanged();
        notifyObservers();
    }

    /**
     * Generates a list of steps to solve
     * @return Returns a list of steps to solve the board
     */
    public List<Configuration> getSolveSteps(){

        SoltrChessConfig config = new SoltrChessConfig(board);
        return solver.solveWithPath(config);
    }

    /**
     * Supplies the board with a hint
     * @return true if the hint is allowed, false if board is currently incorrect
     */
    public boolean hint(){

        List<Configuration> steps = getSolveSteps();


        // current not int list -> not winnable.

        if(steps != null){
            for (int i = 0; i < steps.size(); i++){

                BoardPiece[][] curboard = steps.get(i).getBoard();

                if (equalBoards(curboard,board)){
                    board = steps.get(i+1).getBoard();
                    break;
                }

            }

            setChanged();
            notifyObservers();

            return true;

        }else {
            return false;
        }

    }

    /**
     * Determines if the boards are equal
     * @param one board one
     * @param two board two
     * @return true if boards equal, false otherwise
     */
    private boolean equalBoards(BoardPiece[][] one, BoardPiece[][] two){

        for (int row = 0; row < DIMENSION; row++){

            for (int col = 0; col < DIMENSION; col++){

                if ( !one[row][col].getAbbr().equals(two[row][col].getAbbr()) ){
                    return false;
                }

            }

        }

        return true;

    }

    /**
     * Gets the board
     * @return the board
     */
    public BoardPiece[][] getBoard() {
        return board;
    }
}