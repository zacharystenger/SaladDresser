package com.example.saladdresser;

//Represents an Ingredient 
public class Ingred {
	int id = 0;
	String name = null;
	boolean selected = false;

	// Empty constructor
	public Ingred(){

	}
	// constructor
	public Ingred(int id, String name){
		this.id = id;
		this.name = name;
	}

	public Ingred(int id, String name, boolean selected) {
		super();
		this.id = id;
		this.name = name;
		this.selected = selected;
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
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
