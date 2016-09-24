/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.utils;

import com.cricket.teamselector.gacomponents.PlayerBaseGene;
import com.cricket.teamselector.utils.Preferences;
import com.cricket.teamselector.utils.RandomPlayerGenerator;
import java.util.List;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.Population;

/**
 *
 * @author zenith
 */
public class TeamFeasibilityAdjuster {
       // gets the whole population and the preferences from file
       public static void adjustFeasibilityOfPopulation(Population pop, Preferences settings){
           List<IChromosome> teams = pop.getChromosomes();
           for(IChromosome team:teams){
               //Sends all the teams to check for dupliacte players
               replaceDuplicatePlayers(team, settings);
           }
       }
       public static void replaceDuplicatePlayers(IChromosome team, Preferences settings){
        // the gene arrays is populated for a chromosome   
        int noOfBowlers = settings.composition.get("bowlers");
        int noOfBatsmen = settings.composition.get("batsmen");
        int noOfWKeepers = settings.composition.get("wicketKeepers");
        int noOfARounders = settings.composition.get("allRounders");
        //gets the genes fromt he chromosome
        Gene[] genes = team.getGenes();
        int upperBound = noOfBatsmen -1; // used to track down the composition of the team 
        int lowerBound = 0; // used to track down the composition of the team 
        for (int i = 0; i < noOfBatsmen; i++) {
            // sends the comparing gene id , the set of genes along with the compositon of the team upper an lower bounds
            int duplicateIndex = getDuplicateGeneIndex(i,genes,lowerBound,upperBound); //if this returns -1 no duplicate
            if(duplicateIndex!=-1){
                //replacing duplicate with random value
                 genes[duplicateIndex].setToRandomValue(new RandomPlayerGenerator());
                 //iterate till the getDuplicateGeneIndex returns a value !=-1
                 while((duplicateIndex = getDuplicateGeneIndex(i,genes,lowerBound,upperBound))!= -1){
                     //set the new value 
                    genes[duplicateIndex].setToRandomValue(new RandomPlayerGenerator());// loops till a random value is generated
                }
            }
           
        }
        upperBound +=noOfBowlers;
        lowerBound +=noOfBatsmen;
        for (int i = noOfBatsmen; i < noOfBatsmen +noOfBowlers; i++){
            
            int duplicateIndex = getDuplicateGeneIndex(i,genes,lowerBound,upperBound);
            if(duplicateIndex!=-1){
                 genes[duplicateIndex].setToRandomValue(new RandomPlayerGenerator());
                 while((duplicateIndex = getDuplicateGeneIndex(i,genes,lowerBound,upperBound))!= -1){
                    genes[duplicateIndex].setToRandomValue(new RandomPlayerGenerator());// loops till a random value is generated
                }
            }
        }
        upperBound +=noOfWKeepers;
        lowerBound +=noOfBowlers;
        for (int i = noOfBatsmen +noOfBowlers; i < noOfBatsmen +noOfBowlers+noOfWKeepers; i++) {
           
            int duplicateIndex = getDuplicateGeneIndex(i,genes,lowerBound,upperBound);
            if(duplicateIndex!=-1){
                 genes[duplicateIndex].setToRandomValue(new RandomPlayerGenerator());
                 while((duplicateIndex = getDuplicateGeneIndex(i,genes,lowerBound,upperBound))!= -1){
                    genes[duplicateIndex].setToRandomValue(new RandomPlayerGenerator());// loops till a random value is generated
                }
            }
        }
        upperBound +=noOfARounders;
        lowerBound +=noOfWKeepers;
        for (int i = noOfBatsmen +noOfBowlers+noOfWKeepers; i < noOfBatsmen +noOfBowlers+noOfWKeepers+noOfARounders; i++) {
            
            int duplicateIndex = getDuplicateGeneIndex(i,genes,lowerBound,upperBound);
            if(duplicateIndex!=-1){
                 genes[duplicateIndex].setToRandomValue(new RandomPlayerGenerator());
                 while((duplicateIndex = getDuplicateGeneIndex(i,genes,lowerBound,upperBound))!= -1){
                    genes[duplicateIndex].setToRandomValue(new RandomPlayerGenerator()); // loops till a random value is generated
                }                                                                        // that is not in the set   
            }
        }
    }
    public static int getDuplicateGeneIndex(int source, Gene[] genes, int lowerBound, int upperBound){
        int ss =lowerBound;
        for(int i=lowerBound; i<=upperBound ;i++){
            if(i==source ) // cheks if the sent id is the first of the respective compostion
                break;  // it is the first id hence no need to check for the others
            // There is a duplicate for source gene
            if(((PlayerBaseGene)genes[i]).equalsCustom((PlayerBaseGene)genes[source])){
                return i;
            }
        }
        return -1;
    }
    
}
