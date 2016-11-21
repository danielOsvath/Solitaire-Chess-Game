/**
 * Part of SoltrChessLayout project.
 * Created 10 2015
 *
 * @author James Heliotis
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */

package gui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

/**
 * A miniature chess board
 *
 * @author James Heliotis
 */
public class SoltrChessGUI extends Application implements Observer {
    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the UI
     */
    public void start( Stage stage ) {
    }

    /**
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {

    }
}
