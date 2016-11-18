package ptui;

import model.*;

import static model.SoltrChessModel.DIMENSION;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Part of SoltrChess project.
 * Created 11 2015
 *
 * @author James Heliotis
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
    public void initializeView()
    {
        this.model.addObserver(this);
        update(this.model, null);

    }

    public void displayBoard()
    {
        int pos =1;
        for(ChessPiece c:this.model.getBoard())
        {
            System.out.print(" | ");
            System.out.print(c.getAbbr());
            if (pos % 4 == 0) {
                System.out.print(" | ");
                System.out.println();
            }
            pos++;
        }

        for(ChessPiece c:this.model.getBoard())
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
