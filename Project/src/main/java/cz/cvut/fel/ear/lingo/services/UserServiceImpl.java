package cz.cvut.fel.ear.lingo.services;

import cz.cvut.fel.ear.lingo.dao.StatisticDao;
import cz.cvut.fel.ear.lingo.dao.UserDao;
import cz.cvut.fel.ear.lingo.model.Statistic;
import cz.cvut.fel.ear.lingo.model.User;
import cz.cvut.fel.ear.lingo.model.enums.UserRole;
import cz.cvut.fel.ear.lingo.services.interfaces.UserService;
import cz.cvut.fel.ear.lingo.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final StatisticDao statisticDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, StatisticDao statisticDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.statisticDao = statisticDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        user.encodePassword(passwordEncoder);
        if (user.getRole() == UserRole.VIEWER)
            user.setRole(Constants.DEFAULT_ROLE);
        userDao.persist(user);
    }

    @Override
    @Transactional
    public void persist(User user, String role) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(role);
        user.encodePassword(passwordEncoder);
        if (role.equals(UserRole.ADMIN.toString()))
            user.setRole(UserRole.ADMIN);
        else
            user.setRole(Constants.DEFAULT_ROLE);
        userDao.persist(user);
    }

    @Override
    @Transactional
    public void remove(User user) {
        Objects.requireNonNull(user);
        clearStatistic(user.getStatistic());
        user.setRemoved(true);
        userDao.update(user);
    }

    @Override
    @Transactional
    public void restore(User user) {
        Objects.requireNonNull(user);
        user.setRemoved(false);
        userDao.update(user);
    }

    @Override
    @Transactional
    public void update(User originalUser, User user) {
        Objects.requireNonNull(originalUser);
        Objects.requireNonNull(user);
        originalUser.setUsername(user.getUsername());
        originalUser.setMail(user.getMail());
        originalUser.setPassword(user.getPassword());
        originalUser.encodePassword(passwordEncoder);
        userDao.update(originalUser);
    }

    @Override
    @Transactional
    public void block(User user) {
        Objects.requireNonNull(user);
        user.setActive(false);
        userDao.update(user);
    }

    @Override
    @Transactional
    public void unblock(User user) {
        Objects.requireNonNull(user);
        user.setActive(true);
        userDao.update(user);
    }

    @Override
    @Transactional
    public void setRole(String role, User user) {
        Objects.requireNonNull(role);
        Objects.requireNonNull(user);
        if (role.equals("USER")) user.setRole(UserRole.USER);
        if (role.equals("ADMIN")) user.setRole(UserRole.ADMIN);
        userDao.update(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUser() {
        return userDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersByAchievement(String achievement) {
        Objects.requireNonNull(achievement);
        return userDao.findByAchievement(achievement);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id){
        Objects.requireNonNull(id);
        return userDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User find(Long id) {
        return userDao.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String name) {
        Objects.requireNonNull(name);
        return userDao.findByUsername(name);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByMail(String mail){
        Objects.requireNonNull(mail);
        return userDao.findByMail(mail);
    }

    /**
     * Clears statistic when remove the user.
     *
     * @param statistic user's Statistic
     */
    private void clearStatistic(Statistic statistic) {
        statistic.getAchievements().clear();
        statistic.getFlashcardProgresses().clear();
        statisticDao.update(statistic);
    }
}