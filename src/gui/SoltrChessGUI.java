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

    private BackgroundImage dark;
    private BackgroundImage light;
    private BackgroundImage white;
    private BackgroundImage blue;


    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the UI
     */
    public void start( Stage stage ) {

        //Making the BorderPane and GridView
        BorderPane borderPane = new BorderPane();
        grid = new GridPane();

        generateGrid();
        messageField = new Label("Loaded " + filename);

        instantiateImages();

        borderPane.setCenter(grid);
        displayBoard();

        //TOP Message Field
        borderPane.setTop(messageField);

        //Bottom Box
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        Button newGameBtn = new Button("New Game");
        newGameBtn.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    filename = file.getPath();
                    newGameModel(filename);
                }
            }
        });

        Button restartBtn = new Button("Restart");
        restartBtn.setOnAction(event -> {
            tryToOpenFile(filename,false);
            messageField.setText("Game loaded: " + filename);
//            initializeView();
        });

        Button hintBtn = new Button("Hint");
        hintBtn.setOnAction( event -> {
            if(model.isGoal()){
                messageField.setText("You won! No moves left.");
            }else {
                if(!this.model.hint())
                    messageField.setText("No solution possible from current state.");
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
        borderPane.setBottom(bottomBox);

        Scene scene = new Scene(borderPane);
        stage.setTitle("Solitaire Chess - Nathan & Daniel");
        stage.setScene(scene);
        stage.show();

        for(BoardPiece[] row : model.getBoard()){
            for(BoardPiece element : row){
                System.out.print(element.getAbbr() + " ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Generates the grid.
     */
    private void generateGrid() {
        //Center GridView
        for(int i=0;i< SoltrChessModel.DIMENSION;i++) {
            for(int j=0;j<SoltrChessModel.DIMENSION;j++) {
                Button button = new Button();
                button.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        pieceSelected(button);
                    }
                });
                button.setMinHeight(150);
                button.setMinWidth(150);
                grid.add(button,i,j);
            }
        }

    }

    private void pieceSelected(Button button) {

        Background selectedBackground = new Background(blue);
        button.setBackground(selectedBackground);

        //All the pieces on the board
        BoardPiece[][] boardPieces = model.getBoard();

        //coordinates of button on grid view
        int x = GridPane.getRowIndex(button);
        int y = GridPane.getColumnIndex(button);

        //If there is a piece that is already selected it is referenced here
        BoardPiece pieceAlreadySelected = null;

        //If one exists finds piece already selected
        for(BoardPiece[]boardRow:boardPieces) {
            for(BoardPiece piece:boardRow) {
                if(piece.selected) {
                    pieceAlreadySelected = piece;
                }
            }
        }

        //For Piece currently selected
        BoardPiece pieceCurrentlySelected = boardPieces[x][y];
        pieceCurrentlySelected.selected = true;

        messageField.setText(pieceCurrentlySelected.getName() + " Selected");
        System.out.println("(" + x + " , " + y + ") Selected: " + pieceCurrentlySelected.getName());

        if(!(pieceAlreadySelected==null)) {
            System.out.println("This piece currently selected: " + pieceAlreadySelected.getName());

            System.out.println("(" +pieceAlreadySelected.x + ", " + pieceAlreadySelected.y + ") "
                    + "trying to move to: (" + x + " , " + y + ")");

            if(model.canMovePieceTo(pieceAlreadySelected.x, pieceAlreadySelected.y, x,y)) {
                System.out.println("Can Move to");
                model.movePieceTo(pieceAlreadySelected.x, pieceAlreadySelected.y, x,y);
                pieceAlreadySelected.selected = false;
                messageField.setText(pieceAlreadySelected.getName()+" moved to ("+pieceCurrentlySelected.x+", "
                        +pieceCurrentlySelected.y+") and takes "+pieceCurrentlySelected.getName());
                if(model.isGoal())
                {
                    messageField.setText("You won! No moves left.");
                }
            } else { //If piece cannot move to

                pieceAlreadySelected.selected = false;
                pieceCurrentlySelected.selected = false;
                messageField.setText("Invalid Move.");
                displayBoard();
            }
        } else {
            BoardPiece pieceSelectedObj = boardPieces[x][y];
            pieceSelectedObj.selected = true;
        }

    }

    public void displayBoard(){

        setBoardPattern();

        BoardPiece[][] boardPieces = model.getBoard();

        for(BoardPiece[] row : model.getBoard()){
            for(BoardPiece element : row){
                System.out.print(element.getAbbr() + " ");
            }
            System.out.print("\n");
        }

        for(int i=0;i<SoltrChessModel.DIMENSION;i++) {

            for(int j=0;j<SoltrChessModel.DIMENSION;j++) {
                ObservableList<Node> childrens = grid.getChildren();
                //Matches coordinates of grid to items on GridPane
                for (Node node : childrens) {
                    if(GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == j) {
                        Button result = (Button)node;
//                        result.setText(boardPieces[i][j].getName());

                        setPieceImage(result, boardPieces[i][j].getName());

                        break;
                    }
                }

            }
        }

        if(model.solveStep!=0) messageField.setText("Step: " + model.solveStep);
    }


    private void restartModel(){
        tryToOpenFile(this.filename, false);
    }

    private void newGameModel(String filename){
        tryToOpenFile(filename, false);
    }

    private void instantiateImages() {
        dark = new BackgroundImage(new Image(getClass().getResource("resources/dark.png").toExternalForm()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        light = new BackgroundImage(new Image(getClass().getResource("resources/light.png").toExternalForm()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        blue = new BackgroundImage(new Image(getClass().getResource("resources/blue.png").toExternalForm()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        white = new BackgroundImage(new Image(getClass().getResource("resources/white.png").toExternalForm()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }

    private void setPieceImage(Button button, String name) {

        ImageView img = new ImageView();

//        img.preserveRatioProperty();
//        img.setScaleX(1);
//        img.setScaleY(1);
        button.setGraphic(getBtnImage(name.toLowerCase()));
//        button.setMinHeight(150);
//        button.setMinWidth(150);
    }

    /**
     *
     * @param name
     * @return
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
     *
     */
    public void setBoardPattern() {
        ObservableList<Node> childrens = grid.getChildren();
        for(int i=0;i<childrens.size();i++)
        {
            Node current = childrens.get(i);
            Button currentBtn = (Button)current;

            if(i<4||(i>7&&i<12)) {
                if (i % 2 == 0) {
                    Background selectedBackground = new Background(dark);
                    currentBtn.setBackground(selectedBackground);

                } else {
                    Background selectedBackground = new Background(light);
                    currentBtn.setBackground(selectedBackground);
                }
            } else{
                if (i % 2 == 0) {
                    Background selectedBackground = new Background(light);
                    currentBtn.setBackground(selectedBackground);

                } else {
                    Background selectedBackground = new Background(dark);
                    currentBtn.setBackground(selectedBackground);
                }

            }
        }
    }

    /**
     *
     * @param filename
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
                System.out.println("File Not Found");
                System.exit(1);
            }else{
                createWarningWindow("File Not Found");
            }
        }catch (MalformedInputException e){
            if(initial) {
                System.out.println("Board in file is malformed.");
                System.exit(1);
            } else {
                createWarningWindow("Board in file is malformed.");
            }
        }
    }

    private void createWarningWindow(String message) {
        Label label = new Label(message);
        Scene scene = new Scene(label);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Warning!");
        stage.show();
    }

    /**
     *
     * @param o
     * @param arg
     */
    public void updateMethod(Observable o, Object arg) {
        displayBoard();
        if(model.isGoal()) messageField.setText("Congratulations, you won!");
    }

    /**
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        Platform.runLater(() -> updateMethod(observable,o));
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

        tryToOpenFile(filename, true);

        this.model = new SoltrChessModel(this.filename);
        this.model.addObserver(this);
    }

}
