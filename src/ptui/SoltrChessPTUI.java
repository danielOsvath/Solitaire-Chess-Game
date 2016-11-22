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
        int pos = 1;
        for(BoardPiece c:this.model.getBoard())
        {
            System.out.print(" | ");
            System.out.print(c.getAbbr());
            if (pos % 4 == 0) {
                System.out.print(" | ");
                System.out.println();
            }
            pos++;
        }

        for(BoardPiece c:this.model.getBoard())
        {
            System.out.println(c.toString());
        }
    }

    // CONTROLLER
    public void run() {
    }

    // VIEW

    @Override
    public void update(Observable observable, Object o) {
        displayBoard();

    }
}
