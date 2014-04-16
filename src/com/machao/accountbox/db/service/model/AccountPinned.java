package com.machao.accountbox.db.service.model;

public class AccountPinned {

	private String username;
	private String name;
	private String typeName;
	private int typeParentIdx;
	private int typeChildIdx;
	private int typeParentSelectedIndex;
	private int typeChildSelectedIndex;
	private String web;
	private String account;
	private String password;
	private String findbackway;
	private String gameServer;
	private Integer lock;
	private Integer imgIdx;
	private String bz;

	public static final int ITEM = 0;
	public static final int SECTION = 1;
	public int type;

	public int sectionPosition;
	public int listPosition;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getTypeParentIdx() {
		return typeParentIdx;
	}
	public void setTypeParentIdx(int typeParentIdx) {
		this.typeParentIdx = typeParentIdx;
	}
	public int getTypeChildIdx() {
		return typeChildIdx;
	}
	public void setTypeChildIdx(int typeChildIdx) {
		this.typeChildIdx = typeChildIdx;
	}
	public int getTypeParentSelectedIndex() {
		return typeParentSelectedIndex;
	}
	public void setTypeParentSelectedIndex(int typeParentSelectedIndex) {
		this.typeParentSelectedIndex = typeParentSelectedIndex;
	}
	public int getTypeChildSelectedIndex() {
		return typeChildSelectedIndex;
	}
	public void setTypeChildSelectedIndex(int typeChildSelectedIndex) {
		this.typeChildSelectedIndex = typeChildSelectedIndex;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFindbackway() {
		return findbackway;
	}
	public void setFindbackway(String findbackway) {
		this.findbackway = findbackway;
	}
	public String getGameServer() {
		return gameServer;
	}
	public void setGameServer(String gameServer) {
		this.gameServer = gameServer;
	}
	public Integer getLock() {
		return lock;
	}
	public void setLock(Integer lock) {
		this.lock = lock;
	}
	public Integer getImgIdx() {
		return imgIdx;
	}
	public void setImgIdx(Integer imgIdx) {
		this.imgIdx = imgIdx;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSectionPosition() {
		return sectionPosition;
	}
	public void setSectionPosition(int sectionPosition) {
		this.sectionPosition = sectionPosition;
	}
	public int getListPosition() {
		return listPosition;
	}
	public void setListPosition(int listPosition) {
		this.listPosition = listPosition;
	}
	public static int getItem() {
		return ITEM;
	}
	public static int getSection() {
		return SECTION;
	}
	@Override
	public String toString() {
		return "Account [username=" + username + ", name=" + name
				+ ", typeName=" + typeName + ", typeParentIdx=" + typeParentIdx
				+ ", typeChildIdx=" + typeChildIdx
				+ ", typeParentSelectedIndex=" + typeParentSelectedIndex
				+ ", typeChildSelectedIndex=" + typeChildSelectedIndex
				+ ", web=" + web + ", account=" + account + ", password="
				+ password + ", findbackway=" + findbackway + ", gameServer="
				+ gameServer + ", lock=" + lock + ", imgIdx=" + imgIdx
				+ ", bz=" + bz + ", type=" + type + ", sectionPosition="
				+ sectionPosition + ", listPosition=" + listPosition + "]";
	}

	
	
	
}
