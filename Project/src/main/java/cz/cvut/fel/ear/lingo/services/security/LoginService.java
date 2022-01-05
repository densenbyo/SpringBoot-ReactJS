package cz.cvut.fel.ear.lingo.services.security;

import cz.cvut.fel.ear.lingo.security.DefaultAuthenticationProvider;
import cz.cvut.fel.ear.lingo.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class
LoginService {

    private final DefaultAuthenticationProvider provider;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public LoginService(DefaultAuthenticationProvider provider, JwtTokenProvider tokenProvider) {
        this.provider = provider;
        this.tokenProvider = tokenProvider;
    }

    @Transactional(readOnly = true)
    public String login(String username, String password) {
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = provider.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }
}