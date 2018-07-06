package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

public class User {
	private String username;
	private String password;
	private String name;
	private String surname;
	private String role;
	private String phone_number;
	private String email;
	private Date registeration_date;
	String vehicle;
	int points;
	ArrayList<String> cart = new ArrayList<String>();
	ArrayList<String> orders = new ArrayList<String>();
	ArrayList<String> restaurants = new ArrayList<String>();
	
	public User() {
		
	}

	public User(String username, String password, String name, String surname, String role, String phone_number,
			String email, Date registeration_date, String vehicle, int points, ArrayList<String> cart, ArrayList<String> orders, ArrayList<String> res) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.role = role;
		this.phone_number = phone_number;
		this.email = email;
		this.registeration_date = registeration_date;
		this.vehicle = vehicle;
		this.points=points;
		this.cart = cart;
		this.orders = orders;
		this.restaurants = res;
	}
	public ArrayList<String> getCart() {
		return cart;
	}
	public void setCart(ArrayList<String> cart) {
		this.cart = cart;
	}
	public ArrayList<String> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<String> orders) {
		this.orders = orders;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegisteration_date() {
		return registeration_date;
	}

	public void setRegisteration_date(Date date) {
		this.registeration_date = date;
	}
	
	public ArrayList<String> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(ArrayList<String> restaurants) {
		this.restaurants = restaurants;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(this);
			return json;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
//		return "User [username=" + username + ", password=" + password + ", name=" + name + ", surname=" + surname
//				+ ", role=" + role + ", phone_number=" + phone_number + ", email=" + email + ", registeration_date="
//				+ registeration_date + "]";
	}
}
