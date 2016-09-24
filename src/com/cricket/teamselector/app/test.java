/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author charli
 */
public class test {
    public static void main (String [] args){
       List<String> list = new ArrayList<String>();
	list.add("a");
	list.add("b");
	list.add("c");
	list.add("d");
	list.add("b");
	list.add("c");
	list.add("a");
	list.add("a");
	list.add("a");

	System.out.println("\nExample 1 - Count 'a' with frequency");
	System.out.println("a : " + Collections.frequency(list, "a"));

	System.out.println("\nExample 2 - Count all with frequency");
	Set<String> uniqueSet = new HashSet<String>(list);
	for (String temp : uniqueSet) {
		System.out.println(temp + ": " + Collections.frequency(list, "n"));
	}
    }
          
    
}
