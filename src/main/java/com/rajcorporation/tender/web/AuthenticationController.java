package com.rajcorporation.tender.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rajcorporation.tender.security.TokenHelper;
import com.rajcorporation.tender.security.auth.model.UserTokenState;


@RestController
@RequestMapping( value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE )
public class AuthenticationController {

    @Autowired
    TokenHelper tokenHelper;

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {

        String authToken = tokenHelper.getToken( request );
        if (authToken != null && tokenHelper.canTokenBeRefreshed(authToken)) {
            // TODO check user password last update
            String refreshedToken = tokenHelper.refreshToken(authToken);

            Cookie authCookie = new Cookie( TOKEN_COOKIE, ( refreshedToken ) );
            authCookie.setPath( "/tender" );
            authCookie.setHttpOnly( true );
            authCookie.setMaxAge( EXPIRES_IN );
            // Add cookie to response
            response.addCookie( authCookie );

            UserTokenState userTokenState = new UserTokenState(refreshedToken, EXPIRES_IN);
            return ResponseEntity.ok(userTokenState);
        } else {
            UserTokenState userTokenState = new UserTokenState();
           return ResponseEntity.accepted().body(userTokenState);
        }
    }
}
