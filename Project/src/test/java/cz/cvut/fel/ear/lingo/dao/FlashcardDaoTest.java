/*
package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.Generator;
import cz.cvut.fel.ear.lingo.LingoApplication;
import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class FlashcardDaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private FlashcardDao dao;

    private User user;

    @BeforeEach
    public void setUp() {
        user = Generator.generateUser();
        em.persist(user);
    }

    @AfterEach
    public void cleaner() {
        em.remove(user);
    }

    @Test
    public void find_removedFlashcard_throwsException() {
        final Flashcard flashcard = Generator.generateFlashcard(user);
        flashcard.setRemoved(true);
        dao.persist(flashcard);

        assertThrows(PersistenceException.class, () -> dao.find(flashcard.getId()));
    }

    @Test
    public void findByWord_wordOfExistingFlashcards_getsFlashcardList() {
        String word = "the word";
        final Flashcard flashcard1 = Generator.generateFlashcard(user);
        final Flashcard flashcard2 = Generator.generateFlashcard(user);
        final Flashcard flashcard3 = Generator.generateFlashcard(user);
        flashcard2.setWord(word);
        flashcard3.setWord(word);
        final List<Flashcard> flashcards = new ArrayList<>();
        flashcards.add(flashcard2);
        flashcards.add(flashcard3);
        em.persist(flashcard1);
        em.persist(flashcard2);
        em.persist(flashcard3);

        final List<Flashcard> result = dao.findByWord(word);

        assertEquals(flashcards, result);
    }

    @Test
    public void findAll_wop_getsFlashcardList() {
        final Flashcard flashcard1 = Generator.generateFlashcard(user);
        final Flashcard flashcard2 = Generator.generateFlashcard(user);
        final Flashcard flashcard3 = Generator.generateFlashcard(user);
        flashcard1.setRemoved(true);
        em.persist(flashcard1);
        em.persist(flashcard2);
        em.persist(flashcard3);
        final List<Flashcard> flashcards = new ArrayList<>();
        flashcards.add(flashcard2);
        flashcards.add(flashcard3);

        final List<Flashcard> result = dao.findAll();

        assertEquals(flashcards, result);

    }

    @Test
    public void remove_existentFlashcard_flashcardIsRemoved() {
        final Flashcard flashcard = Generator.generateFlashcard(user);
        em.persist(flashcard);

        dao.remove(flashcard);

        assertTrue(em.find(Flashcard.class, flashcard.getId()).isRemoved());
    }

    @Test
    public void find_existentFlashcardId_getsFlashcard() {
        final Flashcard flashcard = Generator.generateFlashcard(user);
        em.persist(flashcard);

        final Flashcard result = dao.find(flashcard.getId());

        assertEquals(flashcard, result);
    }

}
*/
