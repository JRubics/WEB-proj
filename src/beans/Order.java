package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;

public class Order {
	ArrayList<Article> articles = new ArrayList<Article>();
	ArrayList<Integer> counter = new ArrayList<Integer>();
	String date;
	String byer;
	String deliverer;
	String state;
	String note;
	boolean active;
	int price;
	int points;
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Order() {
		
	}
	
	public Order(ArrayList<Article> articles, ArrayList<Integer> counter, String date, String byer, String deliverer,
			String state, String note, int price, int points) {
		super();
		this.articles = articles;
		this.counter = counter;
		this.date = date;
		this.byer = byer;
		this.deliverer = deliverer;
		this.state = state;
		this.note = note;
		this.active = true;
		this.price = price;
		this.points = points;
	}



	public Order(ArrayList<Article> articles, ArrayList<Integer> counter, String byer, String note, int price, int points) {
		super();
		this.articles = articles;
		this.counter = counter;
		this.byer = byer;
		this.note = note;
		date = (new Date()).toString();
		deliverer = null;
		state = "Ordered";
		this.active = true;
		this.price = price;
		this.points = points;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}

	public ArrayList<Integer> getCounter() {
		return counter;
	}

	public void setCounter(ArrayList<Integer> counter) {
		this.counter = counter;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getByer() {
		return byer;
	}

	public void setByer(String byer) {
		this.byer = byer;
	}

	public String getDeliverer() {
		return deliverer;
	}

	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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
			e.printStackTrace();
			return "";
		}
	}
	
	
}
