package backtracking;
/*
 * SoltrChessConfig.java
 * Created by Daniel Osvath Londono on 11/23/16.
 */


import model.BoardPiece;
import model.SoltrChessModel;

import java.util.Collection;

import static com.sun.tools.doclint.Entity.copy;

/**
 * The configuration class for backtracking.
 *
 * @author Daniel Osvath Londono
 * @author Nathan Cassata
 * @since 11/23/16
 */
public class SoltrChessConfig implements Configuration{

    private BoardPiece[][] config;

    /**
     *
     * @param copy
     */
    public SoltrChessConfig(SoltrChessConfig copy){

        config = new BoardPiece[4][4];

        //Copy config arrray
        for(int i = 0; i < copy.config.length; i++){
            System.arraycopy(copy.config[i], 0, this.config[i],
                    0, copy.config[i].length);
        }

    }

    /**
     *
     * @return
     */
    @Override
    public Collection<Configuration> getSuccessors() {


        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isValid() {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isGoal() {

        int countPiece = 0;

        for (BoardPiece[] row : config){
            for (BoardPiece element : row){
                if (!element.getAbbr().equals(SoltrChessModel.BLANK)){
                    countPiece++;
                }
            }
        }

        return (countPiece == 1);
    }
}
