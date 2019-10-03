package com.poc.authenticate;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

@Service
public class CheckLogin {
	
	/**
	 * Check if user is logged in
	 * @return
	 * @throws IOException 
	 */
	public boolean isUserLoggedIn(HttpServletRequest request) throws IOException {
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
//		Authentication authentication = getContext().getAuthentication();
		if(securityContext != null) {
	    	Authentication authentication =  securityContext.getAuthentication();
	    	if(null != authentication && authentication.isAuthenticated() != true && !authentication.getAuthorities().equals("ROLE_ADMIN")) 
	    		return false;
			else 
				return true;
	    } else {
	    	return false;
	    }
	}
	
}
