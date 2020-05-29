package com.example.demo.controller;


import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("api/public")
public class PublicRestApiController {
	
	
	private UserRepository userRepository;
	
	

    public PublicRestApiController(UserRepository userRepository){
    	this.userRepository = userRepository;
    }

    @GetMapping("test1")
    public String test1(){
    	
    	Authentication auth =         SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName();
    	String userName = auth.toString();
    	String sPrincipal = auth.getPrincipal().toString();
    	return "API Test 1 "+name+" userName "+userName+" sPrincipal "+sPrincipal;
    }

    @GetMapping("test2")
    public String test2(){
        return "API Test 2";
    }
    
    @GetMapping("users")
    public List<User> users(){
    	
        return this.userRepository.findAll();
    }
    
    @GetMapping("user")
    public Principal user(Principal user){
    	
        return user;
    }
    
    @GetMapping("username")
    public String username(Principal user){
    	
    	/*JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(user.toString());
 
			// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
			JSONObject jsonObject = (JSONObject) obj;
 
			// A JSON array. JSONObject supports java.util.List interface.
			JSONArray sUserName = (JSONArray) jsonObject.get("details");
 
			// An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
			// Iterators differ from enumerations in two ways:
			// 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
			// 2. Method names have been improved.
			Iterator<JSONObject> iterator = sUserName.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
				if(iterator.next().containsKey("name")) {
					System.out.println(iterator.next().values().toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	*/
    	
    	
    	
    	return user.getName();
    }

}
