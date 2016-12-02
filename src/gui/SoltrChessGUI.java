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

    //Remembers which location is selected
    private int currentSelectedCol = -1;
    private int currentSelectedRow = -1;



    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the UI
     */
    public void start( Stage stage ) {

        borderPane = new BorderPane();

        grid = new GridPane();

        messageField = new Label();

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
    }

    private void pieceSelected(Button button)
    {
        int col = grid.getColumnIndex(button);
        int row = grid.getRowIndex(button);

        BoardPiece[][] boardPieces = model.getBoard();
        BoardPiece pieceSelectedObj = boardPieces[row][col];

        messageField.setText(pieceSelectedObj.getName() + " Selected");
        System.out.println("COL: " + col + " ROW: " + row + " Selected: " + pieceSelectedObj.getName());

        if(currentSelectedCol == -1 &&currentSelectedRow == -1) {

            //Updates selected position
            currentSelectedCol = col;
            currentSelectedRow = row;

        } else {
            if(model.canMovePieceTo(currentSelectedCol,currentSelectedRow,row,col)) {
                model.movePieceTo(currentSelectedCol,currentSelectedRow,row,col);


                if(model.isGoal()) {System.out.println("You won. Congratulations!");}
            }else {
                messageField.setText("Invalid Move!");
                currentSelectedRow = -1;
                currentSelectedCol = -1;
                displayBoard();
            }
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
                        if(!boardPieces[i][j].getName().equals("Blank"))
                        {
                            result.setText(boardPieces[i][j].getName());
                        }
                        break;
                    }
                }

            }
        }

//        for(BoardPiece[] row : model.getBoard()){
//            for(BoardPiece element : row){
//                System.out.print(element.getAbbr() + " ");
//            }
//            System.out.print("\n");
//        }
    }

    /**
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        displayBoard();
        System.out.println("Update called");
        System.out.println("");
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
