package com.satya.foodordering.restservices;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseOperations {

	FirebaseDatabase database;
	DatabaseReference ref;
	DatabaseReference itemsRef;
	DatabaseReference menusRef;
	DatabaseReference restaurantsRef;
	final List<MenuItem> itemsList = new ArrayList<MenuItem>();
	final List<Menu> menusList = new ArrayList<Menu>();
	final List<Restaurant> restaurantsList = new ArrayList<Restaurant>();

	public DatabaseOperations() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("restaurantservices.json");

		FirebaseOptions options = null;
		try {
			options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(is))
					.setDatabaseUrl("https://restaurantservicessatya.firebaseio.com").build();
		} catch (IOException e) {
			System.out.println("Error while firebase initialization: " + e.getMessage());
			e.printStackTrace();
		}

		FirebaseApp.initializeApp(options);
		// FirebaseDatabase.getInstance().setPersistenceEnabled(true);
		System.out.println("initialized firebase successfully");

		database = FirebaseDatabase.getInstance();
		ref = database.getReference("server/saving-data/fireblog");
		itemsRef = ref.child("items");
		menusRef = ref.child("menus");
		restaurantsRef = ref.child("restaurants");

		itemsRef.addValueEventListener(itemListener);
		menusRef.addValueEventListener(menuListener);
		restaurantsRef.addValueEventListener(restaurantListener);

	}

	ValueEventListener itemListener = new ValueEventListener() {

		public void onCancelled(DatabaseError arg0) {

		}

		@SuppressWarnings("unchecked")
		public void onDataChange(DataSnapshot arg0) {
			itemsList.clear();
			if (arg0.getKey() == "items") {
				Object values = arg0.getValue();
				for (Map<String, ?> item : ((Map<String, Map<String, ?>>) values).values()) {
					final ObjectMapper mapper = new ObjectMapper();
					final MenuItem menuItem = mapper.convertValue(item, MenuItem.class);
					itemsList.add(menuItem);
				}
			} else {
			}

		}
	};

	ValueEventListener menuListener = new ValueEventListener() {

		public void onCancelled(DatabaseError arg0) {

		}

		@SuppressWarnings("unchecked")
		public void onDataChange(DataSnapshot arg0) {
			menusList.clear();
			if (arg0.getKey() == "menus") {
				Object values = arg0.getValue();
				for (Map<String, ?> item : ((Map<String, Map<String, ?>>) values).values()) {
					final ObjectMapper mapper = new ObjectMapper();
					final Menu menu = mapper.convertValue(item, Menu.class);
					menusList.add(menu);
				}
			} else {
			}

		}
	};

	ValueEventListener restaurantListener = new ValueEventListener() {

		public void onCancelled(DatabaseError arg0) {

		}

		@SuppressWarnings("unchecked")
		public void onDataChange(DataSnapshot arg0) {
			restaurantsList.clear();
			if (arg0.getKey() == "restaurants") {
				Object values = arg0.getValue();
				for (Map<String, ?> item : ((Map<String, Map<String, ?>>) values).values()) {
					final ObjectMapper mapper = new ObjectMapper();
					final Restaurant restaurant = mapper.convertValue(item, Restaurant.class);
					restaurantsList.add(restaurant);
				}
			} else {
			}

		}
	};

	public void addItems(Map<String, Object> items) {
		itemsRef.updateChildrenAsync(items);
	}

	public Boolean deleteItems(String itemName) {
		return itemsRef.child(itemName).removeValueAsync().isDone();
		/*
		 * ValueEventListener itemdeletelistener = new ValueEventListener() {
		 * 
		 * public void onCancelled(DatabaseError arg0) {
		 * 
		 * }
		 * 
		 * public void onDataChange(DataSnapshot arg0) {
		 * arg0.getRef().removeValueAsync();
		 * 
		 * } };
		 * menusRef.child("items").orderByChild("itemName").equalTo(itemName)
		 * .addListenerForSingleValueEvent(itemdeletelistener);
		 * 
		 * restaurantsRef.child("menus").child("items").orderByChild("itemName")
		 * .equalTo(itemName)
		 * .addListenerForSingleValueEvent(itemdeletelistener);
		 */
	}

	public List<MenuItem> viewItems() throws InterruptedException {
		return itemsList;
	}

	public void addMenus(Map<String, Object> menusMap) {
		menusRef.updateChildrenAsync(menusMap);
	}

	public Boolean deleteMenus(String name) {
		return menusRef.child(name).removeValueAsync().isDone();

	}

	public List<Menu> viewMenus() {
		return menusList;

	}

	public void addRestaurants(Map<String, Object> resMap) {
		restaurantsRef.updateChildrenAsync(resMap);
	}

	public Boolean deleteRestaurants(String name) {
		return restaurantsRef.child(name).removeValueAsync().isDone();
	}

	public List<Restaurant> viewRestaurants() {
		return restaurantsList;
	}

	public Restaurant viewRestaurant(String name) {
		for (Restaurant restaurant : restaurantsList) {
			if (Objects.equals(restaurant.getName(), name)) {
				return restaurant;
			}
		}
		return null;
	}

	public Menu viewMenu(String name) {
		for (Menu menu : menusList) {
			if (Objects.equals(menu.getName(), name)) {
				//System.out.println("found menu : " + menu);
				return menu;
			}
		}
		return null;
	}

	public MenuItem viewItem(String itemName) {
		for (MenuItem item : itemsList) {
			if (Objects.equals(item.getItemName(), itemName)) {
				return item;
			}
		}
		return null;
	}

}
