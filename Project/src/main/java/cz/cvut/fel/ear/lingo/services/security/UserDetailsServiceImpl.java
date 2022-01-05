package cz.cvut.fel.ear.lingo.services.security;

import cz.cvut.fel.ear.lingo.dao.UserDao;
import cz.cvut.fel.ear.lingo.model.User;
import cz.cvut.fel.ear.lingo.security.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userDao.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        return new UserDetailsImpl(user);
    }
}