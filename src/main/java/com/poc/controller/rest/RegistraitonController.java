package com.poc.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.poc.enums.Role;
import com.poc.model.User;
import com.poc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Account", produces="application/json")
public class RegistraitonController {
	
	@Autowired
	UserService userService;
	
	@ApiOperation(value = "Admin Signup", notes = "Admin Signup")
	@RequestMapping(value = "/api/adminSignUp", method=RequestMethod.POST)
	public void adminSignUp() {
		User user = new User();
		user.setFirstName("parth");
		user.setLastName("solanki");
		user.setEmail("adminstrator@poc.io");
		user.setRole(Role.ROLE_ADMIN);
		user.setPassword("123456");
		userService.saveAdminUser(user);
	}
}
