package com.machao.accountbox.db.service.model;

import java.util.ArrayList;
import java.util.List;

public class AccountGroup {

	private String groupName;
	private int groupImgIdx;
	private List<Account> accounts = new ArrayList<Account>();
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupImgIdx() {
		return groupImgIdx;
	}
	public void setGroupImgIdx(int groupImgIdx) {
		this.groupImgIdx = groupImgIdx;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	@Override
	public String toString() {
		return "AccountGroup [groupName=" + groupName + ", groupImgIdx="
				+ groupImgIdx + ", accounts=" + accounts + "]";
	}
	
	
	
}
