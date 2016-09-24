/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.gacomponents;

import com.cricket.teamselector.app.Executor;
import com.cricket.teamselector.entity.Player;
import com.cricket.teamselector.utils.CommonFitnessCalculator;
import com.cricket.teamselector.utils.PlayerFitnessCalculatorByBudget;
import com.cricket.teamselector.utils.PlayerFitnessCalculatorByStats;
import com.cricket.teamselector.utils.Preferences;
import java.util.ArrayList;
import java.util.List;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 *
 * @author ZenitH
 */
    public class TeamFitnessCalculator extends FitnessFunction{

    
    @Override
    protected double evaluate(IChromosome teamChromosome) {
        return calculateFitness(teamChromosome);
    }

    private double calculateFitness(IChromosome a_potentialSolution){
        List< Player> playersList = new ArrayList<>();
        for(int i = 0; i<a_potentialSolution.size();i++){
            playersList.add((Player)a_potentialSolution.getGene(i).getAllele());
        }
        //setting the strategy of players.
        PlayerFitnessCalculatorByStats s = new PlayerFitnessCalculatorByStats(playersList);
        PlayerFitnessCalculatorByBudget b = new PlayerFitnessCalculatorByBudget(playersList, Executor.getsettings().targetBudget,playersList.size());
        CommonFitnessCalculator calculator = new CommonFitnessCalculator();
        calculator.setIfitnessCalculator(s);
        
        return (calculator.calculateFitness()+b.getTotalFitnessByBudget());


    }
    
}
