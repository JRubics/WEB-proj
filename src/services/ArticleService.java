package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import beans.Order;
import beans.Restaurant;
import beans.User;
import beans.Vehicle;

@Path("/articles")
public class ArticleService {
	
	@GET
	@Path("/test")
	public String test() {
		System.out.println("REST");
		return "REST is working.";
	}
	
	@GET
	@Path("/getCart")
	public String getCart(@Context HttpServletRequest request) {
		
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			ArrayList<Article> articles = new ArrayList<Article>();
			
			try {
				
				FileReader fileReader = new FileReader("articles.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);  
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufferedReader.readLine();
		        ObjectMapper mapper = new ObjectMapper();
		        
		        while (line != null) {
		        	Article a = mapper.readValue(line, Article.class);
		        	articles.add(a);
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
			
			if(articles.isEmpty()) {
				return "";
			}else {
				
				ArrayList<String> cart = new ArrayList<String>();
				HashMap<Article, Integer> articlesInCart = new HashMap<Article, Integer>();
				
				cart = u.getCart();
				for(int i = 0; i < articles.size(); i++) {
					for(int j = 0; j < cart.size(); j++) {
						if(articles.get(i).getName().equals(cart.get(j))) {
							if(articlesInCart.containsKey(articles.get(i))) {
								articlesInCart.put(articles.get(i), articlesInCart.get(articles.get(i)) + 1); 
							}else {
								articlesInCart.put(articles.get(i), 1);
							}
						}
					}
				}
				
				ObjectMapper mapper = new ObjectMapper();
				try {
					String json = mapper.writeValueAsString(articlesInCart);
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
	
	@GET
	@Path("/getPopular")
	public String getPopular(@Context HttpServletRequest request) {
		ArrayList<Article> articles = new ArrayList<Article>();
		
		try {
			
	        FileReader fileReader = new FileReader("articles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Article a = mapper.readValue(line, Article.class);
	        	articles.add(a);
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
		
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			
	        FileReader fileReader = new FileReader("orders.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Order o = mapper.readValue(line, Order.class);
	        	orders.add(o);
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
		
		if(articles.isEmpty() || orders.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			HashMap<String, Integer> popularity = new HashMap<String, Integer>();
			for(int k = 0; k < orders.size(); k++) {
				for(int l = 0; l < orders.get(k).getArticles().size(); l++) {
					if(popularity.containsKey(orders.get(k).getArticles().get(l).getName())) {
						popularity.put(orders.get(k).getArticles().get(l).getName(), popularity.get(orders.get(k).getArticles().get(l).getName()) + 1); 
					}else {
						popularity.put(orders.get(k).getArticles().get(l).getName(), 1);
					}
				}
			}
			
//			System.out.println(popularity);
			
			//ima mapu,sad treba da sortira
			ArrayList<String> sortList = new ArrayList<String>();
			ArrayList<Integer> counter = new ArrayList<Integer>();
			for (String key : popularity.keySet()) {
				sortList.add(key);
				counter.add(popularity.get(key));
			}
			for(int i = 0; i < sortList.size()-1; i++) {
				for(int j = i+1; j < sortList.size(); j++) {
					if(counter.get(i) < counter.get(j)) {
						int temp = counter.get(i);
						counter.set(i, counter.get(j));
						counter.set(j, temp);
						
						String tmp= sortList.get(i);
						sortList.set(i, sortList.get(j));
						sortList.set(j, tmp);
					}
				}
			}
			
//			System.out.println(sortList);
//			System.out.println(counter);
			
			ArrayList<Article> popularArticles = new ArrayList<Article>();
			for(int i = 0; i < sortList.size(); i++) {
				for(int j = 0; j < articles.size(); j++) {
					if(sortList.get(i).equals(articles.get(j).getName())) {
						popularArticles.add(articles.get(j));
					}
				}
			}
			
//			System.out.println(popularArticles);
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(popularArticles);
				return json;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR3");
			}
		}
		System.out.println("no");
		return "";
	}
	
		
	@POST
	@Path("/setQuantity")
	public String setQuantity(@Context HttpServletRequest request,String[] s) {
		String id = s[0];
		int val = Integer.parseInt(s[1]);
		
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			ArrayList<Article> articles = new ArrayList<Article>();
			
			try {
				
				FileReader fileReader = new FileReader("articles.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);  
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufferedReader.readLine();
		        ObjectMapper mapper = new ObjectMapper();
		        
		        while (line != null) {
		        	Article a = mapper.readValue(line, Article.class);
		        	articles.add(a);
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
			
			if(articles.isEmpty()) {
				return "";
			}else {
				
				ArrayList<String> cart = new ArrayList<String>();
				HashMap<Article, Integer> articlesInCart = new HashMap<Article, Integer>();
				
				cart = u.getCart();
				for(int i = 0; i < articles.size(); i++) {
					for(int j = 0; j < cart.size(); j++) {
						if(articles.get(i).getName().equals(cart.get(j))) {
							if(articlesInCart.containsKey(articles.get(i))) {
								articlesInCart.put(articles.get(i), articlesInCart.get(articles.get(i)) + 1); 
							}else {
								articlesInCart.put(articles.get(i), 1);
							}
						}
					}
				}
				
				for (Article key : articlesInCart.keySet()) {
				    if(key.getName().equals(id)) {
				    	if(val > articlesInCart.get(key)) {
				    		u.getCart().add(id);
				    		reloadUsers(u);
				    	}
				    	else if(val < articlesInCart.get(key)) {
				    		u.getCart().remove(id);
				    		reloadUsers(u);
				    	}
				    }
				}
				
				ObjectMapper mapper = new ObjectMapper();
				try {
					String json = mapper.writeValueAsString(articlesInCart);
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
	
	@POST
	@Path("/order")
	public String order(@Context HttpServletRequest request,String s) {
		s = s.substring(1, s.length()-1);
		System.out.println(s);
		
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			u.getCart().add(s);
		
		
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
		}else {
			return "no user";
		}
		
		return "";
	}
	
	
	@POST
	@Path("/search")
	public String search(@Context HttpServletRequest request,String[] s) {
		ArrayList<Article> articles = new ArrayList<Article>();
		String name = s[0];
		String type = s[1];
		int minprice;
		int maxprice;
		try {
			minprice = Integer.parseInt(s[2]);
		}catch (Exception e) {
			minprice = Integer.MIN_VALUE;
		}
		try {
			maxprice = Integer.parseInt(s[3]);
		}catch (Exception e) {
			maxprice = Integer.MAX_VALUE;
		}
		String restaurant = s[4];
		//System.out.println(name + " " + type + " " + minprice + " " + maxprice + " " + restaurant);
		
		try {
			
	        FileReader fileReader = new FileReader("articles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Article a = mapper.readValue(line, Article.class);
	        	if((a.getName().equals(name) || name.equals("")) && (a.getType().equals(type.toLowerCase()) || type.equals("Any")) && (a.getPrice() >= minprice  && a.getPrice() <= maxprice)) {
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
		
		Restaurant r = null; //nadje uvek jedan posto trazi po imenu
		
		try {
			
	        FileReader fileReader = new FileReader("restaurants.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Restaurant res = mapper.readValue(line, Restaurant.class);
	        	if(res.getName().equals(restaurant)) {
	        		r = res;
	        	}
	            line = bufferedReader.readLine();
	        }
	        bufferedReader.close();
	        fileReader.close();
	        System.out.println(r);
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERRORA");
	    	e.printStackTrace();
	    }
		
		if(articles.isEmpty()) {
			System.out.println("no1");
			return "";
		}else {
			//provera za restoran
			ArrayList<Article> RESarticles = new ArrayList<Article>();
			if(r != null) {
				try {
					for(int i = 0; i < r.getDrinks().size(); i++) {
						for(int j = 0; j < articles.size(); j++) {
							if(articles.get(j).getName().equals(r.getDrinks().get(i))) {
								RESarticles.add(articles.get(j));
							}
						}
					}
				}catch (Exception e) {
					return "";
				}finally {
					
				}
				try{
					for(int i = 0; i < r.getDishes().size(); i++) {
						for(int j = 0; j < articles.size(); j++) {
							if(articles.get(j).getName().equals(r.getDishes().get(i))) {
								RESarticles.add(articles.get(j));
							}
						}
					}
				}catch (Exception e) {
					return "";
				}finally {
					
				}
			}else {
				RESarticles = articles;
			}
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(RESarticles);
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
	@Path("/getDrinks")
	public String getDrinks(@Context HttpServletRequest request) {
		
		ArrayList<Article> articles = new ArrayList<Article>();
		
		try {
			
	        FileReader fileReader = new FileReader("articles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Article a = mapper.readValue(line, Article.class);
	        	if(a.getType().equals("drink")){
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
		
		if(articles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
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
		System.out.println("no");
		return "";
	}

	@GET
	@Path("/getFood")
	public String getFood(@Context HttpServletRequest request) {
		
		ArrayList<Article> articles = new ArrayList<Article>();
		
		try {
			
	        FileReader fileReader = new FileReader("articles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Article a = mapper.readValue(line, Article.class);
	        	if(a.getType().equals("food")){
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
		
		if(articles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
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
		System.out.println("no");
		return "";
	}
	
	@GET
	@Path("/getAll")
	public String getAll(@Context HttpServletRequest request) {
		
		ArrayList<Article> articles = new ArrayList<Article>();
		
		try {
			
	        FileReader fileReader = new FileReader("articles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Article a = mapper.readValue(line, Article.class);
	        	articles.add(a);
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
		
		if(articles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
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
		System.out.println("no");
		return "";
	}

	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addFood(@Context HttpServletRequest request, String s) {
		
		ArrayList<Article> articles = new ArrayList<Article>();
		
		try {
			
	        FileReader fileReader = new FileReader("articles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Article a = mapper.readValue(line, Article.class);
	        	if(a.getName().equals(s)){
	        		a.setActive(false);
	        	}
	        	articles.add(a);
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
		
		if(articles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("articles.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("articles.txt", true)));
				for(int k = 0; k < articles.size(); k++) {
					out.println(articles.get(k));	
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
	public String edit(@Context HttpServletRequest request, Article article) {
		try {
			if(article.getPrice() == 0 || article.getQuantity() == 0 || article.getAbout().equals("")) {
				return "Fill all fields!";
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("NULL");
			return "Fill all fields!";
		}
		ArrayList<Article> articles = new ArrayList<Article>();
		
		try {
			
	        FileReader fileReader = new FileReader("articles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Article a = mapper.readValue(line, Article.class);
	        	if(a.getName().equals(article.getName())){
	        		a.setAbout(article.getAbout());
	        		a.setPrice(article.getPrice());
	        		a.setQuantity(article.getQuantity());
	        	}
	        	articles.add(a);
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
		
		if(articles.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("articles.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("articles.txt", true)));
				for(int k = 0; k < articles.size(); k++) {
					out.println(articles.get(k));	
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
	@Path("/addFood")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addFood(@Context HttpServletRequest request,Article a) {
		a.setActive(true);
		a.setType("food");
		
		try {
			if(a.getName().equals("") || a.getPrice() == 0 || a.getQuantity() == 0 || a.getAbout().equals("")) {
				return "Fill all fields!";
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("NULL");
			return "";
		}
		
		boolean found = false;
		try {
	        FileReader fileReader = new FileReader("articles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Article article = mapper.readValue(line, Article.class);
	        	if(a.getName().equals(article.getName())) {
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
	        	File f = new File("articles.txt");
	
		    	//f.getParentFile().mkdirs(); 
		    	f.createNewFile();

	    	}catch (Exception e1) {
	    		e1.printStackTrace();
	    		System.out.println("ERROR1");
			}
		}
		if(found == false) {
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("articles.txt", true)));
			    out.println(a);
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
	
	@POST
	@Path("/addDrink")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addDrink(@Context HttpServletRequest request,Article a) {
		a.setActive(true);
		a.setType("drink");
		
		try {
			if(a.getName().equals("") || a.getPrice() == 0 || a.getQuantity() == 0 || a.getAbout().equals("")) {
				return "Fill all fields!";
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("NULL");
			return "";
		}
		
		boolean found = false;
		try {
	        FileReader fileReader = new FileReader("articles.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Article article = mapper.readValue(line, Article.class);
	        	if(a.getName().equals(article.getName())) {
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
	        	File f = new File("articles.txt");
	
		    	//f.getParentFile().mkdirs(); 
		    	f.createNewFile();

	    	}catch (Exception e1) {
	    		e1.printStackTrace();
	    		System.out.println("ERROR1");
			}
		}
		if(found == false) {
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("articles.txt", true)));
			    out.println(a);
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

}
