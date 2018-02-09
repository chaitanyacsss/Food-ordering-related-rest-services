package com.satya.foodordering.restservices;

public class MenuItem {

	public MenuItem() {
	}

	public MenuItem(int id, String itemName, Double itemCost) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.itemCost = itemCost;
	}

	private String itemName;
	private Double itemCost;
	private int id;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemCost() {
		return itemCost;
	}

	public void setItemCost(Double itemCost) {
		this.itemCost = itemCost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
