package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.Generator;
import cz.cvut.fel.ear.lingo.LingoApplication;
import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;
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
public class BaseDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private FlashcardDeckDao dao;

    @Test
    public void persist_flashcardDeck_getsInstance() {
        final FlashcardDeck flashcardDeck = Generator.generateFlashcardDeck();

        dao.persist(flashcardDeck);
        assertNotNull(flashcardDeck.getId());

        final FlashcardDeck result = em.find(FlashcardDeck.class, flashcardDeck.getId());
        assertNotNull(result);
        assertEquals(flashcardDeck, result);
    }

    @Test
    public void find_flashcardDeckId_getsInstance() {
        final FlashcardDeck flashcardDeck = Generator.generateFlashcardDeck();

        em.persist(flashcardDeck);
        assertNotNull(flashcardDeck.getId());

        final FlashcardDeck result = dao.find(flashcardDeck.getId());
        assertNotNull(result);
        assertEquals(flashcardDeck, result);
    }

    //  wop = without parameters
    @Test
    public void findAll_wop_getsAllInstances() {
        final int limit = Generator.randomInt(10);
        List<FlashcardDeck> flashcardDecks = new ArrayList<>();
        for(int i = 0; i < limit; i ++) {
            FlashcardDeck temp = Generator.generateFlashcardDeck();
            flashcardDecks.add(temp);
            em.persist(temp);
        }

        final List<FlashcardDeck> result = dao.findAll();
        assertNotNull(result);
        assertEquals(limit, result.size());
        assertEquals(flashcardDecks, result);
    }

    @Test
    public void update_existingFlashcardDecl_getsUpdatedInstance() {
        final FlashcardDeck flashcardDeck = Generator.generateFlashcardDeck();
        em.persist(flashcardDeck);

        flashcardDeck.setName("new name");
        flashcardDeck.setDescription("new description");
        flashcardDeck.setRemoved(true);
        dao.update(flashcardDeck);

        final FlashcardDeck result = em.find(FlashcardDeck.class, flashcardDeck.getId());
        assertNotNull(result);
        assertEquals(flashcardDeck, result);
    }

    @Test
    public void remove_existingFlashcardDeck_cantGetInstance() {
        final FlashcardDeck flashcardDeck = new FlashcardDeck(
                "flashcard deck name",
                "flashcard deck description",
                new User()
        );
        em.persist(flashcardDeck);
        assertEquals(em.find(FlashcardDeck.class, flashcardDeck.getId()), flashcardDeck);

        dao.remove(flashcardDeck);
        assertNull(em.find(Flashcard.class, flashcardDeck.getId()));
    }

    @Test
    public void find_nonexistentFlashcardDeck_returnsNull() {
        final FlashcardDeck flashcardDeck = Generator.generateFlashcardDeck();
        flashcardDeck.setId(Generator.randomLong());

        assertThrows(PersistenceException.class, () -> dao.find(flashcardDeck.getId()));
    }

    @Test
    public void upgrade_nonexistentFlashcardDeck_instancePersists() {
        final FlashcardDeck flashcardDeck = Generator.generateFlashcardDeck();
        flashcardDeck.setId(Generator.randomLong());

        dao.update(flashcardDeck);

        assertEquals(flashcardDeck, em.find(FlashcardDeck.class, flashcardDeck.getId()));
    }

    @Test
    public void exist_existingFlashcardDeck_true() {
        final FlashcardDeck flashcardDeck = Generator.generateFlashcardDeck();
        dao.persist(flashcardDeck);

        assertTrue(dao.exist(flashcardDeck));
    }
}

