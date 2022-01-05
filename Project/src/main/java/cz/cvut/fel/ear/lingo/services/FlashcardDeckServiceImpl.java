package cz.cvut.fel.ear.lingo.services;

import cz.cvut.fel.ear.lingo.dao.FlashcardDeckDao;
import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;
import cz.cvut.fel.ear.lingo.services.interfaces.FlashcardDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class FlashcardDeckServiceImpl implements FlashcardDeckService {

    private final FlashcardDeckDao flashcardDeckDao;

    @Autowired
    public FlashcardDeckServiceImpl(FlashcardDeckDao flashcardDeckDao) {
        this.flashcardDeckDao = flashcardDeckDao;
    }

    @Override
    @Transactional
    public void persist(FlashcardDeck flashcardDeck) {
        Objects.requireNonNull(flashcardDeck);
        flashcardDeckDao.persist(flashcardDeck);
    }

    @Override
    @Transactional
    public void remove(FlashcardDeck flashcardDeck) {
        Objects.requireNonNull(flashcardDeck);
        flashcardDeck.setRemoved(true);
        flashcardDeckDao.update(flashcardDeck);
    }

    @Override
    @Transactional
    public void restore(FlashcardDeck flashcardDeck) {
        Objects.requireNonNull(flashcardDeck);
        flashcardDeck.setRemoved(false);
        flashcardDeckDao.update(flashcardDeck);
    }

    @Override
    @Transactional
    public void update(FlashcardDeck originDeck, FlashcardDeck flashcardDeck) {
        Objects.requireNonNull(flashcardDeck);
        if (!flashcardDeck.getRemoved()) {
            originDeck.setName(flashcardDeck.getName());
            originDeck.setDescription(flashcardDeck.getDescription());
            originDeck.setPublic(flashcardDeck.isPublic());
            flashcardDeckDao.update(originDeck);
        }
    }

    @Override
    @Transactional
    public void addFlashcard(FlashcardDeck flashcardDeck, Flashcard flashcard) {
        Objects.requireNonNull(flashcardDeck);
        Objects.requireNonNull(flashcard);
        if (!flashcardDeck.contains(flashcard)) {
            flashcardDeck.addFlashcard(flashcard);
            flashcardDeckDao.update(flashcardDeck);
        }
    }

    @Override
    @Transactional
    public void removeFlashcard(FlashcardDeck flashcardDeck, Flashcard flashcard) {
        Objects.requireNonNull(flashcardDeck);
        Objects.requireNonNull(flashcard);
        flashcardDeck.removeFlashcard(flashcard);
        flashcardDeckDao.update(flashcardDeck);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlashcardDeck> findAll() {
        return flashcardDeckDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FlashcardDeck findById(Long id){
        Objects.requireNonNull(id);
        return flashcardDeckDao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public FlashcardDeck find(Long id) {
        return flashcardDeckDao.find(id);
    }
}