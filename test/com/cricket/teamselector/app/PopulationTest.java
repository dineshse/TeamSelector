/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.app;

import com.cricket.teamselector.data.Statistics;
import com.cricket.teamselector.data.StatisticsProvider;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author charli
 */
public class PopulationTest {
    
    public PopulationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPlayers method, of class Population.
     */
    @Test
    public void testGetPlayers() {
        System.out.println("getPlayers");
        Statistics stat = new StatisticsProvider();
        Population instance = new Population(0,1000000,5,4, 1, 1, 1);
        List<Gens> expResult = null;
        List<Gens> result = instance.population;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of calculateFitness method, of class Population.
     */
    @Test
    public void testCalculateFitness() {
        System.out.println("calculateFitness");
        Population instance =new Population(0,1000000,5,4, 1, 1, 1);
        instance.calculateFitness();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of calculateCrossover method, of class Population.
     */
    @Test
    public void testCalculateCrossover() {
        System.out.println("calculateCrossover");
        Population instance = new Population(0,1000000,5,4, 1, 1, 1);
        List<Gens> expResult = null;
        List<Gens> result = instance.calculateCrossover();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of totalAmount method, of class Population.
     */
    @Test
    public void testTotalAmount() {
        System.out.println("totalAmount");
        List<Gens> list = null;
        Population instance = new Population(0,1000000,5,4, 1, 1, 1);
        int expResult = 0;
        int result = instance.totalAmount(list);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of totalFitness method, of class Population.
     */
//    @Test
//    public void testTotalFitness() {
//        System.out.println("totalFitness");
//        List<Gens> list = null;
//        Population instance = new Population(0,1000000,5,4, 1, 1, 1);
//        int expResult = 0;
////        int result = instance.totalFitness(list);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        
//    }

    /**
     * Test of genarateTeam method, of class Population.
     */
    @Test
    public void testGenarateTeam() {
        System.out.println("genarateTeam");
        Population instance = new Population(0,1000000,5,4, 1, 1, 1);
        List<Gens> expResult = null;
        List<Gens> result = instance.genarateTeam();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of testConnection method, of class Population.
     */
    @Test
    public void testTestConnection() {
        System.out.println("testConnection");
        Population instance = new Population(0,1000000,5,4, 1, 1, 1);
        instance.testConnection();
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
