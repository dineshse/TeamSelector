    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.utils;

import java.util.Random;
import org.jgap.RandomGenerator;

/**
 *
 * @author ZenitH
 */
public class RandomPlayerGenerator implements RandomGenerator {

    private Random randomNum = new Random();

    @Override
    public int nextInt() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int nextInt(int i) {

        //Random randomNum = new Random();
        int randomNumber = randomNum.nextInt(i);

        while (randomNumber == 0) {
            randomNumber = randomNum.nextInt(i);
        }
//        System.out.println("random value from generator "+randomNumber);
        return randomNumber;

    }

    @Override
    public long nextLong() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float nextFloat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean nextBoolean() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double nextDouble() {
        return 0;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
