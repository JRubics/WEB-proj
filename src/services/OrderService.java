package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

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

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import beans.Article;
import beans.Order;
import beans.Restaurant;
import beans.User;

@Path("/orders")
public class OrderService {

	@GET
	@Path("/getOrder")
	public String getOrder(@Context HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			Order o = (Order) request.getSession().getAttribute("order");
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(o);
				return json;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("ERROR3");
			}
		}else {
			return "no user";
		}
		return "";
	}
	
	@GET
	@Path("/getUserHistory")
	public String getCart(@Context HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			
			ArrayList<Order> orders = new ArrayList<Order>();
			try {
				
		        FileReader fileReader = new FileReader("orders.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);  
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufferedReader.readLine();
		        ObjectMapper mapper = new ObjectMapper();
		        
		        while (line != null) {
		        	Order o = mapper.readValue(line, Order.class);
		        	if(o.getByer().equals(u.getUsername())) {
		        		orders.add(o);
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
			if(orders.isEmpty()) {
				return "";
			}else {
				ObjectMapper mapper = new ObjectMapper();
				try {
					String json = mapper.writeValueAsString(orders);
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
	@Path("/getAllOrders")
	public String getAllOrders(@Context HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			
			ArrayList<Order> orders = new ArrayList<Order>();
			try {
				
		        FileReader fileReader = new FileReader("orders.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);  
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufferedReader.readLine();
		        ObjectMapper mapper = new ObjectMapper();
		        
		        while (line != null) {
		        	Order o = mapper.readValue(line, Order.class);
		        	if(o.getDeliverer()!= null && o.getDeliverer().equals(u.getUsername()) && o.getState().equals("Delivery in progress")) {
		        		orders.clear();
		        		return "";
		        	}
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
			if(orders.isEmpty()) {
				return "";
			}else {
				ObjectMapper mapper = new ObjectMapper();
				try {
					String json = mapper.writeValueAsString(orders);
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
	@Path("/openEdit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String openEdit(@Context HttpServletRequest request, String s) {
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			ArrayList<Order> orders = new ArrayList<Order>();
			try {
				
		        FileReader fileReader = new FileReader("orders.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);  
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufferedReader.readLine();
		        ObjectMapper mapper = new ObjectMapper();
		        
		        while (line != null) {
		        	Order o = mapper.readValue(line, Order.class);
		        	if(o.getDate().equals(s)) {
		        		request.getSession().setAttribute("order", o);
		        		return "";
		        	}
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
			
			if(orders.isEmpty()) {
				return "";
			}
		
		}else {
			return "no user";
		}
		return "";
	}
	
	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String edit(@Context HttpServletRequest request, HashMap<String,String> map) {
		//System.out.println(map.get("byer"));
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			Order o = (Order)request.getSession().getAttribute("order");
			for (String key : map.keySet()) {
				if(key.startsWith("quantity")) {
					String article = key.substring(8);
					String count = map.get(key);
					for(int i = 0; i < o.getArticles().size(); i++) {
						if(o.getArticles().get(i).getName().equals(article)) {
							o.getCounter().set(i, Integer.parseInt(count));
						}
					}
				}
			}
			int spentmoney = 0;
			for(int i = 0; i < o.getArticles().size(); i++) {
				spentmoney += o.getArticles().get(i).getPrice() * o.getCounter().get(i);
			}
			o.setByer(map.get("byer"));
			o.setNote(map.get("about"));
			o.setDeliverer(map.get("deliverer"));
			o.setState(map.get("state"));
			o.setPrice(spentmoney);
			reloadOrders(o);
		}else {
			return "no user";
		}
		return "";
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(@Context HttpServletRequest request, String s) {
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			ArrayList<Order> orders = new ArrayList<Order>();
			try {
				
		        FileReader fileReader = new FileReader("orders.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);  
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufferedReader.readLine();
		        ObjectMapper mapper = new ObjectMapper();
		        
		        while (line != null) {
		        	Order o = mapper.readValue(line, Order.class);
		        	if(o.getDate().equals(s)) {
		        		o.setActive(false);
		        		o.setState("Canceled");
		        	}
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
			
			if(orders.isEmpty()) {
				return "";
			}else {
				PrintWriter out;
				try {
					out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", false)));
					out.print("");
					out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)));
					for(int k = 0; k < orders.size(); k++) {
						out.println(orders.get(k));	
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
	
	@GET
	@Path("/getEditOrders")
	public String getEditOrders(@Context HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			
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
			if(orders.isEmpty()) {
				return "";
			}else {
				ObjectMapper mapper = new ObjectMapper();
				try {
					String json = mapper.writeValueAsString(orders);
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
	@Path("/getDelivery")
	public String getDelivery(@Context HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			Order order = null;
			try {
				
		        FileReader fileReader = new FileReader("orders.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);  
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufferedReader.readLine();
		        ObjectMapper mapper = new ObjectMapper();
		        
		        while (line != null) {
		        	Order o = mapper.readValue(line, Order.class);
		        	if(o.getDeliverer()==null) {
		        		 line = bufferedReader.readLine();
		        		continue;
		        	}
		        	if(o.getDeliverer().equals(u.getUsername()) && o.getState().equals("Delivery in progress")) {
		        		order = o;
		        		break;
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
			System.out.println(order);
			if(order == null) {
				return "";
			}else {
				ObjectMapper mapper = new ObjectMapper();
				try {
					String json = mapper.writeValueAsString(order);
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
	@Path("/takeOrder")
	public String takeOrder(@Context HttpServletRequest request,String s) {
		s = s.substring(1, s.length()-1);
		
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			
			ArrayList<Order> orders = new ArrayList<Order>();
			try {
				
		        FileReader fileReader = new FileReader("orders.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);  
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufferedReader.readLine();
		        ObjectMapper mapper = new ObjectMapper();
		        
		        while (line != null) {
		        	Order o = mapper.readValue(line, Order.class);
		        	if(o.getDate().equals(s)) {
		        		o.setDeliverer(u.getUsername());
		        		o.setState("Delivery in progress");
		        	}
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
			if(orders.isEmpty()) {
				return "";
			}else {
				PrintWriter out;
				try {
					out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", false)));
					out.print("");
					out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)));
					for(int k = 0; k < orders.size(); k++) {
						out.println(orders.get(k));	
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
	@Path("/deliverOrder")
	public String deliverOrder(@Context HttpServletRequest request,String s) {
		s = s.substring(1, s.length()-1);
		
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			
			ArrayList<Order> orders = new ArrayList<Order>();
			try {
				
		        FileReader fileReader = new FileReader("orders.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);  
		        
		        StringBuilder sb = new StringBuilder();
		        String line = bufferedReader.readLine();
		        ObjectMapper mapper = new ObjectMapper();
		        
		        while (line != null) {
		        	Order o = mapper.readValue(line, Order.class);
		        	if(o.getDate().equals(s)) {
		        		o.setState("Delivered");
		        		addUserPoints(o);
		        	}
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
			if(orders.isEmpty()) {
				return "";
			}else {
				PrintWriter out;
				try {
					out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", false)));
					out.print("");
					out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)));
					for(int k = 0; k < orders.size(); k++) {
						out.println(orders.get(k));	
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
	
	private void addUserPoints(Order o) {
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
		
		int spentmoney = 0;
		for(int i = 0; i < o.getArticles().size(); i++) {
			spentmoney += o.getArticles().get(i).getPrice() * o.getCounter().get(i);
		}
//		System.out.println(spentmoney + " " + o.getByer());
		
		int points = spentmoney / 5;
		
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			
	        FileReader fileReader = new FileReader("users.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	User user = mapper.readValue(line, User.class);
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
		
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(o.getByer())) {
				User user = users.get(i);
				if(user.getPoints() + points > 10) {
					user.setPoints(10);
				}else {
					user.setPoints(user.getPoints() + points);
				}
				reloadUsers(user);
			}
		}
	}

	@POST
	@Path("/confirmOrder")
	public String confirmOrder(@Context HttpServletRequest request,String[] str) {
		String s = str[0];//.substring(1, str[0].length()-1);
		String p = str[1];//.substring(1, str[1].length()-1);
		
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
//				System.out.println(articlesInCart);
				ArrayList<Article> orderedArticles = new ArrayList<Article>();
				ArrayList<Integer> counter = new ArrayList<Integer>();
				for (Article key : articlesInCart.keySet()) {
					orderedArticles.add(key);
					counter.add(articlesInCart.get(key));
				}
//				System.out.println(orderedArticles);
//				System.out.println(counter);
				int spentmoney = 0;
				for(int i = 0; i < orderedArticles.size(); i++) {
					spentmoney += orderedArticles.get(i).getPrice() * counter.get(i);
				}
				int temp;
				try {
					temp = Integer.parseInt(p);
				}catch (Exception e) {
					temp = 0;
				}
				System.out.println(p + " " +temp);
				Order o = new Order(orderedArticles,counter, u.getUsername(), s,spentmoney,temp);
				u.getCart().clear();
				u.getOrders().add(o.getDate().toString());
				u.setPoints(u.getPoints()-temp);
				reloadUsers(u);
				PrintWriter out;
				try {
					out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)));
					out.println(o);	
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
	@Path("/confirmAdminOrder")
	public String confirmAdminOrder(@Context HttpServletRequest request,String str) {
		String s = str.substring(1, str.length()-1);
		
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
				ArrayList<Article> orderedArticles = new ArrayList<Article>();
				ArrayList<Integer> counter = new ArrayList<Integer>();
				for (Article key : articlesInCart.keySet()) {
					orderedArticles.add(key);
					counter.add(articlesInCart.get(key));
				}
				
				int spentmoney = 0;
				for(int i = 0; i < orderedArticles.size(); i++) {
					spentmoney += orderedArticles.get(i).getPrice() * counter.get(i);
				}
				Order o = new Order(orderedArticles,counter, "/", s,spentmoney,0);
				u.getCart().clear();
				u.getOrders().add(o.getDate().toString());
				reloadUsers(u);
				PrintWriter out;
				try {
					out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)));
					out.println(o);	
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
	
	public String reloadOrders(Order order) {
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			
	        FileReader fileReader = new FileReader("orders.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	Order o = mapper.readValue(line, Order.class);
	        	if(o.getDate().equals(order.getDate())) {
	        		o = order;
	        	}
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
		if(orders.isEmpty()) {
			return "";
		}else {
			PrintWriter out;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", false)));
				out.print("");
				out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)));
				for(int k = 0; k < orders.size(); k++) {
					out.println(orders.get(k));	
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
