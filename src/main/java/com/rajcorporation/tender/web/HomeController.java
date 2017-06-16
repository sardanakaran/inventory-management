/**
 * 
 */
package com.rajcorporation.tender.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author karan
 *
 */
@RestController
public class HomeController {
	@RequestMapping(path = "/", method = GET)
	public void index(HttpServletResponse response) {
		try {
			response.sendRedirect("/swagger-ui.html");
		} catch (IOException e) {
		}
	}

}
