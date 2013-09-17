/*
 * Copyright (C) 2013 shantanu saha <shantanucse18@gmail.com>
 *
 * You can distribute, modify this project. 
 * But you can't use it as academic project or assignment without a good amount of modification.
 */
package com.apriori.data;


import com.apriori.transaction.TransactionItem;

public class CandidateItem {
	
	private TransactionItem item;
	public int supportCount;
	
	public CandidateItem(TransactionItem item, int supportCount) {
		this.item = item;
		this.supportCount = supportCount;
	}
	
	public TransactionItem getItem() {
		return item;
	}
	public void setItem(TransactionItem item) {
		this.item = item;
	}
	public int getSupportCount() {
		return supportCount;
	}
	public void setSupportCount(int supportCount) {
		this.supportCount = supportCount;
	}
}
