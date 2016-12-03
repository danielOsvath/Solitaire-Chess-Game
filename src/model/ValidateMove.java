package model;
/*
 * filename.java
 * Created by Daniel Osvath Londono on 12/2/16.
 */


import static model.SoltrChessModel.*;

/**
 * Class Purpose
 *
 * @author Daniel Osvath Londono
 * @since 12/2/16
 */
public class ValidateMove {

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
    public static boolean canMovePieceTo(BoardPiece[][] myboard, int pieceX,
                                         int pieceY, int toX, int toY){

        BoardPiece currentPiece = myboard[pieceX][pieceY];

        if (currentPiece.getName().equals(BLANK) ||
                ((pieceX == toX) && (pieceY == toY)) ){
            return false;

        }else{
            return (piecePresent(myboard,toX,toY) &&
                    currentPiece.canMoveTo(toX,toY) &&
                    !(figureInPath(myboard, pieceX, pieceY, toX, toY)) );

        }
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
     * @param myboard board to check for,
     *                as parameter so SoltrChessConfig can use.
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    public static boolean figureInPath(BoardPiece[][] myboard, int fromX,
                                       int fromY, int toX, int toY){

        if(myboard[fromX][fromY].getAbbr().equals(KNIGHT)){
            return false;
        }else{
            String typeOfMove = myboard[fromX][fromY]
                    .typeOfMove(fromX,fromY,toX,toY);

            switch(typeOfMove){ //is there figure?
                case "DIAGONAL":
                    return checkDiagPath(myboard,fromX,fromY,toX,toY);
                case "HORIZONTAL":
                    return checkHorizontalPath(myboard,fromX,fromY,toX,toY);
                case "VERTICAL" :
                    return checkVerticalPath(myboard,fromX,fromY,toX,toY);
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
    private static boolean checkVerticalPath(BoardPiece[][] myboard, int fromX,
                                             int fromY, int toX, int toY) {

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
            if (piecePresent(myboard,cur, fromY)) {
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
    private static boolean checkHorizontalPath(BoardPiece[][] myboard, int fromX,
                                               int fromY, int toX, int toY){

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
            if (piecePresent(myboard,fromX,cur)){
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
    private static boolean checkDiagPath(BoardPiece[][] myboard, int fromX,
                                         int fromY, int toX, int toY){

        if( (toX < fromX)&& (toY < fromY)){

            int curX = fromX - 1;
            int curY = fromY - 1;

            while (toX < curX && toY < curY){
                if (piecePresent(myboard,curX,curY)){
                    return true;
                }
                curX--;
                curY--;
            }

        }else if(( toX < fromX) && (toY > fromY) ) {

            int curX = fromX - 1;
            int curY = fromY + 1;

            while (toX < curX && toY > curY){
                if (piecePresent(myboard,curX,curY)){
                    return true;
                }
                curX--;
                curY++;
            }

        }else if( (toX > fromX) && (toY < fromY) ){

            int curX = fromX + 1;
            int curY = fromY - 1;

            while (toX > curX && toY < curY){
                if (piecePresent(myboard,curX,curY)){
                    return true;
                }
                curX++;
                curY--;
            }

        }else{

            int curX = fromX + 1;
            int curY = fromY + 1;

            while (toX > curX && toY > curY){
                if (piecePresent(myboard,curX,curY)){
                    return true;
                }
                curX++;
                curY++;
            }

        }

        return false;

    }

    /**
     * Check if there is a piece at x,y of the board.
     *
     * @param atX
     * @param atY
     * @return
     */
    private static boolean piecePresent(BoardPiece[][] myboard, int atX, int atY){
        return !(myboard[atX][atY].getAbbr().equals(BLANK));
    }

}