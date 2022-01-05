package cz.cvut.fel.ear.lingo.services.interfaces;

import cz.cvut.fel.ear.lingo.model.User;

import java.util.List;

public interface UserService {

    void persist(User user);

    void persist(User user, String role);

    void remove(User user);

    void restore(User user);

    void update(User originalUser, User user);

    void block(User user);

    void unblock(User user);

    void setRole(String role, User user);

    List<User> findAllUser();

    List<User> getUsersByAchievement(String achievement);

    User findById(Long id);

    User find(Long id);

    User findByUsername(String name);

    User findByMail(String mail);
}