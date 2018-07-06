package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.tomcat.jni.Buffer;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import beans.User;

@Path("/users")
public class UserService {

	@GET
	@Path("/test")
	public String test() {
		System.out.println("REST");
		return "REST is working.";
	}
	
	@GET
	@Path("/admin")
	public String admin(@Context HttpServletRequest request) {
		
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
		
		if(users.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(users);
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
	@Path("/getDeliverers")
	public String getDeliverers(@Context HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			
	        FileReader fileReader = new FileReader("users.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	User user = mapper.readValue(line, User.class);
	        	if(user.getRole().equals("deliverer")) {
	        		users.add(user);
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
		
		if(users.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(users);
				return json;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR3");
			}
		}
		System.out.println("no");
		return "";
		}else {
			return "no user";
		}
	}
	
	@GET
	@Path("/getByers")
	public String getByers(@Context HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			
	        FileReader fileReader = new FileReader("users.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	User user = mapper.readValue(line, User.class);
	        	if(user.getRole().equals("byer")) {
	        		users.add(user);
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
		
		if(users.isEmpty()) {
			System.out.println("no");
			return "";
		}else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(users);
				return json;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR3");
			}
		}
		System.out.println("no");
		}else {
			return "no user";
		}
		return "";
	}
	
	@GET
	@Path("/isloggedin")
	public String isloggedin(@Context HttpServletRequest request) {
		
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(u);
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
	@Path("/getname")
	public String getName(@Context HttpServletRequest request) {
		
		User u = (User) request.getSession().getAttribute("user");
		if(u != null) {
			return u.getUsername();
		}else {
			return "nema";
		}
	}
	
	@POST
	@Path("/logout")
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@Context HttpServletRequest request,User u) {
		
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
	        
	        //users = mapper.readValue(str, User[].class);
	        for(int i = 0; i < users.size(); i++) {
	        	if(users.get(i).getUsername().equals(u.getUsername()) && users.get(i).getPassword().equals(u.getPassword())) {
	        		request.getSession().setAttribute("user", users.get(i));
	        		return "";
	        	}
	        }
	        
	        bufferedReader.close();
	        fileReader.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("ERROR");
	    	e.printStackTrace();
	    }
		
		return "Wrong username or password! Try again.";
	}
	
	@POST
	@Path("/setrole")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String setrole(@Context HttpServletRequest request,String[] s) {
		String u = s[0];
		
		ArrayList<User> users = new ArrayList<User>();
		int i = 0;
        int j = 0;
		try {
	        FileReader fileReader = new FileReader("users.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	User user = mapper.readValue(line, User.class);
	        	users.add(user);
	        	if(user.getUsername().equals(u)) {
	        		j = i;
	        	}
	            line = bufferedReader.readLine();
	            i++;
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
			users.get(j).setRole(s[1]);
			
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
	@Path("/registration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String register(@Context HttpServletRequest request,User u) {
		u.setRole("byer");
		u.setRegisteration_date(new Date());
		try {
			if(u.getUsername().equals("") || u.getPassword().equals("") || u.getName().equals("") || u.getSurname().equals("") || u.getEmail().equals("") || u.getPhone_number().equals("") || u.getRole().equals("")) {
				return "Fill all fields!";
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("NULL");
			return "";
		}
		
		
		boolean found = false;
		try {
	        FileReader fileReader = new FileReader("users.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        
	        StringBuilder sb = new StringBuilder();
	        String line = bufferedReader.readLine();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        while (line != null) {
	        	User user = mapper.readValue(line, User.class);
	        	if(user.getUsername().equals(u.getUsername())) {
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
	        	File f = new File("users.txt");
	
		    	//f.getParentFile().mkdirs(); 
		    	f.createNewFile();

	    	}catch (Exception e1) {
	    		e1.printStackTrace();
	    		System.out.println("ERROR1");
			}
		}
		if(found == false) {
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("users.txt", true)));
			    out.println(u);
			    out.close();
			}catch (Exception e) {
				e.printStackTrace();
	    		System.out.println("ERROR2");
			}
		}else {
			return "Username taken! Please choose another one.";
		}
		
		return "";		
	}
}
