package com.apriori.data;


import com.apriori.transaction.TransactionItem;

public class CandidateItem {
	public CandidateItem(TransactionItem item, int supportCount) {
		super();
		this.item = item;
		this.supportCount = supportCount;
	}
	private TransactionItem item;
	public int supportCount;
	
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
