package com.example.saladdresser;

import java.util.ArrayList;

//Represents a Dressing 
public class Dressing {
	int id = 0;
	String name = null;
	String info = null;
	ArrayList<String[]> ingreds = new ArrayList<String[]>();
	// Empty constructor
	public Dressing(){

	}
	public Dressing(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Dressing(int id, String name, String info) {
		this.id = id;
		this.name = name;
		this.info = info;
	}

	public Dressing(int id, String name, ArrayList<String[]> ingreds) {
		this.id = id;
		this.name = name;
		this.ingreds = ingreds;
	}

	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public ArrayList<String[]> getIngreds() {
		return ingreds;
	}
	public void setIngreds(ArrayList<String[]> ingreds) {
		this.ingreds = ingreds;
	}
	
	public void addIngred(String name, String parts, String optional) {
		String[] ingred = {name, parts, optional};
		ingreds.add(ingred);
	}
}
