package backtracking;
/*
 * SoltrChessConfig.java
 * Created by Daniel Osvath Londono on 11/23/16.
 */


import model.BoardPiece;
import model.SoltrChessModel;
import model.pieces.Blank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static model.SoltrChessModel.*;

/**
 * The configuration class for backtracking.
 *
 * @author Daniel Osvath Londono
 * @author Nathan Cassata
 * @since 11/23/16
 */
public class SoltrChessConfig implements Configuration{

    private BoardPiece[][] config;

    /**
     *
     * @param board
     */
    public SoltrChessConfig(BoardPiece[][] board){

        config = new BoardPiece[DIMENSION][DIMENSION];

        for(int row = 0; row < DIMENSION; row++){
            for (int col = 0; col < DIMENSION; col++){
                this.config[row][col] = board[row][col].clone();
            }
        }
    }

    /**
     *
     * @param copy
     */
    public SoltrChessConfig(SoltrChessConfig  copy){

        this.config = new BoardPiece[DIMENSION][DIMENSION];

        for(int row = 0; row < DIMENSION; row++){
            for (int col = 0; col < DIMENSION; col++){
                this.config[row][col] = copy.config[row][col].clone();
            }
        }

    }

    /**
     *
     * @return
     */
    @Override
    public Collection<Configuration> getSuccessors() {

        Collection<Configuration> successors = new ArrayList<>();

        for (BoardPiece[] row : config){

            for (BoardPiece piece : row){

                if(!(piece.getAbbr().equals(BLANK))){

                    for ( int[] coordinates: getPossibleMoves(piece)){

                        SoltrChessConfig successor = new SoltrChessConfig(this);

                        successor.makeMove(piece.x,piece.y,coordinates[0],coordinates[1]);

                        successors.add(successor);

                    }

                }
            }
        }

        return successors;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /**
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    private void makeMove(int fromX, int fromY, int toX, int toY){

        BoardPiece piece = config[fromX][fromY];

        piece.moveTo(toX,toY);

        config[toX][toY] = piece;

        config[fromX][fromY] = new Blank(fromX,fromY);
    }

    /**
     * Returns an array of possible move coordinates
     * for a piece, as an (x,y) array.
     *
     * @param mypiece piece to move
     * @return array of possible moves
     */
    private ArrayList<int[]> getPossibleMoves(BoardPiece mypiece){

        ArrayList<int[]> moves = new ArrayList<>();

        for (int toX = 0; toX < DIMENSION; toX++) {

            for (int toY = 0; toY < DIMENSION; toY++) {

                BoardPiece current = config[toX][toY];

                int myX = mypiece.x;
                int myY = mypiece.y;

                if ( !(current.getAbbr().equals(BLANK)) &&
                        !(myX == toX && myY == toY) &&
                        !(figureInPath(config, myX, myY, toX, toY))) {

                    if(mypiece.canMoveTo(toX,toY)){

                        int[] coordinate = {toX,toY};
                        moves.add(coordinate);
                    }
                }
            }
        }

        return moves;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isGoal() {

        int countPiece = 0;

        for (BoardPiece[] row : config){
            for (BoardPiece element : row){
                if (!element.getAbbr().equals(BLANK)){
                    countPiece++;
                }
            }
        }

        return (countPiece == 1);
    }

    @Override
    public BoardPiece[][] getBoard(){
        return config;
    }

    @Override
    public void printBoard(){

        for(BoardPiece[] row : config){
            for(BoardPiece element : row){
                System.out.print(element.getAbbr() + " ");
            }
            System.out.print("\n");
        }

    }
}
