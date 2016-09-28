package com.qingtian.model;
import java.util.ArrayList;

@ScanModel
public class Model {
	
	private String name;
	private String pkfield;
	private String title;
	private ArrayList<Field> fields;
	
	public String getPkfield() {
		return pkfield;
	}
	public void setPkfield(String pkfield) {
		this.pkfield = pkfield;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<Field> getFields() {
		return fields;
	}
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
