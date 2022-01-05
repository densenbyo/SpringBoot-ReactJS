package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.Generator;
import cz.cvut.fel.ear.lingo.LingoApplication;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackageClasses = LingoApplication.class)
public class FlashcardDeckDaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private FlashcardDeckDao dao;

    @Test
    public void remove_existentFlashcardDeck_flashcardDeckIsRemoved() {
        final FlashcardDeck flashcardDeck = Generator.generateFlashcardDeck();
        em.persist(flashcardDeck);

        dao.remove(flashcardDeck);

        assertTrue(em.find(FlashcardDeck.class, flashcardDeck.getId()).getRemoved());
    }
}
