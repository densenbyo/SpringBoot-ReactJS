/*
package cz.cvut.fel.ear.lingo.services;

import cz.cvut.fel.ear.lingo.Generator;
import cz.cvut.fel.ear.lingo.dao.FlashcardDeckDao;
import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;
import cz.cvut.fel.ear.lingo.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class FlashcardDeckServiceImplTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private FlashcardDeckServiceImpl service;

    private User user;

    private FlashcardDeck flashcardDeck;

    @BeforeEach
    public void setUp() {
        flashcardDeck = Generator.generateFlashcardDeck();
        user = Generator.generateUser();
        flashcardDeck.setCreator(user);
        em.persist(flashcardDeck);
        em.persist(user);
    }

    @AfterEach
    public void cleaner() {
        em.remove(user);
        em.remove(flashcardDeck);
    }

    @Test
    public void addFlashcard_flashcard_flashcardFindsInDeck() {
        final Flashcard flashcard = Generator.generateFlashcard(user);
        em.persist(flashcard);

        service.addFlashcard(flashcardDeck, flashcard);
        final List<Flashcard> result = em.find(
                FlashcardDeck.class, flashcardDeck.getId()
        ).getFlashcards();

        assertTrue(result.contains(flashcard));
    }

    @Test
    public void removeFlashcard_existentFlashcard_cantFindFlashcardInDeck() {
        final Flashcard flashcard = Generator.generateFlashcard(user);
        flashcardDeck.addCard(flashcard);
        em.merge(flashcardDeck);

        service.removeFlashcard(flashcardDeck, flashcard);
        final List<Flashcard> result = em.find(
                FlashcardDeck.class, flashcardDeck.getId()
        ).getFlashcards();

        assertFalse(result.contains(flashcard));
    }

}
*/
