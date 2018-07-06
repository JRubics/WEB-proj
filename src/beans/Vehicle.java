package beans;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class Vehicle {
	String brand;
	String model;
	String type;
	String registration_number;
	int year;
	boolean usable;
	String note;
	boolean active;
	
	public Vehicle() {
	}

	public Vehicle(String brand, String model, String type, String registration_number, int year, boolean usable,
			String note, boolean active) {
		super();
		this.brand = brand;
		this.model = model;
		this.type = type;
		this.registration_number = registration_number;
		this.year = year;
		this.usable=usable;
		this.note = note;
		this.active = active;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegistration_number() {
		return registration_number;
	}

	public void setRegistration_number(String registration_number) {
		this.registration_number = registration_number;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public boolean isUsable() {
		return usable;
	}

	public void setUsable(boolean usable) {
		this.usable = usable;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
	}
	
	
}
