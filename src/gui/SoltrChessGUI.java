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
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.*;
import model.BoardPiece;
import model.SoltrChessModel;

import java.util.Observable;
import java.util.Observer;

/**
 * A miniature chess board
 *
 * @author James Heliotis
 */
public class SoltrChessGUI extends Application implements Observer {

    private SoltrChessModel model;
    private String filename;
    private BorderPane borderPane;
    private GridPane grid;
    private Label messageField;


    /**
     * Construct the layout for the game. 
     *
     * @param stage container (window) in which to render the UI
     */
    public void start( Stage stage ) {

        borderPane = new BorderPane();

        grid = new GridPane();

        messageField = new Label("Select a Piece");

        for(int i=0;i< SoltrChessModel.DIMENSION;i++)
        {
            for(int j=0;j<SoltrChessModel.DIMENSION;j++)
            {
                Button button = new Button();
                button.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        pieceSelected(button);
                    }
                });
                button.setMinHeight(100);
                button.setMinWidth(100);
                grid.add(button,i,j);
            }
        }

        borderPane.setCenter(grid);
        displayBoard();

        borderPane.setTop(messageField);

        Scene scene = new Scene(borderPane);
        stage.setTitle("Solitaire Chess");
        stage.setScene(scene);
        stage.show();

        for(BoardPiece[] row : model.getBoard()){
            for(BoardPiece element : row){
                System.out.print(element.getAbbr() + " ");
            }
            System.out.print("\n");
        }
    }

    private void pieceSelected(Button button)
    {
        //All the pieces on the board
        BoardPiece[][] boardPieces = model.getBoard();

        //coordinates of button on grid view
        int y = grid.getColumnIndex(button);
        int x = grid.getRowIndex(button);

        //If there is a piece that is already selected it is referenced here
        BoardPiece pieceAlreadySelected = null;

        //If one exists finds piece already selected
        for(BoardPiece[]boardRow:boardPieces)
        {
            for(BoardPiece piece:boardRow)
            {
                if(piece.selected)
                {
                    pieceAlreadySelected = piece;
                }
            }
        }

        //For Piece currently selected
        BoardPiece pieceCurrentlySelected = boardPieces[x][y];
        pieceCurrentlySelected.selected = true;

        messageField.setText(pieceCurrentlySelected.getName() + " Selected");
//        System.out.println("(" + x + " , " + y + ") Selected: " + pieceCurrentlySelected.getName());

        if(!(pieceAlreadySelected==null)) {
//            System.out.println("This piece currently selected: " + pieceAlreadySelected.getName());
//
//            System.out.println("(" +pieceAlreadySelected.x + ", " + pieceAlreadySelected.y + ") "
//                    + "trying to move to: (" + x + " , " + y + ")");

            if(model.canMovePieceTo(pieceAlreadySelected.x, pieceAlreadySelected.y, x,y))
            {
//                System.out.println("Can Move to");
                model.movePieceTo(pieceAlreadySelected.x, pieceAlreadySelected.y, x,y);
                pieceAlreadySelected.selected = false;
                messageField.setText("Select a Piece");
                if(model.isGoal())
                {
                    messageField.setText("You won! No moves left.");
                }
            }
        } else {
            BoardPiece pieceSelectedObj = boardPieces[x][y];
            pieceSelectedObj.selected = true;
        }

    }

    public void displayBoard(){

        BoardPiece[][] boardPieces = model.getBoard();

        for(int i=0;i<SoltrChessModel.DIMENSION;i++)
        {
            for(int j=0;j<SoltrChessModel.DIMENSION;j++)
            {
                ObservableList<Node> childrens = grid.getChildren();
                //Matches coordinates of grid to items on GridPane
                for (Node node : childrens) {
                    if(grid.getRowIndex(node) == i && grid.getColumnIndex(node) == j) {
                        Button result = (Button)node;
                        result.setText(boardPieces[i][j].getName());

                        if(boardPieces[i][j].getName().equals("Blank"))
                        {
                            result.setText("");
                        }
                        break;
                    }
                }

            }
        }
    }

    /**
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Update called");
        displayBoard();
    }

    /**
     * Initializes the GUI
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        System.out.println("init: Initialize and connect to model");

        filename = getParameters().getRaw().get(0);
        System.out.println(filename);

        System.out.println("The filename is: "+this.filename);
        this.model = new SoltrChessModel(this.filename);
        this.model.addObserver(this);
    }
}
