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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import beans.Article;
import beans.Restaurant;
import beans.User;
import beans.Vehicle;

@Path("/restaurants")
public class RestaurantService {
	
	@GET
	@Path("/test")
	public String test() {
		System.out.println("REST");
		return "REST is working.";
	}
	
	@GET
	@Path("/getFavorite")
	public String getFavorite(@Context HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
			try {
				
		        FileReader fileReader = new FileReader("restaurants.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);  
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufferedReader.readLine();
		        ObjectMapper mapper = new ObjectMapper();
		        
		        while (line != null) {
		        	Restaurant res = mapper.readValue(line, Restaurant.class);
		        	restaurants.add(res);
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
			
			if(restaurants.isEmpty()) {
				return "";
			}else {
				ArrayList<Restaurant> returnRestaurants = new ArrayList<Restaurant>();
				for(int i = 0; i < restaurants.size(); i++) {
					for(int j = 0; j < u.getRestaurants().size(); j++) {
						if(restaurants.get(i).getName().equals(u.getRestaurants().get(j))) {
							returnRestaurants.add(restaurants.get(i));
						}
					}
				}
				ObjectMapper mapper = new ObjectMapper();
				try {
					String json = mapper.writeValueAsString(returnRestaurants);
					return json;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("ERROR3");
				}
			}
		}else {
			return "no user";
		}
		return "";
	}
	
	@POST
	@Path("/favorite")
	public String favorite(@Context HttpServletRequest request,String s) {
		s = s.substring(1, s.length()-1);
		System.out.println(s);
		
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			boolean found = false;
			for(int i = 0; i < u.getRestaurants().size(); i++) {
				if(u.getRestaurants().get(i).equals(s)) {
					found = true;
				}
			}
			if(!found) {
				u.getRestaurants().add(s);
			}
			reloadUsers(u);
		}else {
			return "no user";
		}
		return "";
	}
	
//	@POST
//	@Path("/addRestaurant/local")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String addLocal(@Context HttpServletRequest request,Restaurant r) {
//		String rez = addRestaurant("local", r);
//		return rez;
//	}
//	
//	@POST
//	@Path("/addRestaurant/grill")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String addGrill(@Context HttpServletRequest request,Restaurant r) {
//		String rez = addRestaurant("grill", r);
//		return rez;
//	}
//	
//	@POST
//	@Path("/addRestaurant/chinese")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String addChinese(@Context HttpServletRequest request,Restaurant r) {
//		String rez = addRestaurant("chinese", r);
//		return rez;
//	}
//	
//	@POST
//	@Path("/addRestaurant/indian")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String addIndian(@Context HttpServletRequest request,Restaurant r) {
//		String rez = addRestaurant("indian", r);
//		return rez;
//	}
//	
//	@POST
//	@Path("/addRestaurant/sweets")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String addSweets(@Context HttpServletRequest request,Restaurant r) {
//		String rez = addRestaurant("sweets", r);
//		return rez;
//	}
//	
//	@POST
//	@Path("/addRestaurant/italian")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String addItalian(@Context HttpServletRequest request,Restaurant r) {
//		String rez = addRestaurant("italian", r);
//		return rez;
//	}
	
	@POST
	@Path("/addRestaurant/{type}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addItalian(@PathParam("type") String type,Restaurant r) {
		String rez = addRestaurant(type, r);
		return rez;
	}

	public String addRestaurant(String type, Restaurant r) {
		r.setActive(true);
		r.setType(type);
		try {
			if(r.getName().equals("") || r.getAdress().equals("")) {
				return "Fill all fields!";
			}
		}catch (Exception e) {
			System.out.println("NULL");
			return "";
		}
		
		boolean found = false;
		try {
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant res = mapper.readValue(line, Restaurant.class);
	        	if(r.getName().equals(res.getName())) {
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
	        	File f = new File("restaurants.txt");
	
		    	f.createNewFile();

	    	}catch (Exception e1) {
	    		e1.printStackTrace();
	    		System.out.println("ERROR1");
			}
		}
		if(found == false) {
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", true)));
			    out.println(r);
			    out.close();
			}catch (Exception e) {
				e.printStackTrace();
	    		System.out.println("ERROR2");
			}
		}else {
			return "Name taken! Please choose another one.";
		}
		return "";

	}
	
//	@GET
//	@Path("/get/local")
//	public String getCar() {
//		String rez = getRestaurant("local");
//		return rez;
//	}
//	
//	@GET
//	@Path("/get/grill")
//	public String getGrill() {
//		String rez = getRestaurant("grill");
//		return rez;
//	}
//	
//	@GET
//	@Path("/get/chinese")
//	public String getChinese() {
//		String rez = getRestaurant("chinese");
//		return rez;
//	}
//	
//	@GET
//	@Path("/get/indian")
//	public String getIndian() {
//		String rez = getRestaurant("indian");
//		return rez;
//	}
//	
//	@GET
//	@Path("/get/sweets")
//	public String getSweets(@Context HttpServletRequest request) {
//		String rez = getRestaurant("sweets");
//		return rez;
//	}
//	
//	@GET
//	@Path("/get/italian")
//	public String getItalian() {
//		String rez = getRestaurant("italian");
//		return rez;
//	}
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
	
	@GET
	@Path("/get/{type}")
	public String getItalian(@PathParam("type") String id) {
		String rez = getRestaurant(id);
		return rez;
	}
	
	public String getRestaurant(String type) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant res = mapper.readValue(line, Restaurant.class);
	        	if(res.getType().equals(type)) {
	        		restaurants.add(res);
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
		
		if(restaurants.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(restaurants);
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
	@Path("/getAll")
	public String getAll(@Context HttpServletRequest request) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant res = mapper.readValue(line, Restaurant.class);
	        	restaurants.add(res);
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
		
		if(restaurants.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(restaurants);
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
	@Path("/search")
	public String search(@Context HttpServletRequest request,Restaurant r) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant res = mapper.readValue(line, Restaurant.class);
	        	if((res.getName().equals(r.getName()) || r.getName().equals("")) && (res.getAdress().equals(r.getAdress()) || r.getAdress().equals("")) && (r.getType().equals("Any") || res.getType().equals(r.getType().toLowerCase()))) {
	        		restaurants.add(res);
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
		if(restaurants.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(restaurants);
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
	@Path("/getDrinks")
	public String getDrinks(@Context HttpServletRequest request, String name) {
		name = name.substring(1, name.length()-1);
		ArrayList<Article> articles = new ArrayList<Article>();
		ArrayList<String> articlesStr = new ArrayList<String>();
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant res = mapper.readValue(line, Restaurant.class);
	        	if(res.getName().equals(name)) {
	        		for(int i = 0; i < res.getDrinks().size(); i++) {
	        			articlesStr.add(res.getDrinks().get(i));
	        		}
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
		
		if(articlesStr.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			for(int i = 0; i < articlesStr.size(); i++) {
				try {
					
			        FileReader fileReader = new FileReader("articles.txt");
			        BufferedReader bufferedReader = new BufferedReader(fileReader);  
			        
			        StringBuilder sb = new StringBuilder();
			        String line = bufferedReader.readLine();
			        ObjectMapper mapper = new ObjectMapper();
			        
			        while (line != null) {
			        	Article a = mapper.readValue(line, Article.class);
			        	if(a.getType().equals("drink") && a.getName().equals(articlesStr.get(i))){
			        		articles.add(a);
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
			}
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(articles);
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
	@Path("/getFood")
	public String getFood(@Context HttpServletRequest request, String name) {
		name = name.substring(1, name.length()-1);
		ArrayList<Article> articles = new ArrayList<Article>();
		ArrayList<String> articlesStr = new ArrayList<String>();
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant res = mapper.readValue(line, Restaurant.class);
	        	if(res.getName().equals(name)) {
	        		for(int i = 0; i < res.getDishes().size(); i++) {
	        			articlesStr.add(res.getDishes().get(i));
	        		}
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
		
		if(articlesStr.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			for(int i = 0; i < articlesStr.size(); i++) {
				try {
					
			        FileReader fileReader = new FileReader("articles.txt");
			        BufferedReader bufferedReader = new BufferedReader(fileReader);  
			        
			        StringBuilder sb = new StringBuilder();
			        String line = bufferedReader.readLine();
			        ObjectMapper mapper = new ObjectMapper();
			        
			        while (line != null) {
			        	Article a = mapper.readValue(line, Article.class);
			        	if(a.getType().equals("food") && a.getName().equals(articlesStr.get(i))){
			        		articles.add(a);
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
			}
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(articles);
				return json;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR3");
			}
		}
		return "";
	}
	
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(@Context HttpServletRequest request, String s) {
		
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant res = mapper.readValue(line, Restaurant.class);
	        	if(res.getName().equals(s)){
	        		res.setActive(false);
	        	}
	        	restaurants.add(res);
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
		
		if(restaurants.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", true)));
				for(int k = 0; k < restaurants.size(); k++) {
					out.println(restaurants.get(k));	
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
	public String edit(@Context HttpServletRequest request, Restaurant restaurant) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			if(restaurant.getAdress().equals("")) {
				return "Fill all fields!";
			}
		}catch (Exception e) {
			System.out.println("NULL");
			return "";
		}
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant r = mapper.readValue(line, Restaurant.class);
	        	if(r.getName().equals(restaurant.getName())) {
	        		r.setAdress(restaurant.getAdress());
	        	}
	        	restaurants.add(r);
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
		
		if(restaurants.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", true)));
				for(int k = 0; k < restaurants.size(); k++) {
					out.println(restaurants.get(k));	
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
	@Path("/addDrink")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addDrink(@Context HttpServletRequest request, String[] s) {
		String restaurant = s[0];
		String article = s[1];
		
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant r = mapper.readValue(line, Restaurant.class);
	        	restaurants.add(r);
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
		
		if(restaurants.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			for(int i = 0; i < restaurants.size(); i++) {
				if(restaurants.get(i).getName().equals(restaurant)) {
	        		try {
	        			
	        	        FileReader fileReader = new FileReader("articles.txt");
	        	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        	        
	        	        StringBuilder sb = new StringBuilder();
	        	        String line = bufferedReader.readLine();
	        	        ObjectMapper mapper = new ObjectMapper();
	        	        
	        	        while (line != null) {
	        	        	Article a = mapper.readValue(line, Article.class);
	        	        	if(a.getType().equals("drink") && a.getName().equals(article) ){
	        	        		boolean foundarticle = false;
	        	        		for(int j = 0; j < restaurants.get(i).getDrinks().size(); j++) {
	        	        			if(restaurants.get(i).getDrinks().get(j).equals(a.getName())) {
	        	        				foundarticle = true;
	        	        				return "Restaurant already contains that article";
	        	        			}
	        	        		}
	        	        		if(!foundarticle) {
	        	        			restaurants.get(i).getDrinks().add(article);
	        	        		}
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
				}
			}
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", true)));
				for(int k = 0; k < restaurants.size(); k++) {
					out.println(restaurants.get(k));	
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
	@Path("/addFood")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addFood(@Context HttpServletRequest request, String[] s) {
		String restaurant = s[0];
		String article = s[1];
		
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant r = mapper.readValue(line, Restaurant.class);
	        	restaurants.add(r);
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
		
		if(restaurants.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			for(int i = 0; i < restaurants.size(); i++) {
				if(restaurants.get(i).getName().equals(restaurant)) {
	        		try {
	        			
	        	        FileReader fileReader = new FileReader("articles.txt");
	        	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        	        
	        	        StringBuilder sb = new StringBuilder();
	        	        String line = bufferedReader.readLine();
	        	        ObjectMapper mapper = new ObjectMapper();
	        	        
	        	        while (line != null) {
	        	        	Article a = mapper.readValue(line, Article.class);
	        	        	if(a.getType().equals("food") && a.getName().equals(article) ){
	        	        		boolean foundarticle = false;
	        	        		for(int j = 0; j < restaurants.get(i).getDishes().size(); j++) {
	        	        			if(restaurants.get(i).getDishes().get(j).equals(a.getName())) {
	        	        				foundarticle = true;
	        	        				return "Restaurant already contains that article";
	        	        			}
	        	        		}
	        	        		if(!foundarticle) {
	        	        			restaurants.get(i).getDishes().add(article);
	        	        		}
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
				}
			}
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", true)));
				for(int k = 0; k < restaurants.size(); k++) {
					out.println(restaurants.get(k));	
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
	@Path("/removeDrink")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String removeDrink(@Context HttpServletRequest request, String[] s) {
		String restaurant = s[0];
		String article = s[1];
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant r = mapper.readValue(line, Restaurant.class);
	        	if(r.getName().equals(restaurant)) {
	        		int j = 0;
		        	for(int i = 0; i < r.getDrinks().size(); i++) {
		        		if(r.getDrinks().get(i).equals(article)) {
		        			j = i;
		        		}
		        	}
		        	r.getDrinks().remove(j);
	        	}
	        	restaurants.add(r);
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
		
		if(restaurants.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", true)));
				for(int k = 0; k < restaurants.size(); k++) {
					out.println(restaurants.get(k));	
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
	@Path("/removeFood")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String removeFood(@Context HttpServletRequest request, String[] s) {
		System.out.println("REMOVE " + s[0]);
		String restaurant = s[0];
		String article = s[1];
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant r = mapper.readValue(line, Restaurant.class);
	        	if(r.getName().equals(restaurant)) {
	        		int j = 0;
		        	for(int i = 0; i < r.getDishes().size(); i++) {
		        		if(r.getDishes().get(i).equals(article)) {
		        			j = i;
		        		}
		        	}
		        	r.getDishes().remove(j);
	        	}
	        	restaurants.add(r);
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
		
		if(restaurants.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("restaurants.txt", true)));
				for(int k = 0; k < restaurants.size(); k++) {
					out.println(restaurants.get(k));	
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
