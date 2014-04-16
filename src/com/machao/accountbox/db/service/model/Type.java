package com.machao.accountbox.db.service.model;

public class Type {

	private Integer idx;
	private String name;
	private Integer parentIdx = 0;
	private Integer imgId;
	
	public Type() {};
	
	
	public Type(String name, Integer parentIdx, Integer imgId) {
		super();
		this.name = name;
		this.parentIdx = parentIdx;
		this.imgId = imgId;
	}


	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	public Integer getParentIdx() {
		return parentIdx;
	}
	public void setParentIdx(Integer parentIdx) {
		this.parentIdx = parentIdx;
	}
	@Override
	public String toString() {
		return "Type [idx=" + idx + ", name=" + name + ", parentIdx="
				+ parentIdx + ", imgId=" + imgId + "]";
	}
	
	
}
