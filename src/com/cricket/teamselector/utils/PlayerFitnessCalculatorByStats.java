/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.utils;

import com.cricket.teamselector.app.Executor;
import com.cricket.teamselector.data.StatisticsProvider;
import com.cricket.teamselector.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zenith
 */
public class PlayerFitnessCalculatorByStats implements IFitnessCalculator {
    int numberOfYears = Executor.getsettings().selectedYears.size(); 
    int highstNumberOfBallsFaced;
    int highstNumberOfBallsBowled;
    int highstNumberOfInningsPlayed;
    int highstNumberOfMatchessPlayed;
    
    
    
        
    

    /**
     * @return the totalFitnessByBattingAverage
     */
    public static double getTotalFitnessByBattingAverage() {
        return totalFitnessByBattingAverage;
    }

    /**
     * @param aTotalFitnessByBattingAverage the totalFitnessByBattingAverage to
     * set
     */
    public static void setTotalFitnessByBattingAverage(double aTotalFitnessByBattingAverage) {
        totalFitnessByBattingAverage = aTotalFitnessByBattingAverage;
    }

    /**
     * @return the totalFitnessByBattingStrikeRate
     */
    public static double getTotalFitnessByBattingStrikeRate() {
        return totalFitnessByBattingStrikeRate;
    }

    /**
     * @param aTotalFitnessByBattingStrikeRate the
     * totalFitnessByBattingStrikeRate to set
     */
    public static void setTotalFitnessByBattingStrikeRate(double aTotalFitnessByBattingStrikeRate) {
        totalFitnessByBattingStrikeRate = aTotalFitnessByBattingStrikeRate;
    }

    /**
     * @return the totalFitnessByCatches
     */
    public static double getTotalFitnessByCatches() {
        return totalFitnessByCatches;
    }

    /**
     * @param aTotalFitnessByCatches the totalFitnessByCatches to set
     */
    public static void setTotalFitnessByCatches(double aTotalFitnessByCatches) {
        totalFitnessByCatches = aTotalFitnessByCatches;
    }

    /**
     * @return the totalFitnessByStumpings
     */
    public static double getTotalFitnessByStumpings() {
        return totalFitnessByStumpings;
    }

    /**
     * @param aTotalFitnessByStumpings the totalFitnessByStumpings to set
     */
    public static void setTotalFitnessByStumpings(double aTotalFitnessByStumpings) {
        totalFitnessByStumpings = aTotalFitnessByStumpings;
    }
    
    private double playersFitness = 0; 
    private List<Player> players;
    private static double totalFitnessByBowlingAverage;
    private static double totalFitnessByBowlingStrikeRate;
    private static double totalFitnessByBowlingEconomy;
    private static double totalFitnessByBattingAverage;
    private static double totalFitnessByBattingStrikeRate;
    private static double totalFitnessByCatches;
    private static double totalFitnessByStumpings;
    private static double totalFitness;
     
     
    
    
    /**
     * @return the totalFitnessByAverage
     */
    public static double getTotalFitnessByAverage() {
        return totalFitnessByBowlingAverage;
    }

    /**
     * @param aTotalFitnessByAverage the totalFitnessByAverage to set
     */
    public static void setTotalFitnessByAverage(double aTotalFitnessByAverage) {
        totalFitnessByBowlingAverage = aTotalFitnessByAverage;
    }

    /**
     * @return the totalFitnessByStrikeRate
     */
    public static double getTotalFitnessByStrikeRate() {
        return totalFitnessByBowlingStrikeRate;
    }

    /**
     * @param aTotalFitnessByStrikeRate the totalFitnessByStrikeRate to set
     */
    public static void setTotalFitnessByStrikeRate(double aTotalFitnessByStrikeRate) {
        totalFitnessByBowlingStrikeRate = aTotalFitnessByStrikeRate;
    }

    /**
     * @return the totalFitnessByEconomy
     */
    public static double getTotalFitnessByEconomy() {
        return totalFitnessByBowlingEconomy;
    }

    /**
     * @param aTotalFitness the totalFitness to set
     */
    public static void setTotalFitness(double aTotalFitness) {
        totalFitness = aTotalFitness;
    }

    public PlayerFitnessCalculatorByStats(List<Player> playerObj) {
       
        StatisticsProvider pdao = new StatisticsProvider();
            highstNumberOfBallsFaced=  pdao.getHighestBallsFaceed();          
            highstNumberOfInningsPlayed=   pdao.getHighestBattedInnings();
            
        try {
            highstNumberOfMatchessPlayed = pdao.getHighestNumberOfMatchesPlayed();
            highstNumberOfBallsBowled  = pdao.getHighestNumberOfBallsBowled();
        } catch (Exception ex) {
            Logger.getLogger(PlayerFitnessCalculatorByStats.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        players = new ArrayList<>();
        for (Player playerID : playerObj) {
            players.add(playerID);
        }
    }

    private double getBowlingFitnessByEconomy(int i) throws Exception {
        double fitness =0;
           
           
        int noOfCount = players.get(i).getBallsBowled() / 6;
        int maxNumberCount = highstNumberOfBallsBowled / 6;//CommonValues.getMaximumOvers(); 
        int noOfYears = numberOfYears; //CommonValues.getNumberOfYears();
        fitness = StatisticFitnessCalculatorHelper.getPlayerFitness("BowlingEcon", noOfCount, maxNumberCount, noOfYears, players.get(i).getBowlingEcon());


        return fitness;
     
    }

    private double getBowlingFitnessByAverage(int i) throws Exception {
       double fitness =0;
                    int noOfCount = players.get(i).getBallsBowled()/6;
                    int maxNumberCount = highstNumberOfBallsBowled/6;//CommonValues.getMaximumOvers();
                    int noOfYears = numberOfYears;// CommonValues.getNumberOfYears();
         return     fitness= StatisticFitnessCalculatorHelper.getPlayerFitness("BowlingAvg", noOfCount, maxNumberCount, noOfYears, players.get(i).getBowlingAve());
                     
          
    }

    private double getBowlingFitnessByStrikeRate(int i) throws Exception {
        double fitness =0;
                    int noOfCount = players.get(i).getBallsBowled()/6;
                    int maxNumberCount = highstNumberOfBallsBowled/6;;
                    int noOfYears = numberOfYears; //CommonValues.getMaximumInnings();
             return  fitness = StatisticFitnessCalculatorHelper.getPlayerFitness("BowlingStrikeRate", noOfCount, maxNumberCount, noOfYears,  players.get(i).getBowlingSr());
          
    }

    private double getBattingFitnessByAverage(int i) throws Exception {
        double fitness =0;
                    int noOfCount = players.get(i).getBattedInnings();
                    int maxNumberCount = highstNumberOfInningsPlayed;//CommonValues.getMaximumInnings();
                    int noOfYears = numberOfYears;//CommonValues.getNumberOfYears();
                    return fitness = StatisticFitnessCalculatorHelper.getPlayerFitness("BattingAvg", noOfCount, maxNumberCount, noOfYears, players.get(i).getBattingAve());
         
    }

    private double getBattingFitnessByStrikeRate(int i) throws Exception {
            double fitness =0;
       
                    int noOfCount = players.get(i).getBf(); //// NEED TO get the innings coloumn and set to the below parametr
                    
                    int maxNumberCount = highstNumberOfBallsFaced;//CommonValues.getMaxBallsFaced();
                    int noOfYears = numberOfYears;//CommonValues.getNumberOfYears();;
              return      fitness = StatisticFitnessCalculatorHelper.getPlayerFitness("BattingStrikeRate", noOfCount, maxNumberCount, noOfYears, players.get(i).getBattingSr());

    }
    
    private double getFeildingFitnessByCatches(int i) throws Exception {
            double fitness =0;
       
                    int noOfCount = players.get(i).getMatches(); //// NEED TO get the innings coloumn and set to the below parametr
                    
                    int maxNumberCount = highstNumberOfMatchessPlayed;//CommonValues.getMaxBallsFaced();
                    int noOfYears = numberOfYears;//CommonValues.getNumberOfYears();;
              return      fitness = StatisticFitnessCalculatorHelper.getPlayerFitness("Catches", noOfCount, maxNumberCount, noOfYears, players.get(i).getCt());

    }
    
    private double getFeildingFitnessByStumpings(int i) throws Exception {
            double fitness =0;
       
                    int noOfCount = players.get(i).getMatches(); //// NEED TO get the innings coloumn and set to the below parametr
                    
                    int maxNumberCount = highstNumberOfMatchessPlayed;//CommonValues.getMaxBallsFaced();
                    int noOfYears = numberOfYears;//CommonValues.getNumberOfYears();;
              return      fitness = StatisticFitnessCalculatorHelper.getPlayerFitness("Stumpings", noOfCount, maxNumberCount, noOfYears, players.get(i).getStumpings());

    }



    
    public double getPlayersTotalFitness(int i){
      
           double fintessForPlayer = 0;
           try {
               fintessForPlayer +=  getBowlingFitnessByAverage(i);
               fintessForPlayer +=  getBowlingFitnessByEconomy(i);
               fintessForPlayer +=  getBowlingFitnessByStrikeRate(i);
               fintessForPlayer +=  getBattingFitnessByAverage(i);
               fintessForPlayer +=  getBattingFitnessByStrikeRate(i);
               fintessForPlayer +=  getFeildingFitnessByCatches(i);
               fintessForPlayer +=  getFeildingFitnessByStumpings(i);
               
                
                System.out.println("Player Name : "+ players.get(i).getName()+"   FitnessValue : "+ fintessForPlayer);
                return fintessForPlayer;
           } catch (Exception ex) {
               Logger.getLogger(PlayerFitnessCalculatorByStats.class.getName()).log(Level.SEVERE, null, ex);
           }
             return fintessForPlayer;
            
        }
            
    
    
    
    @Override
    public double getTotalFitness() {
        double totalFitness =0;
        for (int i= 0;i<players.size();i++) {
          totalFitness += getPlayersTotalFitness( i);
         
        }
        return totalFitness;
    } 
}
