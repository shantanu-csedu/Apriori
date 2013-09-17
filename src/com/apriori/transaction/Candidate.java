/*
 * Copyright (C) 2013 shantanu saha <shantanucse18@gmail.com>
 *
 * You can distribute, modify this project. 
 * But you can't use it as academic project or assignment without a good amount of modification.
 */
package com.apriori.transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import com.apriori.data.CandidateItem;

public class Candidate {
	private List<CandidateItem> list;
	private TransactionTable table;
	private int curItemCount;
	private int supportCount;
	
	public int getCurItemCount() {
		return curItemCount;
	}

	public Candidate(TransactionTable table,int supportCount) {
		list = new ArrayList<CandidateItem>();
		this.table = table;
		this.supportCount = supportCount;
	}
	
	public boolean init(){ // generate 1 level candidate
		curItemCount = 1;
		Hashtable<Integer, Integer> hashTable = new Hashtable<Integer,Integer>();
		for(int i=0;i<table.getCount(); i++){
			TransactionItem transactionItem = table.getItem(i);
			for(int j=0;j<transactionItem.getCount();j++){
				int item = transactionItem.getItem(j);
				if(hashTable.containsKey(item)){
					hashTable.put(item, hashTable.get(item)+1); 
				}
				else{
					hashTable.put(item,1);
				}
			}
		}
		Integer[] keys = (Integer[]) hashTable.keySet().toArray(new Integer[0]);
		Arrays.sort(keys);
		for(Integer key : keys){
			if(hashTable.get(key) >= supportCount){
				TransactionItem tItem = new TransactionItem();
				tItem.add(key);
				list.add(new CandidateItem(tItem, hashTable.get(key)));
			}
		}
		if(list.size() >0) return true;
		return false;
	}
	
	public boolean genApriori(int itemCount){
		if(itemCount == 1){
			return init();
		}
		curItemCount = itemCount;
		List<CandidateItem> tmp = new ArrayList<CandidateItem>();
		for(int i=0;i<list.size();i++){
			for(int j=i+1; j< list.size();j++){
				CandidateItem mergeItem = merge(list.get(i), list.get(j) , itemCount);
				if(mergeItem !=null){
					tmp.add(mergeItem);
				}
			}
		}
		if(itemCount > 2)
			pruning(tmp);
		calculateSupportCount(tmp);
		list = tmp; // replacing with newly generated
		if(list.size() == 0) return false;
		return true;
	}



	private void pruning(List<CandidateItem> tmp) {
		for(int i=0;i<tmp.size();i++){
			CandidateItem candidateItem = tmp.get(i);
			for(int j=0;j<candidateItem.getItem().getCount();j++){
				TransactionItem item = new TransactionItem(candidateItem.getItem());
				item.remove(j);
				boolean flag = false;
				for(int k=0;k<list.size();k++){
					if(list.get(k).getItem().isEqual(item)){
						flag = true;
						break;
					}
				}
				if(!flag){ // not available in list. so not frequent
					tmp.remove(i);
					i--;
					break;
				}
			}
		}
	}

	private void calculateSupportCount(List<CandidateItem> tmp) {
		for(int i=0;i<tmp.size();i++){
			CandidateItem cItem = tmp.get(i);
			for(int j=0;j<table.getCount();j++){
				TransactionItem transactionItem = table.getItem(j);
				if( transactionItem.isSubset(cItem.getItem()) ){
					cItem.supportCount ++;
				}
			}
			tmp.remove(i);
			if(cItem.supportCount >= supportCount)
				tmp.add(i, cItem);
			else{
				i--;
			}
		}
		
	}

	private CandidateItem merge(CandidateItem candidateItem,
			CandidateItem candidateItem2, int itemCount) {
		if(itemCount == 2){
			TransactionItem tmpItem = new TransactionItem();
			tmpItem.add(candidateItem.getItem().getItem(0));
			tmpItem.add(candidateItem2.getItem().getItem(0));
			return new CandidateItem(tmpItem, 0);
		}
		else if(itemCount > 2){
			TransactionItem result = null;
			TransactionItem item1 = candidateItem.getItem().getSuffix(itemCount-2); 
			TransactionItem item2 = candidateItem2.getItem().getPrefix(itemCount-2);
			if(item1.isEqual(item2)){
				result = new TransactionItem(candidateItem.getItem());
				result.merge(candidateItem2.getItem(),itemCount-2);
				return new CandidateItem(result, 0);
			}
			return null;
		}
		return null;
	}
	
	public void print(){
		for(int i=0; i<list.size() ; i++){
			System.out.print("{ ");
			list.get(i).getItem().print();
			System.out.println(" } : " + list.get(i).getSupportCount());
		}
	}
}
