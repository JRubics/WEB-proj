package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import beans.Article;
import beans.User;
import beans.Vehicle;

@Path("/vehicles")
public class VehicleService {
	
	@GET
	@Path("/test")
	public String test() {
		System.out.println("REST");
		return "REST is working.";
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(@Context HttpServletRequest request, String s) {
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		try {
			
	        FileReader fileReader = new FileReader("vehicles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Vehicle v = mapper.readValue(line, Vehicle.class);
	        	if(v.getRegistration_number().equals(s)){
	        		v.setActive(false);
	        	}
	        	vehicles.add(v);
	            line = bufferedReader.readLine();
	        }
	        bufferedReader.close();
	        fileReader.close();
	        System.out.println(vehicles);
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERRORA");
	    	e.printStackTrace();
	    }
		
		if(vehicles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("vehicles.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("vehicles.txt", true)));
				for(int k = 0; k < vehicles.size(); k++) {
					out.println(vehicles.get(k));	
				}
			    out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	@PUT
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String edit(@Context HttpServletRequest request, Vehicle vehicle) {
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		try {
			if(vehicle.getBrand().equals("") || vehicle.getClass().equals("") || vehicle.getModel().equals("") || vehicle.getNote().equals("") || vehicle.getYear()==0 ) {
				return "Fill all fields!";
			}
		}catch (Exception e) {
			System.out.println("NULL");
			return "Fill all fields!";
		}
		try {
			
	        FileReader fileReader = new FileReader("vehicles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Vehicle v = mapper.readValue(line, Vehicle.class);
	        	if(v.getRegistration_number().equals(vehicle.getRegistration_number())) {
	        		v.setBrand(vehicle.getBrand());
	        		v.setModel(vehicle.getModel());
	        		v.setNote(vehicle.getNote());
//	        		v.setUsable(vehicle.isUsable());
	        		v.setYear(vehicle.getYear());
	        	}
	        	vehicles.add(v);
	            line = bufferedReader.readLine();
	        }
	        bufferedReader.close();
	        fileReader.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERRORA");
	    	e.printStackTrace();
	    }
		
		if(vehicles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("vehicles.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("vehicles.txt", true)));
				for(int k = 0; k < vehicles.size(); k++) {
					out.println(vehicles.get(k));	
				}
			    out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}
	
	@GET
	@Path("/getAll")
	public String getAll(@Context HttpServletRequest request) {
		
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		try {
			
	        FileReader fileReader = new FileReader("vehicles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Vehicle v = mapper.readValue(line, Vehicle.class);
	        	vehicles.add(v);
	            line = bufferedReader.readLine();
	        }
	        bufferedReader.close();
	        fileReader.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERRORA");
	    	e.printStackTrace();
	    }
		
		if(vehicles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(vehicles);
				return json;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR3");
			}
		}
		return "";
	}
	
	@GET
	@Path("/getScooter")
	public String getScooter(@Context HttpServletRequest request) {
		
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		try {
			
	        FileReader fileReader = new FileReader("vehicles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Vehicle v = mapper.readValue(line, Vehicle.class);
	        	if(v.getType().equals("scooter")){
	        			vehicles.add(v);
	        	}
	            line = bufferedReader.readLine();
	        }
	        bufferedReader.close();
	        fileReader.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERRORA");
	    	e.printStackTrace();
	    }
		
		if(vehicles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(vehicles);
				return json;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR3");
			}
		}
		return "";
	}
	
	@GET
	@Path("/getBicycle")
	public String getBicycle(@Context HttpServletRequest request) {
		
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		try {
			
	        FileReader fileReader = new FileReader("vehicles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Vehicle v = mapper.readValue(line, Vehicle.class);
	        	if(v.getType().equals("bicycle")){
	        			vehicles.add(v);
	        	}
	            line = bufferedReader.readLine();
	        }
	        bufferedReader.close();
	        fileReader.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERRORA");
	    	e.printStackTrace();
	    }
		
		if(vehicles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(vehicles);
				return json;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR3");
			}
		}
		return "";
	}
	
	@GET
	@Path("/getCar")
	public String getCar(@Context HttpServletRequest request) {
		
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		try {
			
	        FileReader fileReader = new FileReader("vehicles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Vehicle v = mapper.readValue(line, Vehicle.class);
	        	if(v.getType().equals("car")){
	        			vehicles.add(v);
	        	}
	            line = bufferedReader.readLine();
	        }
	        bufferedReader.close();
	        fileReader.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERRORA");
	    	e.printStackTrace();
	    }
		
		if(vehicles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(vehicles);
				return json;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR3");
			}
		}
		return "";
	}
	
	@POST
	@Path("/addVehicle/car")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addCar(@Context HttpServletRequest request,Vehicle v) {
		String rez = addVehicle("car", v);
		return rez;
	}
	
	@POST
	@Path("/addVehicle/scooter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addScooter(@Context HttpServletRequest request,Vehicle v) {
		String rez = addVehicle("scooter", v);
		return rez;
	}
	
	@POST
	@Path("/addVehicle/bicycle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addBicycle(@Context HttpServletRequest request,Vehicle v) {
		String rez = addVehicle("bicycle", v);
		return rez;
	}

	public String addVehicle(String type, Vehicle v) {
		v.setActive(true);
		v.setType(type);
		v.setUsable(false);
		try {
			if(v.getBrand().equals("") || v.getClass().equals("") || v.getModel().equals("") || v.getNote().equals("") || v.getRegistration_number().equals("") || v.getYear()==0 ) {
				return "Fill all fields!";
			}
		}catch (Exception e) {
			System.out.println("NULL");
			return "";
		}
		
		boolean found = false;
		try {
	        FileReader fileReader = new FileReader("./vehicles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Vehicle vehicle = mapper.readValue(line, Vehicle.class);
	        	if(v.getRegistration_number().equals(vehicle.getRegistration_number())) {
	        		found = true;
	        		System.out.println("VEC POSTOJI");
	        	}
	            line = bufferedReader.readLine();
	        }
	        
	        bufferedReader.close();
	        fileReader.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERROR");
	    	try {
	        	File f = new File("vehicles.txt");
	
		    	f.createNewFile();

	    	}catch (Exception e1) {
	    		e1.printStackTrace();
	    		System.out.println("ERROR1");
			}
		}
		if(found == false) {
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("vehicles.txt", true)));
			    out.println(v);
			    out.close();
			}catch (Exception e) {
				e.printStackTrace();
	    		System.out.println("ERROR2");
			}
		}else {
			return "Registration number taken! Please choose another one.";
		}
		return "";

	}
	
	public String reloadVehicles(String s) {
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		try {
			
	        FileReader fileReader = new FileReader("vehicles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Vehicle v = mapper.readValue(line, Vehicle.class);
	        	if(v.getRegistration_number().equals(s)) {
	        		v.setUsable(!v.isUsable());
	        	}
	        	vehicles.add(v);
	            line = bufferedReader.readLine();
	        }
	        bufferedReader.close();
	        fileReader.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERRORA");
	    	e.printStackTrace();
	    }
		
		
		if(vehicles.isEmpty()) {
			return "";
		}else {
			
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("vehicles.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("vehicles.txt", true)));
				for(int k = 0; k < vehicles.size(); k++) {
					out.println(vehicles.get(k));	
				}
			    out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}
		return "";
	}
	
	@POST
	@Path("/chooseVehicle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String chooseVehicle(@Context HttpServletRequest request,String s) {
		s = s.substring(1, s.length()-1);
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			reloadVehicles(u.getVehicle());
			u.setVehicle(s);
			reloadVehicles(u.getVehicle());
			reloadUsers(u);
		}else {
			return "no user";
		}
		
		return "";
	}
	public String reloadUsers(User u) {
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			
	        FileReader fileReader = new FileReader("users.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	User user = mapper.readValue(line, User.class);
	        	if(user.getUsername().equals(u.getUsername())) {
	        		user = u;
	        	}
	        	users.add(user);
	            line = bufferedReader.readLine();
	        }
	        bufferedReader.close();
	        fileReader.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERRORA");
	    	e.printStackTrace();
	    }
		
		if(users.isEmpty()) {
			return "";
		}else {
			
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("users.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("users.txt", true)));
				for(int k = 0; k < users.size(); k++) {
					out.println(users.get(k));	
				}
			    out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}
		return "";
	}
}
