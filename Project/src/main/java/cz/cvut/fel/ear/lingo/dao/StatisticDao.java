package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.model.Statistic;
import org.springframework.stereotype.Repository;

@Repository
public class StatisticDao extends BaseDao<Statistic> {

    public StatisticDao() {
        super(Statistic.class);
    }
}