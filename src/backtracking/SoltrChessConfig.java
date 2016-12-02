package backtracking;
/*
 * SoltrChessConfig.java
 * Created by Daniel Osvath Londono on 11/23/16.
 */


import model.BoardPiece;
import model.SoltrChessModel;
import model.pieces.Blank;

import java.util.ArrayList;
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
     * @param copy
     */
    public SoltrChessConfig(SoltrChessConfig copy){

        config = new BoardPiece[DIMENSION][DIMENSION];

        //Copy config arrray
        for(int i = 0; i < copy.config.length; i++){
            System.arraycopy(copy.config[i], 0, this.config[i],
                    0, copy.config[i].length);
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

                if(!piece.getName().equals(BLANK)){
                    for ( int[] coordinates: getPossibleMoves(piece)){

                        SoltrChessConfig successor = new SoltrChessConfig(this);

                        successor.makeMove(piece.x,piece.y,coordinates[0],coordinates[1]);

                        successors.add(successor);

                    }

                }
            }
        }

        return null;
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

                if ( !(current.getName().equals(BLANK)) &&
                        !(myX == toX && myY == toY) ) {

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
}
