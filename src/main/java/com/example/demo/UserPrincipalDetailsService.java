/**
 * 
 */
package com.example.demo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

/**
 * @author Harshesh
 *
 */
@Service
public class UserPrincipalDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	public UserPrincipalDetailsService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		User user = this.userRepository.findByUsername(username);
		UserPrincipal userPrinciple = new UserPrincipal(user);
		
		return userPrinciple;
	}

}
