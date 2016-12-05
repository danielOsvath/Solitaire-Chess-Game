/*
 * Part of SoltrChessLayout project.
 * Created 10 2015
 *
 * @author James Heliotis
 * @author Nathan Cassata
 * @author Daniel Osvath Londono
 */

package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.*;
import model.BoardPiece;
import model.SoltrChessModel;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.MalformedInputException;
import java.util.Observable;
import java.util.Observer;

import static model.SoltrChessModel.BLANK;

/**
 * A miniature chess board
 *
 * @author James Heliotis
 */
public class SoltrChessGUI extends Application implements Observer {

    private SoltrChessModel model;
    private String filename;
    private GridPane grid;
    private Label messageField;
    private Stage stage;

    private BackgroundImage dark;
    private BackgroundImage light;
    private BackgroundImage blue;

    /**
     * Initializes the GUI
     *
     * @throws Exception exception
     */
    @Override
    public void init() throws Exception {

        filename = getParameters().getRaw().get(0);

        tryToOpenFile(filename, true);
        this.model.addObserver(this);

        instantiateImages();
    }


    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the UI
     */
    public void start( Stage stage ) throws Exception {

        Scene scene = new Scene( this.makeborderPane() );

        stage.setTitle( "Solitaire Chess - Nathan & Daniel" );
        stage.setScene( scene );
        stage.show();

        displayBoard();

        this.stage = stage;
    }

    /**
     * Updates the model and the board
     * @param o the observable
     * @param arg the object
     */
    private void updateMethod(Observable o, Object arg) {
        displayBoard();
    }

    /**
     * The MVC update method
     * @param observable the observable
     * @param o the object
     */
    @Override
    public void update(Observable observable, Object o) {
        Platform.runLater(() -> updateMethod(observable,o));
    }

    /**
     * Creates the BorderPane for the game
     * @return the BorderPane
     */
    private BorderPane makeborderPane(){

        //Making the BorderPane
        BorderPane borderPane = new BorderPane();

        //TOP Message Field
        borderPane.setTop( this.topMessage() );
        borderPane.setCenter( this.generateGrid() );
        borderPane.setBottom( this.bottomControls() );

        return borderPane;

    }

    /**
     * Creates the HBOX for the top Message Field
     * @return the HBOX with the message field
     */
    private HBox topMessage(){

        HBox top = new HBox();

        this.messageField = new Label("Loaded " + filename);

        top.getChildren().add( messageField );

        return top;
    }

    /**
     * Creates an HBOX for the bottom control buttons
     * @return the Bottom HBOX
     */
    private HBox bottomControls(){

        HBox bottomBox = new HBox();

        bottomBox.setAlignment(Pos.CENTER);

        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction(event -> openFileWindow() );

        Button restartBtn = new Button("Restart");
        restartBtn.setOnAction(event -> {
            tryToOpenFile(filename,false);
            messageField.setText("Game loaded: " + filename);
            displayBoard();
        });

        Button hintBtn = new Button("Hint");
        hintBtn.setOnAction( event -> {
            if(model.isGoal()){
                messageField.setText("You won! No moves left.");
            }else {
                if(!this.model.hint()) {
                    messageField.setText("No solution possible from current state.");
                }else{
                    messageField.setText("Hint was given.");
                }
            }
        } );

        Button solveBtn = new Button("Solve");
        solveBtn.setOnAction( event -> {
            if(model.isGoal()){
                messageField.setText("You won! No moves left.");
            }else {
                if(this.model.getSolveSteps() != null){
                    Thread sol = new SolveWithGUI(model);
                    sol.start();
                }else{
                    messageField.setText("No solution possible from current state.");
                }
            }
        } );

        bottomBox.getChildren().addAll(newGameBtn, restartBtn, hintBtn, solveBtn);

        return bottomBox;
    }

    /**
     * Opens a window to open a new board file
     */
    private void openFileWindow(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open New Board File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            filename = file.getPath();
            tryToOpenFile(filename,false);
            messageField.setText("Game loaded: " + filename);
        }
    }

    /**
     * Generates the grid.
     * @return the GridPane
     */
    private GridPane generateGrid() {

        grid = new GridPane();

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth( 50 );
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth( 50 );
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth( 50 );
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth( 50 );
        grid.getColumnConstraints().addAll( column1, column2, column3, column4 );
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight( 50 );
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight( 50 );
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight( 50 );
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight( 50 );
        grid.getRowConstraints().addAll( row1, row2, row3, row4 );

        //Center GridView
        for(int i=0;i< SoltrChessModel.DIMENSION;i++) {
            for(int j=0;j<SoltrChessModel.DIMENSION;j++) {
                Button button = new Button();
                button.setOnAction(event -> pieceSelected(button));
                button.setMinHeight(150);
                button.setMinWidth(150);
                grid.add(button,i,j);
            }
        }

        return grid;
    }

    /**
     * Sets the current board piece selected
     * @param button the button pushed
     */
    private void pieceSelected(Button button) {

        Background selectedBackground = new Background(blue);
        //All the pieces on the board
        BoardPiece[][] boardPieces = model.getBoard();

        //coordinates of button on grid view
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);

        //If there is a piece that is already selected it is referenced here
        BoardPiece alreadySelected = findSelected();

        //For Piece currently selected
        BoardPiece currentlySelected = boardPieces[row][col];

        if( alreadySelected != null ) {

            if(model.canMovePieceTo(alreadySelected.x, alreadySelected.y,
                    row, col)) {

                alreadySelected.selected = false;

                model.movePieceTo(alreadySelected.x, alreadySelected.y, row, col);

                messageField.setText(alreadySelected.getName() +
                        " moved to " + "(" + currentlySelected.x+
                        ", " + currentlySelected.y + ") taking "
                        + currentlySelected.getName() + ".");

            } else { //If piece cannot move to

                if(!currentlySelected.getAbbr().equals(BLANK)) {
                    messageField.setText("Invalid move. Select piece to move.");
                }else{
                    messageField.setText("Cannot move to blank. " +
                            "Select piece to move.");
                }

                displayBoard();
                alreadySelected.selected = false;
            }
        } else {

            if(!currentlySelected.getAbbr().equals(BLANK)){
                button.setBackground(selectedBackground);
                currentlySelected.selected = true;
                messageField.setText(currentlySelected.getName() + " selected.");
            }
        }

    }

    /**
     * If one exists finds piece already selected
     * @return the BoardPiece
     */
    private BoardPiece findSelected(){
        //If one exists finds piece already selected
        for(BoardPiece[] boardRow : model.getBoard()) {
            for(BoardPiece piece : boardRow) {
                if(piece.selected) {
                    return piece;
                }
            }
        }

        return null;
    }

    /**
     * Displays the board using the model
     */
    private void displayBoard(){

        if(model.isGoal()) messageField.setText("Congratulations, you won! | "
                + messageField.getText());

        for(int row = 0; row < SoltrChessModel.DIMENSION; row++) {

            for(int col = 0; col < SoltrChessModel.DIMENSION; col++) {

                Button currentButton = getButtonAt(row,col);

                if(currentButton != null){
                    String image = this.model.getBoard()[row][col].getName().toLowerCase();
                    currentButton.setGraphic(getBtnImage(image.toLowerCase()));
                    currentButton.setBackground(getBtnBackground(row,col));
                }

            }
        }

        if(model.solveStep != 0) messageField.setText("Step: " + model.solveStep);
    }

    /**
      Return the button from Grid using supplied coordinates
     * @param row the row coordinate
     * @param col the col coordinate
     * @return the Button
     */
    private Button getButtonAt(int row, int col){

        ObservableList<Node> buttons = grid.getChildren();

        for (Node button : buttons){
            if(GridPane.getRowIndex(button) == row &&
                    GridPane.getColumnIndex(button) == col){
                return (Button) button;
            }
        }

        return null;
    }


    /**
     * Instatiates the background images for the buttons
     */
    private void instantiateImages() {

        dark = new BackgroundImage(new Image(getClass().
                getResource("resources/dark.png").toExternalForm()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        light = new BackgroundImage(new Image(getClass().
                getResource("resources/light.png").toExternalForm()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        blue = new BackgroundImage(new Image(getClass().
                getResource("resources/blue.png").toExternalForm()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }

    /**
     * Returns the ImageView for the piece's type
     * @param name the name of the piece
     * @return the ImageView
     */
    private ImageView getBtnImage(String  name){

        if(name.equals("blank")) return null;
        Image img1 = new Image( getClass()
                .getResourceAsStream( "resources/" + name + ".png" ) );

        ImageView view = new ImageView( img1 );
        view.setFitHeight( 100 );
        view.setFitWidth( 100 );

        return view;
    }

    /**
     * Get the button's background for the coordinate
     * @param row the row coordinate
     * @param col the column coordinate
     * @return the Background
     */
    private Background getBtnBackground(int row, int col){

        Background selectedBackground;

        col = row + col;

        if(col % 2 == 0){
            selectedBackground = new Background(dark);
        }else{
            selectedBackground = new Background(light);
        }

        return selectedBackground;
    }


    /**
     * Attempts to open the file using what the user supplied
     * @param filename the filename the user supplied
     */
    private void tryToOpenFile(String filename, boolean initial){
        try {
            this.model = new SoltrChessModel(filename);
            if(!initial) {
                this.model.addObserver(this);
                displayBoard();
            }
        }catch (FileNotFoundException w){
            if(initial){
                System.out.println("File Not Found \n" + filename);
                System.exit(1);
            }else{
                createWarningWindow("File Not Found \n" + filename);
            }
        }catch (MalformedInputException e){
            if(initial) {
                System.out.println("Board in file is malformed. \n" + filename);
                System.exit(1);
            } else {
                createWarningWindow("Board in file is malformed. \n" + filename);
            }
        }
    }

    /**
     * Generates a warning window with the supplied message
     * @param message the supplied message
     */
    private void createWarningWindow(String message) {
        Label label = new Label(message);
        Scene scene = new Scene(label);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Warning!");
        stage.show();
    }


}
