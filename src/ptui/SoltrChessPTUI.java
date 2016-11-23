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
    //test making moves with figures, playing some puzzles.

    // VIEW

    @Override
    public void update(Observable observable, Object o) {
        displayBoard(); //update the board.
    }
}
