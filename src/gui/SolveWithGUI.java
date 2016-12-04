package gui;
/*
 * SolveWithGUI.java
 * Created by Daniel Osvath Londono on 12/4/16.
 */


import backtracking.Configuration;
import javafx.scene.control.Label;
import model.SoltrChessModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Class Purpose
 *
 * @author Daniel Osvath Londono
 * @author Nathan Cassata
 * @since 12/4/16
 */
public class SolveWithGUI extends Thread {

    /* */
    private SoltrChessModel model;

    /**
     *
     * @param model
     */
    public SolveWithGUI(SoltrChessModel model){
        this.model = model;
    }

    /**
     *
     */
    public void run(){

        List<Configuration> steps = this.model.getSolveSteps();

        if(steps != null){

            for (int curStep = 1; curStep < steps.size(); curStep++){

                model.setBoard(steps.get(curStep).getBoard());

                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){

                }

            }

        }
    }
}