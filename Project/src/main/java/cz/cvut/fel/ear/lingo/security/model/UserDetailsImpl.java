package cz.cvut.fel.ear.lingo.security.model;

import cz.cvut.fel.ear.lingo.model.Repo;
import cz.cvut.fel.ear.lingo.model.User;
import cz.cvut.fel.ear.lingo.model.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsImpl implements UserDetails {

    private User user;
    private final Set<GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        Objects.requireNonNull(user);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
    }

    public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(authorities);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
        this.authorities.addAll(authorities);
    }

    private void addUserRole() {
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

    public void eraseCredentials() {
        user.erasePassword();
    }

    public Long getId() {
        return user.getId();
    }

    public UserRole getRole(){
        return user.getRole();
    }

    public String getRepoId(){
        return user.getRepository().getId().toString();
    }
    public Repo getRepo(){
        return user.getRepository();
    }

    public List<String> getDetails(){
        List<String> list = new ArrayList<>();
        list.add(user.getUsername());
        list.add(user.getId().toString());
        list.add(user.getRole().toString());
        return list;
    }
}
