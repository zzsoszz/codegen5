package com.qingtian.model;
import java.util.ArrayList;

@ScanModel
public class Model {
	private String title;
	private ArrayList<Field> fields;
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
}
