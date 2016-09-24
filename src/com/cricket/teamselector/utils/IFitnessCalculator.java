/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.utils;

import com.cricket.teamselector.entity.Player;
import java.util.List;

/**
 *
 * @author zenith
 */
//an approach for the strategy pattern
public interface IFitnessCalculator {
    public  double getTotalFitness();
    
}
