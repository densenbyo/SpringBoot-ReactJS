package cz.cvut.fel.ear.lingo.services.interfaces;

import cz.cvut.fel.ear.lingo.model.Statistic;
import cz.cvut.fel.ear.lingo.model.User;

import java.util.List;

public interface StatisticService {

    void clearStatistic(Statistic statistic);

    void removeAchievements(User user, String achievements);

    List<Statistic> findAll();
}