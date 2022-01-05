package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.LingoApplication;
import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;
import cz.cvut.fel.ear.lingo.model.Repo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackageClasses = LingoApplication.class)
public class RepoDaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private RepoDao dao;

//    @Test
//    public void findAllFlashcardInRepository_existentRepositoryWithFlashcards_getsFlashcardList() {
//        final Repo repo = repoGenerator();
//        final List<Flashcard> flashcards = repo.getFlashcards();
//        em.persistAndFlush(repo);
//
//        List<Flashcard> result = dao.findAllFlashcardInRepository(repo);
//
//        assertEquals(flashcards, result);
//    }
}
