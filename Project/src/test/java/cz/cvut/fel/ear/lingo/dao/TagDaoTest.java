package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.Generator;
import cz.cvut.fel.ear.lingo.LingoApplication;
import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.Tag;
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
public class TagDaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private TagDao dao;

    @Test
    public void find_removedTag_throwsException() {
        final Tag tag = Generator.generateTag();
        dao.persist(tag);

        assertThrows(PersistenceException.class, () -> dao.find(tag.getId()));
    }

    @Test
    public void find_existentTagId_getTag() {
        final Tag tag = Generator.generateTag();
        em.persist(tag);

        final Tag result = dao.find(tag.getId());

        assertEquals(tag, result);
    }

    @Test
    public void findByName_existentTagsName_getsTag() {
        final Tag tag = Generator.generateTag();
        em.persist(tag);

        final Tag result = dao.findByName(tag.getName());

        assertEquals(tag, result);
    }

    @Test
    public void remove_existentTag_tagIsRemoved() {
        final Tag tag = Generator.generateTag();
        em.persist(tag);

        dao.remove(tag);
    }
}
