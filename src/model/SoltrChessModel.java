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

        if (currentPiece.getName().equals(BLANK) ||
                ((pieceX == toX) && (pieceY == toY)) ){
            return false;

        }else{
            return (piecePresent(toX,toY) && currentPiece.canMoveTo(toX,toY)
            && !(figureInPath(pieceX,pieceY,toX,toY)) );

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
        return !(board[atX][atY].getAbbr().equals(BLANK));
    }

    /**
     * Move a piece on the board.
     *
     * Precondition: VALID MOVE.
     * Post Condition: Piece moved to the new location replacing other piece.
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

        setChanged();
        notifyObservers();
    }

    //temporary isGoal func -> move to configuration later.
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
     */
    public void solve(){
        Backtracker solver = new Backtracker();

        SoltrChessConfig config = new SoltrChessConfig(board);

        Optional<Configuration> steps = solver.solve(config);

        if(steps.isPresent()){
            board = steps.get().getBoard();
        }
//        for (Configuration current : steps){
//
//            board = current.getBoard();
//
        setChanged();
        notifyObservers();
//
//            try{
//                TimeUnit.SECONDS.sleep(3);
//            }catch (InterruptedException e){
//
//            }
//
//        }

    }

    /**
     *
     * @return
     */
    public BoardPiece[][] getBoard() {
        return board;
    }

    /**
     * Pre-condition: Valid Move for figure in direction
     * and piece present at destination.
     *
     * NO JUMPING OVER FIGURES!
     *
     * Determine whether there is a figure between two coordinates
     * (except for knight)
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    private boolean figureInPath(int fromX, int fromY, int toX, int toY){

        if(board[fromX][fromY].getAbbr().equals(KNIGHT)){
            return false;
        }else{
            String typeOfMove = board[fromX][fromY].typeOfMove(fromX,fromY,toX,toY);

            switch(typeOfMove){ //is there figure?
                case "DIAGONAL": return checkDiagPath(fromX,fromY,toX,toY);
                case "HORIZONTAL": return checkHorizontalPath(fromX,fromY,toX,toY);
                case "VERTICAL" : return checkVerticalPath(fromX,fromY,toX,toY);
            }
            return true;
        }
    }

    /**
     * Determine whether there is a piece between two coordinates vertically.
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    private boolean checkVerticalPath(int fromX, int fromY, int toX, int toY) {

        int smaller;
        int greater;

        if (fromX < toX) {
            smaller = fromX;
            greater = toX;
        } else {
            smaller = toX;
            greater = fromX;

        }

        for (int cur = smaller + 1; cur < greater; cur++) {
            if (piecePresent(cur, fromY)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determine whether there is a piece between two coordinates horizontally.
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    private boolean checkHorizontalPath(int fromX, int fromY, int toX, int toY){

        int smaller;
        int greater;

        if(fromY < toY){
            smaller = fromY;
            greater = toY;
        }else {
            smaller = toY;
            greater = fromY;

        }

        for (int cur = smaller + 1; cur < greater; cur++){
            if (piecePresent(fromX,cur)){
                return true;
            }
        }

        return false;
    }

    /**
     * Determine whether there is a figure in the path between the coordinates.
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return true if there is a piece, else false.
     */
    private boolean checkDiagPath(int fromX, int fromY, int toX, int toY){

        if( (toX < fromX)&& (toY < fromY)){

            int curX = fromX - 1;
            int curY = fromY - 1;

            while (toX < curX && toY < curY){
                if (piecePresent(curX,curY)){
                    return true;
                }
                curX--;
                curY--;
            }

        }else if(( toX < fromX) && (toY > fromY) ) {

            int curX = fromX - 1;
            int curY = fromY + 1;

            while (toX < curX && toY > curY){
                if (piecePresent(curX,curY)){
                    return true;
                }
                curX--;
                curY++;
            }

        }else if( (toX > fromX) && (toY < fromY) ){

            int curX = fromX + 1;
            int curY = fromY - 1;

            while (toX > curX && toY < curY){
                if (piecePresent(curX,curY)){
                    return true;
                }
                curX++;
                curY--;
            }

        }else{

            int curX = fromX + 1;
            int curY = fromY + 1;

            while (toX > curX && toY > curY){
                if (piecePresent(curX,curY)){
                    return true;
                }
                curX++;
                curY++;
            }

        }

        return false;

    }
}
