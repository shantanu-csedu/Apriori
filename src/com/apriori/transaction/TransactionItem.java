/*
 * Copyright (C) 2013 shantanu saha <shantanucse18@gmail.com>
 *
 * You can distribute, modify this project. 
 * But you can't use it as academic project or assignment without a good amount of modification.
 */
package com.apriori.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class TransactionItem {
	private List<Integer> transactionItem;
	
	
	public TransactionItem(){
		transactionItem = new ArrayList<Integer>();
	}
	
	public TransactionItem(List<Integer> item) {
		this.transactionItem = item;
	}
	
	public TransactionItem(String items){ // seperate by space.1 2 3 ..etc
		transactionItem = new ArrayList<Integer>();
		String item[] = items.split(" ");
		for(int i=0;i<item.length;i++){
			try{
				transactionItem.add(Integer.parseInt(item[i].trim()));
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		Collections.sort(transactionItem);
	}
	
	public TransactionItem(TransactionItem item) {
		this.transactionItem = new ArrayList<Integer>(item.transactionItem);
	}

	public void add(int val){
		transactionItem.add(val);
	}
	
	public void remove(int index){
		transactionItem.remove(index);
	}
	
	public int getCount(){
		return transactionItem.size();
	}
	
	public Integer getItem(int pos){
		return transactionItem.get(pos);
	}
	
	public void print(){
		for(int i=0;i<transactionItem.size();i++){
			if(i>0) System.out.print(" ");
			System.out.print(transactionItem.get(i));
		}
	}

	public TransactionItem getSuffix(int position) {
		TransactionItem result = new TransactionItem();
		for(int i=position;i<transactionItem.size();i++){
			result.add(transactionItem.get(i));
		}
		return result;
	}

	public TransactionItem getPrefix(int position) {
		TransactionItem result = new TransactionItem();
		for(int i=0;i<transactionItem.size()-position;i++){
			result.add(transactionItem.get(i));
		}
		return result;
	}

	public boolean isEqual(TransactionItem item2) {
		if(transactionItem.size() != item2.getCount())
			return false;
		else{
			for(int i=0;i<transactionItem.size();i++){
				if(transactionItem.get(i) !=  item2.getItem(i)){
					return false;
				}
			}
		}
		return true;
	}

	public TransactionItem merge(TransactionItem item2, int similarItem) {
		TransactionItem result = this;
		for(int i=similarItem;i<item2.getCount();i++){
			result.add(item2.getItem(i));
		}
		return result;
	}

	public boolean isSubset(TransactionItem item) { //assume sorted
		
		int i=0,j=0;
		do{
			if(transactionItem.get(i) == item.getItem(j)){
				i++;
				j++;
			}
			else{
				i++;
			}
		}while(i<transactionItem.size() && j<item.getCount());
		if(j != item.getCount()){
			return false;
		}
		return true;
	}
}
