/**
 * 
 */
package com.rajcorporation.tender.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rajcorporation.tender.security.auth.model.User;
import com.rajcorporation.tender.service.UserService;

/**
 * @author Karan
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("/addAuthority")
	public ResponseEntity<User> addAuthorityToTheUser(@RequestParam("id") Long id,
			@RequestParam("authorityId") Long authorityId) {
		return ResponseEntity.ok(service.addAuthority(id, authorityId));
	}

}
