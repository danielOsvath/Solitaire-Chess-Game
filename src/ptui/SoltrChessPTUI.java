/*
 * SoltrChessPTUI.java
 */
package ptui;

import backtracking.Backtracker;
import model.*;

import java.io.FileNotFoundException;
import java.nio.charset.MalformedInputException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

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
    private String originalfile;

    /**
     *
     * @param fileName
     */
    public SoltrChessPTUI( String fileName ) {
        originalfile = fileName;
        System.out.println("Game File: " + fileName);
        tryToOpenFile(fileName,true);
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
    private void displayBoard() {
        for(BoardPiece[] row : model.getBoard()){
            for(BoardPiece element : row){
                System.out.print(element.getAbbr() + " ");
            }
            System.out.print("\n");
        }
    }

    // CONTROLLER
    /**
     *
     */
    public void run() {
        Scanner in = new Scanner(System.in);

        while (true){

            System.out.print("[move,new,restart,hint,solve,quit]> ");
            String line = in.nextLine().toLowerCase();

            switch (line){

                case "move":
                    if(model.isGoal()){
                        System.out.println("You won! No moves left.");
                        break;
                    }
                    promptForMove(in);
                    break;

                case "new":

                    System.out.print("game file name: ");
                    String newFile = in.nextLine();
                    originalfile = newFile;
                    tryToOpenFile(newFile,false);
                    initializeView();
                    checkGoal();
                    break;

                case "restart":

                    System.out.println("Restarting game. ");
                    tryToOpenFile(originalfile,false);
                    initializeView();
                    checkGoal();
                    break;

                case "hint":
                    if(model.isGoal()){
                        System.out.println("You won! No moves left.");
                        break;
                    }
                    System.out.println("HINT");
                    if(!model.hint()) System.out.println("Not solvable, no hint.");
                    break;

                case "solve":
                    if(model.isGoal()){
                        System.out.println("You won! No moves left.");
                        break;
                    }
                    if(!model.solve())
                        System.out.println("Cannot solve current configuration.");
                    break;

                case "quit":
                    System.out.println("Thank you for playing.");
                    System.exit(1);

                default: break;
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
        }catch (FileNotFoundException w){
            System.out.println("File Not Found");
            if(initial) System.exit(1);
        }catch (MalformedInputException e){
            System.out.println("Board in file is malformed.");
            if(initial) System.exit(1);
        }
    }

    /**
     *
     * @param in
     */
    private void promptForMove(Scanner in){

        int fromX = promptForSourceX(in);
        int fromY = promptForSourceY(in);

        int toX = promptForDestX(in);
        int toY = promptForDestY(in);


        if(model.canMovePieceTo(fromX,fromY,toX,toY)) {
            model.movePieceTo(fromX,fromY,toX,toY);
            checkGoal();
        }else {
            System.out.println("\nInvalid move\n");
            displayBoard();
        }

    }

    /**
     *
     */
    private void checkGoal(){
        if(model.isGoal()) System.out.println("You won. Congratulations!");
    }

    /**
     *
     * @param in
     * @return
     */
    private int promptForSourceX(Scanner in){
        System.out.print("Source Row? ");
        return promptForValidInt(in);
    }

    /**
     *
     * @param in
     * @return
     */
    private int promptForSourceY(Scanner in){
        System.out.print("Source Col? ");
        return promptForValidInt(in);
    }

    /**
     *
     * @param in
     * @return
     */
    private int promptForDestX(Scanner in){
        System.out.print("Destination Row? ");
        return promptForValidInt(in);
    }

    /**
     *
     * @param in
     * @return
     */
    private int promptForDestY(Scanner in){
        System.out.print("Destination Col? ");
        return promptForValidInt(in);
    }

    /**
     * Prompts until user enters a valid integer between 0 and DIM-1.
     *
     * @param in input Scanner
     * @return valid integer within bounds of board.
     */
    private int promptForValidInt(Scanner in){
        while (true){
            String x = in.nextLine();

            try{
                int integer = Integer.parseInt(x);

                if(integer >= SoltrChessModel.DIMENSION){
                    System.out.print("Please enter an integer from 0 - "
                            + Integer.toString(SoltrChessModel.DIMENSION-1)
                            + ": ");
                }else{
                    return integer;
                }

            }catch (NumberFormatException e){
                System.out.print("Please enter a valid integer: ");
            }

        }
    }

    //test making moves with figures, playing some puzzles.

    // VIEW

    @Override
    public void update(Observable observable, Object o) {
        displayBoard(); //update the board.
    }
}