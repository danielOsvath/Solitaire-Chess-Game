package backtracking;
/*
 * SoltrChessConfig.java
 * Created by Daniel Osvath Londono on 11/23/16.
 */


import model.BoardPiece;
import model.SoltrChessModel;

import java.util.Collection;

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
     * @param config
     */
    public SoltrChessConfig(BoardPiece[][] config){
        this.config = config;
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
