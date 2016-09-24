/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.app;

import java.util.List;

/**
 *
 * @author charli
 */
public class EvalFitness {
     private List<Gens> list;
    private float fitness;

    public List<Gens> getList() {
        return list;
    }

    public void setList(List<Gens> list) {
        this.list = list;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }
    
}
