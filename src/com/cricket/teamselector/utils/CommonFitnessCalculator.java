/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.utils;

import com.cricket.teamselector.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author ZenitH
 */
public class CommonFitnessCalculator {
    private IFitnessCalculator ifitnessCalculator;
    
    public double calculateFitness() {
        try {
            double playerFitnessByStats =  ifitnessCalculator.getTotalFitness();
            return playerFitnessByStats;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    


    public IFitnessCalculator getIfitnessCalculator() {
        return ifitnessCalculator;
    }

    public void setIfitnessCalculator(IFitnessCalculator ifitnessCalculator) {
        this.ifitnessCalculator = ifitnessCalculator;
    }
}