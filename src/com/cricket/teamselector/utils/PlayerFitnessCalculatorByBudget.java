/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.utils;

import com.cricket.teamselector.entity.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zenith
 */
public class PlayerFitnessCalculatorByBudget  {
    private List<Player> players;
    private int numOfPlayers ;
    private int user_budget;//CommonValues.getMaximumUserBudget();
    public PlayerFitnessCalculatorByBudget(List<Player> players, int userBudget, int numberOfPlayers) {
        user_budget = userBudget;
        this.numOfPlayers =numberOfPlayers;
        this.players = new ArrayList<>();
        for (Player playerID : players) {
            
            this.players.add(playerID);
       
        }
    }
    
    private double teamBudgetFeasibility(int teamBudget){
        int teamBudgetTot = teamBudget;
        if( teamBudgetTot <= user_budget){
            System.out.println("Finally a team under the user enterd budget is generated : ");
            System.out.println(300*numOfPlayers);
            return (300*numOfPlayers);
        }
        else{
            System.err.println("team that exceeded the user enterd budget is generated : ");
            return 0;
        }
    }
  
    public double getTotalFitnessByBudget() {
        int teamBudget = 0;
        for(Player play : players ){
            teamBudget += play.getBaseValue();
        }
        System.out.println("The genereated teams total budget : "+teamBudget);
        return teamBudgetFeasibility(teamBudget);
    }
    
}
