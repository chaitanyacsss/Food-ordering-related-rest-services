package com.satya.foodordering.restservices;

import java.util.ArrayList;

public class Menu {

	public Menu() {
	}

	public Menu(int id, String name, ArrayList<MenuItem> items) {
		super();
		this.id = id;
		this.name = name;
		this.items = items;
	}

	int id;
	String name;
	ArrayList<MenuItem> items = new ArrayList<MenuItem>();

	public ArrayList<MenuItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<MenuItem> items) {
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
