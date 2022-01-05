package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.model.Repo;
import org.springframework.stereotype.Repository;

@Repository
public class RepoDao extends BaseDao<Repo> {

    protected RepoDao() {
        super(Repo.class);
    }
}