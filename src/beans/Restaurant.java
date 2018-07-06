package beans;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;

public class Restaurant {
	String Name;
	String Adress;
	String Type;
	boolean active;
	
	ArrayList<String> drinks = new ArrayList<String>();
	ArrayList<String> dishes = new ArrayList<String>();
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAdress() {
		return Adress;
	}
	public void setAdress(String adress) {
		Adress = adress;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public ArrayList<String> getDrinks() {
		return drinks;
	}
	public void setDrinks(ArrayList<String> drinks) {
		this.drinks = drinks;
	}
	public ArrayList<String> getDishes() {
		return dishes;
	}
	public void setDishes(ArrayList<String> dishes) {
		this.dishes = dishes;
	}
	public Restaurant(String name, String adress, String type, ArrayList<String> drinks, ArrayList<String> dishes) {
		super();
		Name = name;
		Adress = adress;
		Type = type;
		this.drinks = drinks;
		this.dishes = dishes;
		this.active = true;
	}
	public Restaurant() {
	}
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(this);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
}
