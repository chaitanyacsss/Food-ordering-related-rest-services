package com.satya.foodordering.restservices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
public class RestaurantController {
	DatabaseOperations db = new DatabaseOperations();

	@GetMapping(value = "/getItem", produces = "application/json")
	public ResponseEntity getItem(@RequestParam(value = "itemName", required = false) String itemName)
			throws InterruptedException {
		if (Objects.equals(itemName, null)) {
			List<MenuItem> menuItems = db.viewItems();
			return new ResponseEntity(menuItems, HttpStatus.OK);
		} else {
			MenuItem item = db.viewItem(itemName);
			if (item != null) {
				return new ResponseEntity(item, HttpStatus.OK);
			} else {
				return new ResponseEntity(
						"Item with the given name not found. Please enter the exact name. You can see all available items by hitting /getitem url without params.",
						HttpStatus.OK);
			}
		}
	}

	@PostMapping(value = "/addItems", consumes = "application/json")
	public ResponseEntity postItem(@RequestBody MenuItem... menuItems) {

		addMenuItemsToDb(menuItems);
		return new ResponseEntity(menuItems, HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteItem", produces = "application/json")
	public ResponseEntity deleteItem(@RequestParam(value = "itemName", required = true) String itemName) {

		db.deleteItems(itemName);
		return new ResponseEntity("item : " + itemName + " * if available on db* deleted.", HttpStatus.OK);
	}

	@GetMapping(value = "/getMenu", produces = "application/json")
	public ResponseEntity getMenu(@RequestParam(value = "name", required = false) String name) {

		if (Objects.equals(name, null)) {
			List<Menu> menus = db.viewMenus();
			return new ResponseEntity(menus, HttpStatus.OK);
		} else {
			Menu menu = db.viewMenu(name);
			if (menu != null) {
				return new ResponseEntity(menu, HttpStatus.OK);
			} else {
				return new ResponseEntity(
						"Menu with the given name not found. Please enter the exact name. You can see all available menus by hitting /getmenu url without params.",
						HttpStatus.OK);
			}
		}
	}

	@PostMapping(value = "/addMenus", consumes = "application/json")
	public ResponseEntity postMenu(@RequestBody Menu... menus) {

		Map<String, Object> menusMap = addMenusToDb(menus);
		return new ResponseEntity(menusMap, HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteMenu", produces = "application/json")
	public ResponseEntity deleteMenu(@RequestParam(value = "name", required = true) String name) {

		db.deleteMenus(name);
		return new ResponseEntity("menu : " + name + " * if available on db* deleted.", HttpStatus.OK);
	}

	@GetMapping(value = "/getRestaurant", produces = "application/json")
	public ResponseEntity getRestaurant(@RequestParam(value = "name", required = false) String name) {

		if (Objects.equals(name, null)) {
			List<Restaurant> restaurants = db.viewRestaurants();
			return new ResponseEntity(restaurants, HttpStatus.OK);
		} else {
			Restaurant restaurant = db.viewRestaurant(name);
			if (restaurant != null) {
				return new ResponseEntity(restaurant, HttpStatus.OK);
			} else {
				return new ResponseEntity(
						"Restaurant with the given name not found. Please enter the exact name. You can see all available restaurants by hitting /getrestaurant url without params.",
						HttpStatus.OK);
			}
		}
	}

	@PostMapping(value = "/addRestaurants", consumes = "application/json")
	public ResponseEntity postRestaurant(@RequestBody Restaurant... restaurants) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		for (Restaurant res : restaurants) {
			resMap.put(res.getName(), res);
			for (Menu menu : res.getMenus()) {
				addMenusToDb(menu);
			}
		}
		db.addRestaurants(resMap);
		return new ResponseEntity(resMap, HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteRestaurant", produces = "application/json")
	public ResponseEntity deleteRestaurant(@RequestParam(value = "name", required = true) String name) {

		db.deleteRestaurants(name);
		return new ResponseEntity("restaurant : " + name + " * if available on db* deleted.", HttpStatus.OK);
	}

	private void addMenuItemsToDb(MenuItem... menuItems) {
		Map<String, Object> items = new HashMap<String, Object>();
		for (MenuItem menuItem : menuItems) {
			items.put(menuItem.getItemName(), menuItem);
		}
		db.addItems(items);
	}

	private Map<String, Object> addMenusToDb(Menu... menus) {
		Map<String, Object> menusMap = new HashMap<String, Object>();
		for (Menu menu : menus) {
			menusMap.put(menu.getName(), menu);
			for (MenuItem item : menu.getItems()) {
				addMenuItemsToDb(item);
			}
		}
		db.addMenus(menusMap);
		return menusMap;
	}

	/*
	 * private Restaurant fetchRestaurant(Integer id, String name) { MenuItem
	 * bacon = new MenuItem(id, "bacon", 1.55); MenuItem sub = new MenuItem(id,
	 * "sub", 2.22); MenuItem burger = new MenuItem(id, "burger", 3.33);
	 * 
	 * ArrayList<MenuItem> items = new ArrayList<MenuItem>(); items.add(bacon);
	 * items.add(sub); items.add(burger);
	 * 
	 * Menu breakfast = new Menu(id, "breakfast", items); Menu lunch = new
	 * Menu(id, "lunch", items); Menu dinner = new Menu(id, "dinner", items);
	 * ArrayList<Menu> menus = new ArrayList<Menu>(); menus.add(breakfast);
	 * menus.add(lunch); menus.add(dinner);
	 * 
	 * Restaurant res = new Restaurant(id, name, menus); return res; }
	 */

	/*
	 * private Integer setDefaultId(Integer id) { if (id == null) { // TODO :
	 * add logic to print all items
	 * System.out.println("id value not entered; taking default value of 1"); id
	 * = 1; } return id; }
	 */
}
