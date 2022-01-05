package cz.cvut.fel.ear.lingo.services;

import cz.cvut.fel.ear.lingo.dao.FlashcardDao;
import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractContent;
import cz.cvut.fel.ear.lingo.services.interfaces.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class  FlashcardServiceImpl implements FlashcardService {

    private final FlashcardDao flashcardDao;

    @Autowired
    public FlashcardServiceImpl(FlashcardDao flashcardDao) {
        this.flashcardDao = flashcardDao;
    }

    @Override
    @Transactional
    public void persist(Flashcard flashcard) {
        Objects.requireNonNull(flashcard);
        flashcardDao.persist(flashcard);
    }

    @Override
    @Transactional
    public void remove(Flashcard flashcard) {
        Objects.requireNonNull(flashcard);
        flashcard.setRemoved(true);
        flashcardDao.update(flashcard);
    }

    @Override
    @Transactional
    public void restore(Flashcard flashcard) {
        Objects.requireNonNull(flashcard);
        flashcard.setRemoved(false);
        flashcardDao.update(flashcard);
    }

    @Override
    @Transactional
    public void update(Flashcard originalFlashcard, Flashcard flashcard) {
        Objects.requireNonNull(flashcard);
        originalFlashcard.setWord(flashcard.getWord());
        originalFlashcard.setTranslation(flashcard.getTranslation());
        originalFlashcard.setCreator(originalFlashcard.getCreator());
        flashcardDao.update(originalFlashcard);
    }

    @Override
    @Transactional
    public void addContent(Long id, AbstractContent abstractContent) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(abstractContent);
        Flashcard card = flashcardDao.find(id);
        card.addContent(abstractContent);
        flashcardDao.update(card);
    }

    @Override
    @Transactional
    public void removeContent(Long id, AbstractContent abstractContent) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(abstractContent);
        Flashcard card = flashcardDao.find(id);
        if(!card.getContents().contains(abstractContent)) card.removeContent(abstractContent);
        flashcardDao.update(card);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flashcard> findAll() {
        return flashcardDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flashcard> findCardsInPublicDeck() {
        return flashcardDao.findCardsInPublicDecks();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flashcard> findByWord(String word) {
        return flashcardDao.findByWord(word);
    }

    @Override
    @Transactional(readOnly = true)
    public Flashcard findById(Long id){
        return flashcardDao.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Flashcard find(Long id) {
        return flashcardDao.find(id);
    }
}