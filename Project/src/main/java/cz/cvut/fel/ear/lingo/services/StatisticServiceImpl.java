package cz.cvut.fel.ear.lingo.services;

import cz.cvut.fel.ear.lingo.dao.UserDao;
import cz.cvut.fel.ear.lingo.model.Statistic;
import cz.cvut.fel.ear.lingo.model.User;
import cz.cvut.fel.ear.lingo.services.interfaces.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final UserDao userDao;

    @Autowired
    public StatisticServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void clearStatistic(Statistic statistic) {
        Objects.requireNonNull(statistic);
        for (User user: userDao.findAll()) {
            if (user.getStatistic().getId().equals(statistic.getId())) {
                user.getStatistic().getAchievements().clear();
                user.getStatistic().getFlashcardProgresses().clear();
                userDao.update(user);
            }
        }
    }

    @Override
    @Transactional
    public void removeAchievements(User user, String achievements) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(achievements);
        user.getStatistic().removeAchievements(achievements);
        userDao.update(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Statistic> findAll() {
        List<User> users = userDao.findAll();
        return users.stream().map(User::getStatistic).collect(Collectors.toList());
    }
}