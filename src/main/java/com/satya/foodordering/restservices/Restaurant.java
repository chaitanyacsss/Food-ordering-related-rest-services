package com.satya.foodordering.restservices;

import java.util.ArrayList;

public class Restaurant {

	int id;
	String name;
	ArrayList<Menu> menus = new ArrayList<Menu>();

	public Restaurant() {
	}

	public Restaurant(int id, String name, ArrayList<Menu> menus) {
		super();
		this.id = id;
		this.name = name;
		this.menus = menus;
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

	public ArrayList<Menu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}
}
