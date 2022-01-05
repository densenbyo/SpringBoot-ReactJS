package cz.cvut.fel.ear.lingo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.ear.lingo.model.User;
import cz.cvut.fel.ear.lingo.security.jwt.JwtAuthenticationResponse;
import cz.cvut.fel.ear.lingo.security.model.LoginStatus;
import cz.cvut.fel.ear.lingo.services.interfaces.UserService;
import cz.cvut.fel.ear.lingo.services.security.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service("oauth2authSuccessHandler")
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService service;
    private final LoginService loginService;
    private final ObjectMapper mapper;

    @Autowired
    public Oauth2AuthenticationSuccessHandler(UserService service, ObjectMapper mapper, LoginService loginService) {
        this.service = service;
        this.mapper = mapper;
        this.loginService = loginService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String username = oauthUser.getAttribute("name");
        User existUser = service.findByMail(email);
        if (existUser == null) {
            User user = new User(username, email, "");
            service.persist(user);
        }
        final LoginStatus loginStatus = new LoginStatus(true, authentication.isAuthenticated(), username, null);
        response.setStatus(HttpStatus.OK.value());
        ResponseEntity<?> entity = ResponseEntity.ok(new JwtAuthenticationResponse(loginService.login(username, "")));
        String json = new ObjectMapper().writeValueAsString(entity.getBody());
        ServletOutputStream out = response.getOutputStream();
        out.print(json);
        mapper.writeValue(out, loginStatus);
    }
}
