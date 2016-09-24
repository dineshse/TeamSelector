/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.utils;

/**
 *
 * @author zenith
 */
public class StatisticFitnessCalculatorHelper {
      //returns fitnes value based on the statConsidered, players count, maxnumberofCount, noOf years, and the statof the player considered
    public static double getPlayerFitness(String type, int noOfCount, int maxNumberCount, int noOfYears, double stat) {
        try {
            double fitness = 0;
            int inningsAverage = maxNumberCount / noOfYears; //inAvg is the avg of the topCount divided by number of years considered
            double minimumWeight = 100 / noOfYears; //miniWieght is used as the ratio
            for (int i = 0; i < noOfYears; i++) {
                if (noOfCount > (i * inningsAverage) && noOfCount <= (inningsAverage * (i + 1))) {
                    
                    switch (type) {
                        case "BattingAvg": {
                            fitness = getWeightedFitnessByBattingAvg(stat, minimumWeight * (i + 1));
                            break;
                        }
                        case "BattingStrikeRate": {
                            fitness = getWeightedFitnessForStrikeRate(stat, minimumWeight * (i + 1));
                            break;
                        }
                        case "BowlingStrikeRate": {
                            fitness = getWeightedFitnessForBowlingStrikeRate(stat, minimumWeight * (i + 1));
                            break;
                        }
                        case "BowlingEcon": {
                            fitness = getWeightedFitnessForBowlingEconomy(stat, minimumWeight * (i + 1));
                            break;
                        }
                        case "BowlingAvg": {
                            fitness = getWeightedFitnessForBowlingAvg(stat, minimumWeight * (i + 1));
                            break;
                        }
                            
                        case "Catches": {
                            fitness = getWeightedFitnessForCatches(stat, minimumWeight * (i + 1));
                            break;
                        }
                        case "Stumpings": {
                            fitness = getWeightedFitnessForStumpings(stat, minimumWeight * (i + 1));
                            break;
                        }
                            
                            
                    }
                }
            }
            return fitness;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private static double getWeightedFitnessByBattingAvg(double batAvg, double weight) {
        double fitness = 0;
        try {
            if (batAvg >= 0 && batAvg < 10) {
                fitness = 10;
            } else if (batAvg >= 10 && batAvg < 15) {
                fitness = 20;
            } else if (batAvg >= 15 && batAvg < 20) {
                fitness = 30;
            } else if (batAvg >= 20 && batAvg < 25) {
                fitness = 40;
            } else if (batAvg >= 25 && batAvg < 30) {
                fitness = 50;
            } else if (batAvg >= 30 && batAvg < 35) {
                fitness = 60;
            } else if (batAvg >= 35 && batAvg < 40) {
                fitness = 70;
            } else if (batAvg >= 40 && batAvg < 45) {
                fitness = 80;
            } else if (batAvg >= 45 && batAvg < 50) {
                fitness = 90;
            } else if (batAvg >= 50) {
                fitness = 100;
            }
            double outOfHundre = ((fitness * weight / 100)/100)*50;
          return outOfHundre;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private static double getWeightedFitnessForStrikeRate(double batStrikeRate, double weight) {
        try {
            double fitness = 0;

            if (batStrikeRate >= 0 && batStrikeRate < 25) {
                fitness = 10;
            } else if (batStrikeRate >= 25 && batStrikeRate < 50) {
                fitness = 20;
            } else if (batStrikeRate >= 50 && batStrikeRate < 75) {
                fitness = 30;
            } else if (batStrikeRate >= 75 && batStrikeRate < 100) {
                fitness = 40;
            } else if (batStrikeRate >= 100 && batStrikeRate < 125) {
                fitness = 50;
            } else if (batStrikeRate >= 125 && batStrikeRate < 150) {
                fitness = 60;
            } else if (batStrikeRate >= 150 && batStrikeRate < 175) {
                fitness = 70;
            } else if (batStrikeRate >= 175 && batStrikeRate < 200) {
                fitness = 80;
            } else if (batStrikeRate >= 200 && batStrikeRate < 225) {
                fitness = 90;
            } else if (batStrikeRate >= 225) {
                fitness = 100;
            }
            double outOfHundre = ((fitness * weight / 100)/100)*50;
          return outOfHundre;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private static double  getWeightedFitnessForBowlingStrikeRate(double bwStrikeRate, double weight){
        double fitness = 0;
        try{
        if (bwStrikeRate > 0 && bwStrikeRate < 10) {
            fitness = 100;
        } else if (bwStrikeRate >= 10 && bwStrikeRate < 12.5) {
            fitness = 95;
        } else if (bwStrikeRate >= 12.5 && bwStrikeRate < 15) {
            fitness = 90;
        } else if (bwStrikeRate >= 15 && bwStrikeRate < 17.5) {
            fitness = 85;
        } else if (bwStrikeRate >= 17.5 && bwStrikeRate < 20) {
            fitness = 80;
        } else if (bwStrikeRate >= 20 && bwStrikeRate < 22.5) {
            fitness = 75;
        } else if (bwStrikeRate >= 22.5 && bwStrikeRate < 25) {
            fitness = 70;
        } else if (bwStrikeRate >= 25 && bwStrikeRate < 27.5) {
            fitness = 65;
        } else if (bwStrikeRate >= 27.5 && bwStrikeRate < 30) {
            fitness = 60;
        } else if (bwStrikeRate >= 30 && bwStrikeRate < 32.5) {
            fitness = 55;
        } else if (bwStrikeRate >= 32.5 && bwStrikeRate < 35) {
            fitness = 50;
        } else if (bwStrikeRate >= 35 && bwStrikeRate < 37.5) {
            fitness = 45;
        } else if (bwStrikeRate >= 37.5 && bwStrikeRate < 40) {
            fitness = 40;
        } else if (bwStrikeRate >= 40 && bwStrikeRate < 42.5) {
            fitness = 35;
        } else if (bwStrikeRate >= 42.5 && bwStrikeRate < 45) {
            fitness = 30;
        } else if (bwStrikeRate >= 45 && bwStrikeRate < 47.5) {
            fitness = 25;
        } else if (bwStrikeRate >= 47.5 && bwStrikeRate < 50) {
            fitness = 20;
        } else if (bwStrikeRate >= 50) {
            fitness = 10;
        }
            double outOfHundre = ((fitness * weight / 100)/100)*33;
          return outOfHundre;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private static double  getWeightedFitnessForBowlingEconomy(double bwEcon, double weight){
        double fitness = 0;
        try{
          if (bwEcon > 0 && bwEcon < 2) {
            fitness = 100;
        } else if (bwEcon >= 2 && bwEcon < 4) {
            fitness = 87.5;
        } else if (bwEcon >= 4 && bwEcon < 6) {
            fitness = 75;
        } else if (bwEcon >= 6 && bwEcon < 8) {
            fitness = 62.5;
        } else if (bwEcon >= 8 && bwEcon < 10) {
            fitness = 50;
        } else if (bwEcon >= 10 && bwEcon < 12) {
            fitness = 37.5;
        } else if (bwEcon >= 12 && bwEcon < 14) {
            fitness = 25;
        } else if (bwEcon >= 14 && bwEcon < 16) {
            fitness = 12.5;
        } else if (bwEcon >= 16) {
            fitness = 0;
        }
          double outOfHundre = ((fitness * weight / 100)/100)*33;
          return outOfHundre;
        } catch (Exception ex){
            throw ex;
        }
    }
    
    private static double  getWeightedFitnessForBowlingAvg(double bwAvg, double weight){
        double fitness = 0;
        try{
        if (bwAvg > 0 && bwAvg < 5) {
            fitness = 100;
        } else if (bwAvg >= 5 && bwAvg < 10) {
            fitness = 90;
        } else if (bwAvg >= 10 && bwAvg < 15) {
            fitness = 80;
        } else if (bwAvg >= 15 && bwAvg < 20) {
            fitness = 70;
        } else if (bwAvg >= 20 && bwAvg < 25) {
            fitness = 60;
        } else if (bwAvg >= 25 && bwAvg < 30) {
            fitness = 50;
        } else if (bwAvg >= 30 && bwAvg < 35) {
            fitness = 40;
        } else if (bwAvg >= 35 && bwAvg < 40) {
            fitness = 30;
        } else if (bwAvg >= 40 && bwAvg < 50) {
            fitness = 20;
        } else if (bwAvg >= 50) {
            fitness = 10;
        }
         double outOfHundre = ((fitness * weight / 100)/100)*33;
          return outOfHundre;
        } catch (Exception ex){
            throw ex;
        }
    }
    
    private static double  getWeightedFitnessForCatches(double catches, double weight){
        double fitness = 0;
        try{
        if (catches > 0 && catches < 5) {
            fitness = 10;
        } else if (catches >= 5 && catches < 10) {
            fitness = 20;
        } else if (catches >= 10 && catches < 15) {
            fitness = 30;
        } else if (catches >= 15 && catches < 20) {
            fitness = 40;
        } else if (catches >= 20 && catches < 25) {
            fitness = 50;
        } else if (catches >= 25 && catches < 30) {
            fitness = 60;
        } else if (catches >= 30 && catches < 35) {
            fitness = 70;
        } else if (catches >= 35 && catches < 40) {
            fitness = 80;
        } else if (catches >= 40 && catches < 45) {
            fitness = 90;
        } else if (catches >= 45 && catches < 50) {
            fitness = 100;
        } else if (catches >= 50) {
            fitness = 10;
        }
       
         return ((fitness * weight / 100)/100)*50;
        } catch (Exception ex){
            throw ex;
        }
    }
    
    private static double  getWeightedFitnessForStumpings(double stumpings, double weight){
        double fitness = 0;
        try{
        if (stumpings > 0 && stumpings < 2) {
            fitness = 5;
        } else if (stumpings >= 2 && stumpings < 4) {
            fitness = 10;
        } else if (stumpings >= 4 && stumpings < 6) {
            fitness = 20;
        } else if (stumpings >= 6 && stumpings < 8) {
            fitness = 30;
        } else if (stumpings >= 8 && stumpings < 10) {
            fitness = 40;
        } else if (stumpings >= 10 && stumpings < 12) {
            fitness = 50;
        } else if (stumpings >= 12 && stumpings < 14) {
            fitness = 60;
        } else if (stumpings >= 14 && stumpings < 16) {
            fitness = 70;
        } else if (stumpings >= 16 && stumpings < 18) {
            fitness = 80;
        } else if (stumpings >= 18 && stumpings < 20) {
            fitness = 90;
        }   else if (stumpings >= 20 && stumpings < 22) {
            fitness = 95;
        } else if (stumpings >= 22) {
            fitness = 100;
        }
        return ((fitness * weight / 100)/100)*50;
        } catch (Exception ex){
            throw ex;
        }
    }
    
}
