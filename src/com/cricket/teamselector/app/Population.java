/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.app;

import com.cricket.teamselector.data.DBConnection;
import com.cricket.teamselector.data.Statistics;
import com.cricket.teamselector.data.StatisticsProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author charli
 */
public class Population {

    List<Gens> population;
    int popSize;
    long budget;
    long batsmen;
    long bowlers;
    long wkerpers;
    long allrounders;
    int eveluation;

    public Population(int size, long budget, long batsmen, long bowlers, long wkerpers, long allrounders, int eveluation) {
        Statistics stat = new StatisticsProvider();
        this.popSize = size;
        this.budget = budget;
        this.batsmen = batsmen;
        this.bowlers = bowlers;
        this.wkerpers = wkerpers;
        this.allrounders = allrounders;
        this.eveluation = eveluation;
        this.population = stat.getPlayers(this.budget);
    }

//    fitness value calcutation
    public void calculateFitnessbutget() {
        float max = 0;
        DecimalFormat numberFormat = new DecimalFormat("#");
        for (int i = 0; i < this.population.size(); i++) {
            this.population.get(i).fitness = this.budget - this.population.get(i).amount;
        }

        for (int i = 0; i < this.population.size(); i++) {
            if (max < this.population.get(i).fitness) {
                max = this.population.get(i).fitness;
            }
        }
        System.out.println("max " + max);
        for (int i = 0; i < this.population.size(); i++) {
            this.population.get(i).fitness = Long.parseLong(numberFormat.format((((float) this.population.get(i).fitness) / max) * 100));
        }

    }

//    one player fitness value calculate
    public void calculateFitness() {
        for (int i = 0; i < this.population.size(); i++) {
            switch (this.population.get(i).role) {
                case "Batsman":
                    this.population.get(i).fitness = this.population.get(i).batstrictRate;
                    break;
                case "Bowler":
                    this.population.get(i).fitness = this.population.get(i).ballstrictRate;
                    break;
                case "Wicketkeeper":
                    this.population.get(i).fitness = this.population.get(i).batstrictRate;
                    break;
                case "Allrounder":
                    this.population.get(i).fitness = (this.population.get(i).batavg > this.population.get(i).ballavg)
                            ? this.population.get(i).batavg : this.population.get(i).ballavg;

                    break;
            }
            System.out.println("fitness " + this.population.get(i).fitness);

        }


    }

//    calculate crossover and optimized using batting rate
    public List<Gens> calculateCrossover() {
        List<Gens> list = new ArrayList();
        List<Gens> list1 = new ArrayList();

        int valid = 0;
        List<Gens> finalTeam = new ArrayList();
        while (valid != this.eveluation) {
            List<Gens> ball = new ArrayList();
            List<Gens> bat = new ArrayList();
            List<Gens> wkeep = new ArrayList();
            List<Gens> allround = new ArrayList();

            list = (list.isEmpty()) ? genarateTeam() : list;
            list1 = genarateTeam();
            EvalGens eval = new EvalGens(totalFitness(list, "Bowler"), totalFitness(list, "Batsman"), totalFitness(list, "Wicketkeeper"), totalFitness(list, "Allrounder"));
            EvalGens eval1 = new EvalGens(totalFitness(list1, "Bowler"), totalFitness(list1, "Batsman"), totalFitness(list1, "Wicketkeeper"), totalFitness(list1, "Allrounder"));
            if (eval.getBallFit() > eval1.getBallFit()) {
                ball = getSeparatedList(list, "Bowler");
            } else {
                ball = getSeparatedList(list1, "Bowler");
            }
            if (eval.getBatFit() > eval1.getBatFit()) {
                bat = getSeparatedList(list, "Batsman");
            } else {
                bat = getSeparatedList(list1, "Batsman");
            }

            if (eval.getKeepFit() > eval1.getKeepFit()) {
                wkeep = getSeparatedList(list, "Wicketkeeper");
            } else {
                wkeep = getSeparatedList(list1, "Wicketkeeper");
            }

            if (eval.getAllroundFit() > eval1.getAllroundFit()) {
                allround = getSeparatedList(list, "Allrounder");
            } else {
                allround = getSeparatedList(list1, "Allrounder");
            }
            list.clear();
            list = getMergList(ball, bat, wkeep, allround);


            if (!checkMutant(list)&&(totalAmount(list)<=this.budget)) {
                valid++;
            }else{
                list.clear();
            }

        }
        return list;

    }

    public int totalAmount(List<Gens> list) {
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i).amount;
        }
        return total;
    }

//    total fitness value
    public float totalFitness(List<Gens> list, String role) {
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).role.equalsIgnoreCase(role)) {
                total += list.get(i).fitness;
            }
        }
        return total;
    }
//    public float preFitness(List<Gens> list,String role) {
//        int total = 0;
//        float max = new StatisticsProvider().getMax("Batsman");
//        for (int i = 0; i < list.size(); i++) {
//
//            if (list.get(i).role.equalsIgnoreCase(role)) {
//                float temp =  (float) ((list.get(i).fitness/max)*100.0);
//                total += temp;
//            }
//
//        }
//        return total;
//    }
    
//    percentage of fitness value according to maxmum strict rate
    public float actualFitness(List<Gens> list, String role) {
        float actotal = 0;
        
        float max = new StatisticsProvider().getMax(role);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).role.equalsIgnoreCase(role)) {
                float temp =  (float) ((list.get(i).fitness/max)*100.0);
                actotal += temp;
            }
        }
        
        return actotal;
    }

    public List<Gens> genarateTeam() {

        List<Gens> team = new ArrayList();
//        List<Gens> batting = new ArrayList();
//        List<Gens> keeper = new ArrayList();
        int batcount = 0;
        int ballcount = 0;
        int wkeeper = 0;
        int allround = 0;

        Random r = new Random();
        int Low = 0;
        int High = this.population.size();
        int index = 0;
        int total = 0;
        while (true) {
            index = r.nextInt(High - Low) + Low;
            if (this.population.get(index).role.equalsIgnoreCase("Bowler") && ballcount != this.bowlers) {
                team.add(this.population.get(index));
                ballcount++;
            } else if (this.population.get(index).role.equalsIgnoreCase("Allrounder") && allround != this.allrounders) {
                team.add(this.population.get(index));
                allround++;
            } else if (this.population.get(index).role.equalsIgnoreCase("Batsman") && batcount != this.batsmen) {
                team.add(this.population.get(index));
                batcount++;
            } else if (this.population.get(index).role.equalsIgnoreCase("Wicketkeeper") && wkeeper != this.wkerpers) {
                team.add(this.population.get(index));
                wkeeper++;
            }
            total = ballcount + allround + batcount + wkeeper;
            if (total == 11) {
                break;
            }
        }
        int totalbud = totalAmount(team);
        
        if (totalbud > this.budget) {
            genarateTeam();
        }

        return team;
    }

    public void testConnection() {
        try {
            DBConnection ob = new DBConnection();
            Connection con = ob.createConnection();
            Statement stmt = con.createStatement();
            String query = "select * from player where SoldValue < " + 200000;
            String query1 = "select count(*) as total from player where SoldValue < " + 200000;
//            ResultSet rs = stmt.executeQuery(query);
            ResultSet rs = stmt.executeQuery(query1);
            if (rs.next()) {
                System.out.println("total " + rs.getString("total"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Gens> getSeparatedList(List<Gens> list, String role) {
        List<Gens> finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).role.equalsIgnoreCase(role)) {
                finalList.add(list.get(i));
            }
        }

        return finalList;
    }

    public List<Gens> getMergList(List<Gens> list1, List<Gens> list2, List<Gens> list3, List<Gens> list4) {
        List<Gens> finalList = new ArrayList();
        for (Gens g : list1) {
            finalList.add(g);
        }
        for (Gens g : list2) {
            finalList.add(g);
        }
        for (Gens g : list3) {
            finalList.add(g);
        }
        for (Gens g : list4) {
            finalList.add(g);
        }
        return finalList;
    }

    

    public boolean checkMutant(List<Gens> list) {
        List<String> check = new ArrayList();
        boolean ok = false;
        for (Gens g : list) {
            check.add(g.name);
        }

        for (String name : check) {
            ok = (Collections.frequency(check, name) > 1);
            if (ok) {
                break;
            }
        }
        return ok;
    }

}
