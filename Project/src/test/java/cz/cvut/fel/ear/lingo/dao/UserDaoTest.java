package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.Generator;
import cz.cvut.fel.ear.lingo.LingoApplication;
import cz.cvut.fel.ear.lingo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackageClasses = LingoApplication.class)
public class UserDaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserDao dao;

    @Test
    public void findAll_wop_getsUserList() {
        final User user1 = Generator.generateUser();
        final User user2 = Generator.generateUser();
        final User user3 = Generator.generateUser();
        user1.setRemoved(true);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.flush();
        final List<User> users = new ArrayList<>();
        users.add(user2);
        users.add(user3);

        final List<User> result = dao.findAll();

        assertEquals(users, result);
    }

    @Test
    public void findByMail_existentUsersMail_getsInstance() {
        final User user = Generator.generateUser();
        em.persist(user);

        final User result = dao.findByMail(user.getMail());

        assertEquals(user, result);
    }

    @Test
    public void findByMail_nonexistentUsersMail_throwsException() {
        assertThrows(PersistenceException.class, () -> dao.findByMail("LOLOLOLO"));
    }

    @Test
    public void remove_existentUser_userIsRemoved() {
        final User user = Generator.generateUser();
        em.persist(user);

        dao.remove(user);

        assertTrue(em.find(User.class, user.getId()).isRemoved());
    }
}
