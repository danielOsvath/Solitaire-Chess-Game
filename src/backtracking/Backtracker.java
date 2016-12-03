package backtracking;
/*
 * backtracking.Backtracker.java
 *
 * This file comes from the backtracking lab. It should be useful
 * in this project. A second method has been added that you should
 * implement.
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * This class represents the classic recursive backtracking algorithm.
 * It has a solver that can take a valid configuration and return a
 * solution, if one exists.
 * 
 * @author sps (Sean Strout @ RIT CS)
 * @author jeh (James Heliotis @ RIT CS)
 * @author Daniel Osvath Londono
 * @author Nathan Cassata
 */
public class Backtracker {

    /**
     * Try find a solution, if one exists, for a given configuration.
     * 
     * @param config A valid configuration
     * @return A solution config, or null if no solution
     */
    public Optional<Configuration> solve(Configuration config) {

        if (config.isGoal()) {
            return Optional.of(config);
        } else {
            for (Configuration child : config.getSuccessors()) {
                if (child.isValid()) {
                    //steps.add(child);
                    Optional<Configuration> sol = solve(child);
                    if (sol.isPresent()) {
                        return sol;
                    }
                }
            }
            // implicit backtracking happens here
        } 
        return Optional.empty();
    }

    /**
     * Find a goal configuration if it exists, and how to get there.
     * @param current the starting configuration
     * @return a list of configurations to get to a goal configuration.
     *         If there are none, return null.
     */
    public List< Configuration > solveWithPath( Configuration current ) {

        List<Configuration> steps = new LinkedList<>();

        while (current != null) {

            if (current.isGoal()) {

                steps.add(current);

                return steps;

            } else {

                Configuration step = aChildHasSolution(current);

                if(step != null){
                    steps.add(step);
                    current = step;
                }else {
                    break;
                }

            }
        }

        System.out.println("No Solution");
        return null;
    }

    /**
     * Determine if a child has a solution, if so return the child that
     * leads to a solution, else return null.
     *
     * @param current current configuration
     * @return child that produces solution or null.
     */
    private Configuration aChildHasSolution(Configuration current){

        for (Configuration child : current.getSuccessors()) {
            if (solve(child) != null) {
                return child;
            }
        }

        return null;
    }

}
