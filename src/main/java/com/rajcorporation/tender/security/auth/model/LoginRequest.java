/**
 * 
 */
package com.rajcorporation.tender.security.auth.model;

import lombok.Data;

/**
 * @author Karan
 *
 */
@Data
public class LoginRequest {
	String username;
	String password;
}
