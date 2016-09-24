/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zenith
 */
public class Preferences implements Serializable{
    
    public List<PlayerListItem> selectedPlayers;
    public List<String>  selectedYears;
    public  int  targetBudget;
    public Map<String, Integer> composition;
    public int maxEvaluations;
    public int populationSize;
    public boolean hasSelectedAll;
    
    public Preferences() {
        selectedPlayers = new ArrayList<>();
        selectedYears = new ArrayList<>();
        composition = new HashMap<>();
    }

    public Preferences(List<PlayerListItem> selectedPlayers, List<String> selectedYears, int targetBudget, Map<String, Integer> composition, int maxEvaluations, int populationSize) {
        this.selectedPlayers = selectedPlayers;
        this.selectedYears = selectedYears;
        this.targetBudget = targetBudget;
        this.composition = composition;
        this.maxEvaluations = maxEvaluations;
        this.populationSize = populationSize;
    }

    
}
