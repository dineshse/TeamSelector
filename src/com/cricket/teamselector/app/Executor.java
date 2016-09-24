/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.app;

import com.cricket.teamselector.utils.TeamFeasibilityAdjuster;
import com.cricket.teamselector.data.StatisticsProvider;
import com.cricket.teamselector.entity.Player;
import com.cricket.teamselector.gacomponents.PlayerBaseGene;
import com.cricket.teamselector.gacomponents.TeamFitnessCalculator;
import com.cricket.teamselector.utils.PlayerListItem;
import com.cricket.teamselector.utils.Preferences;
import com.cricket.teamselector.utils.RandomPlayerGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.GeneticOperator;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.data.DataTreeBuilder;
import org.jgap.data.IDataCreators;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.MutationOperator;
import org.jgap.xml.XMLDocumentBuilder;
import org.jgap.xml.XMLManager;
import org.w3c.dom.Document;

/**
 *
 * @author ZenitH
 */
public class Executor extends SwingWorker<IChromosome, IChromosome>{

    private static Preferences settings;
    private Main parentUI;
    private Genotype population;
    private int currentProgress;
    public static Preferences getsettings(){
        return settings;
    }
    public Executor(Preferences settings,Main parentUI ) {
        this.settings = settings;
        this.parentUI = parentUI;
    }
    
    private List<String> getIDList(List<PlayerListItem> players){
        List<String> lst = new ArrayList<>();
        for(PlayerListItem p:players){
            lst.add(p.getId());
        }
        return lst;
    }
    public void teamEvolution() throws Exception {
        //gets the compostion from the UI /file
        int noOfBowlers = settings.composition.get("bowlers");
        int noOfBatsmen = settings.composition.get("batsmen");
        int noOfWKeepers = settings.composition.get("wicketKeepers");
        int noOfARounders = settings.composition.get("allRounders");
        
        StatisticsProvider playerDbObject = new StatisticsProvider();   
        setProgress(3);
        playerDbObject.setTemporaryPlayerRecords(getIDList(settings.selectedPlayers), settings.selectedYears, "IPL");
        setProgress(20);
        currentProgress =20;
        // Start with a DefaultConfiguration, which comes setup with the
        // most common settings.
        // -------------------------------------------------------------
        Configuration conf = new DefaultConfiguration();
        // Care that the fittest individual of the current population is
        // always taken to the next generation.
        // Consider: With that, the pop. size may exceed its original
        conf.setPreservFittestIndividual(true);
        
//        
//         ArrayList<GeneticOperator> arr= new ArrayList<GeneticOperator>(conf.getGeneticOperators());
//            for (int ijk=0;ijk<arr.size();ijk++){
////                arr.get(ijk).toString().concat(null)
//                if (arr.get(ijk).toString().contains(".MutationOperator@")){
//                    System.err.println("mutation rate was set");
//                    ((MutationOperator)arr.get(ijk)).setMutationRate(2);
//                }
             
                 
        
        // Set the fitness function we want to use, which is our
        // MinimizingMakeChangeFitnessFunction. We construct it with
        // the target amount of change passed in to this method.
        // ---------------------------------------------------------
        FitnessFunction myFunc =
                new TeamFitnessCalculator();
        conf.setFitnessFunction(myFunc);
        conf.setRandomGenerator(new RandomPlayerGenerator());
        
        Gene[] playerGenes = new Gene[noOfBatsmen+noOfBowlers+noOfWKeepers+noOfARounders];

        for (int i = 0; i < noOfBatsmen; i++) {
            PlayerBaseGene player = new PlayerBaseGene(conf, "batsman" );
            player.setToRandomValue(conf.getRandomGenerator());
            playerGenes[i] = player;             
        }
        for (int i = noOfBatsmen; i < noOfBatsmen +noOfBowlers; i++){
            PlayerBaseGene player = new PlayerBaseGene(conf, "bowler" );
            player.setToRandomValue(conf.getRandomGenerator());
            playerGenes[i] = player; 
        }
        
        for (int i = noOfBatsmen +noOfBowlers; i < noOfBatsmen +noOfBowlers+noOfWKeepers; i++) {
            PlayerBaseGene player = new PlayerBaseGene(conf, "wicketkeeper" );
            player.setToRandomValue(conf.getRandomGenerator());
            playerGenes[i] = player; 
            
        }
        for (int i = noOfBatsmen +noOfBowlers+noOfWKeepers; i < noOfBatsmen +noOfBowlers+noOfWKeepers+noOfARounders; i++) {
           PlayerBaseGene player = new PlayerBaseGene(conf, "Allrounder" );
           player.setToRandomValue(conf.getRandomGenerator());
           playerGenes[i] = player; 
            
        }
        
        
        IChromosome sampleChromosome = new Chromosome(conf, playerGenes);
        
        conf.setSampleChromosome(sampleChromosome);

        // Finally, we need to tell the Configuration object how many
        // Chromosomes we want in our population. The more Chromosomes,
        // the larger number of potential solutions (which is good for
        // finding the answer), but the longer it will take to evolve
        // the population (which could be seen as bad).
        // ------------------------------------------------------------
        conf.setPopulationSize(settings.populationSize);
        //setting the random generator
        conf.setRandomGenerator(new RandomPlayerGenerator());

        // Create random initial population of Chromosomes.
        // Here we try to read in a previous run via XMLManager.readFile(..)
        // for demonstration purpose only!
        // -----------------------------------------------------------------
        
        try {
            Document doc = XMLManager.readFile(new File("JGAPExample32.xml"));
            population = XMLManager.getGenotypeFromDocument(conf, doc);
        } catch (UnsupportedRepresentationException uex) {
            // JGAP codebase might have changed between two consecutive runs.
            // --------------------------------------------------------------
            population = Genotype.randomInitialGenotype(conf);
        } catch (FileNotFoundException fex) {
            population = Genotype.randomInitialGenotype(conf);
        }

        // Now we initialize the population randomly, anyway (as an example only)!
        // If you want to load previous results from file, remove the next line!
        // -----------------------------------------------------------------------
        population = Genotype.randomInitialGenotype(conf);
        // Evolve the population. Since we don't know what the best answer
        // is going to be, we just evolve the max number of times.
        // ---------------------------------------------------------------
        for (int i = 0; i < settings.maxEvaluations; i++) {
            if (!uniqueChromosomes(population.getPopulation())) {
                throw new RuntimeException("Invalid state in generation " + i);
            }
            population.evolve();
            System.err.println("The best teams population at evolution no : "+i+" is :"+population.getFittestChromosome().getFitnessValue() );
            //Repairs the infeasible chromosomes
            TeamFeasibilityAdjuster.adjustFeasibilityOfPopulation(population.getPopulation(), settings);
            
            currentProgress += (80/settings.maxEvaluations);
            setProgress(currentProgress);
        }

        // Represent Genotype as tree with elements Chromomes and Genes.
        // -------------------------------------------------------------
        DataTreeBuilder builder = DataTreeBuilder.getInstance();
        IDataCreators doc2 = builder.representGenotypeAsDocument(population);
        // create XML document from generated tree
        XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
        Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
        XMLManager.writeFile(xmlDoc, new File("JGAPExample26.xml"));
        
    
                

    /**
     * @param a_pop the population to verify
     * @return true if all chromosomes in the populationa are unique
     *
     * @author ZenitH
     * @since 3.3.1
     */
            
    }
    public static boolean uniqueChromosomes(Population a_pop) {
        // Check that all chromosomes are unique
        for (int i = 0; i < a_pop.size() - 1; i++) {
            IChromosome c = a_pop.getChromosome(i);
            for (int j = i + 1; j < a_pop.size(); j++) {
                IChromosome c2 = a_pop.getChromosome(j);
                if (c == c2) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected IChromosome doInBackground() throws Exception {
        long startTime = System.currentTimeMillis();
        teamEvolution();
                   
                    long endTime = System.currentTimeMillis();
        System.err.println("Do background time: " + (endTime - startTime)
                + " ms");
        return population.getFittestChromosome();
    }

//    @Override
//    protected void process(List<IChromosome> chunks) {
//         parentUI.updateSolutionTeamPlayerList(
//                        getPlayerListFromCromosone(chunks.get(0))
//                    );
//    }

//    @Override
//    protected void done() {
//        super.done(); 
//        try {
////            parentUI.updateSolutionTeamPlayerList(
////                        getPlayerListFromCromosone(get()), get().getFitnessValue(), getTeamBudget(get()));
//                    
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Executor.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ExecutionException ex) {
//            Logger.getLogger(Executor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    protected int getTeamBudget(IChromosome team){
        int budget = 0;
   
        for(Object obj:team.getGenes()){
            PlayerBaseGene pg = (PlayerBaseGene)obj;
            Player p = (Player)pg.getAllele();
            budget+=p.getBaseValue();
        }
        return budget;
    }
    protected List<PlayerListItem> getPlayerListFromCromosone(IChromosome team){
        
        Object[] arr = team.getGenes();
        List<PlayerListItem> lst = new ArrayList<>();
        
        for(Object obj:arr){
            PlayerBaseGene pg = (PlayerBaseGene)obj;
            Player p = (Player)pg.getAllele();
            lst.add(new PlayerListItem(p.getPlayerID()+"",p.getName()));
        }
        return lst;
    }
    

}
