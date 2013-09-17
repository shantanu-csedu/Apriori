/*
 * Copyright (C) 2013 shantanu saha <shantanucse18@gmail.com>
 *
 * You can distribute, modify this project. 
 * But you can't use it as academic project or assignment without a good amount of modification.
 */
package com.apriori.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import com.apriori.transaction.Candidate;
import com.apriori.transaction.TransactionTable;

public class Main {
	private final int supportCount = 3000;
	public static void main(String[] args) {
		
		new Main().Apriori();
	}
	
	public void Apriori(){
		
		TransactionTable ttable = new TransactionTable(supportCount);
		try {
			long startTime = new Date().getTime();
			/*generate transaction table from file */
			ttable.generateTable(new File("res/chess.dat"));
			// sort transaction table order by frequent item
			ttable.print();
			Candidate candidate = new Candidate(ttable,supportCount);
			System.out.println("\nItem Count : 1");
			candidate.init();
			candidate.print();
			int itemCount = 2;
			while( (candidate.genApriori(itemCount)) ){
				System.out.println("\nItem Count : "  + itemCount);
				candidate.print();
				itemCount++;
			}
			candidate.genApriori(2);
			
			// generate fp-tree structure
			// calculate frequent pattern 
			long endTime = new Date().getTime();
			System.out.println("Support count: " + supportCount);
			System.out.println("Execution time(including print): " + (double) ((endTime - startTime)/1000.00) + " secs");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
