/*
 * SoltrChessPTUI.java
 */
package ptui;

import model.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Part of SoltrChess project.
 * Created 11 2015
 *
 * @author James Heliotis
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */
public class SoltrChessPTUI implements Observer {

    private SoltrChessModel model;

    public SoltrChessPTUI( String fileName ) {
        model = new SoltrChessModel(fileName);
        initializeView();
    }

    /**
     * Initializes view
     */
    public void initializeView() {
        this.model.addObserver(this);
        update(this.model, null);

    }

    /**
     *
     */
    public void displayBoard() {
        for(BoardPiece[] row : model.getBoard()){
            for(BoardPiece element : row){
                System.out.print(element.getAbbr() + " ");
            }
            System.out.print("\n");
        }
    }

    // CONTROLLER

    public void run() {
    }
    //make sure to check that entered coordinates are within bounds

    // VIEW

    private String typeOfMove(int fromX, int fromY, int toX, int toY) {
        //NOTE: X and Y coordinates are reversed due to 2D array!

        int xChange = Math.abs(fromX - toX);
        int yChange = Math.abs(fromY - toY);

        if(fromX == toX){
            return "HORIZONTAL";

        } else if(fromY == toY){
            return "VERTICAL";

        }else if(yChange == xChange){
            return "DIAGONAL";

        }else if( ((yChange == 1) && (xChange == 2)) ||
                        ((yChange == 2) && (xChange == 1)) ){
            return "LSHAPE";

        }else{
            return "INVALID";
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        displayBoard(); //update the board.
    }
}
