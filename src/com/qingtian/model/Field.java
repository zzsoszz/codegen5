package com.qingtian.model;

public class Field {
	private String name;
	private String title;
	private String type;
	private String typedata;
	private String parent;
	private boolean addEnable;
	private boolean searchEnable;
	private boolean editEnable;
	private boolean detailEnable;
	private boolean listEnable;
	private boolean require;
	public Field(){
		
	}
	public Field(String name,String title,String type,String typedata,String parent,boolean addEnable,boolean searchEnable,boolean editEnable,boolean detailEnable,boolean listEnable,boolean require){
		this.name=name;
		this.title=title;
		this.type=type;
		this.typedata=typedata;
		this.parent=parent;
		this.addEnable=addEnable;
		this.searchEnable=searchEnable;
		this.editEnable=editEnable;
		this.detailEnable=detailEnable;
		this.listEnable=listEnable;
		this.require=require;
//		this.typedata=typedata==null?"":typedata;
//		this.parent=parent==null?"":parent;
	}
	
	public String getTypedata() {
		return typedata;
	}

	public void setTypedata(String typedata) {
		this.typedata = typedata;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	public boolean isRequire() {
		return require;
	}

	public void setRequire(boolean require) {
		this.require = require;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isAddEnable() {
		return addEnable;
	}
	public void setAddEnable(boolean addEnable) {
		this.addEnable = addEnable;
	}
	public boolean isSearchEnable() {
		return searchEnable;
	}
	public void setSearchEnable(boolean searchEnable) {
		this.searchEnable = searchEnable;
	}
	public boolean isEditEnable() {
		return editEnable;
	}
	public void setEditEnable(boolean editEnable) {
		this.editEnable = editEnable;
	}
	public boolean isDetailEnable() {
		return detailEnable;
	}
	public void setDetailEnable(boolean detailEnable) {
		this.detailEnable = detailEnable;
	}
	public boolean isListEnable() {
		return listEnable;
	}
	public void setListEnable(boolean listEnable) {
		this.listEnable = listEnable;
	}
	
}
