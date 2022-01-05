package cz.cvut.fel.ear.lingo.services.interfaces;

import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractContent;

import java.util.List;

public interface FlashcardService {

    void persist(Flashcard flashcard);

    void remove(Flashcard flashcard);

    void restore(Flashcard flashcard);

    void update(Flashcard originalFlashcard, Flashcard flashcard);

    void addContent(Long id, AbstractContent abstractContent);

    void removeContent(Long id, AbstractContent abstractContent);

    List<Flashcard> findAll();

    List<Flashcard> findCardsInPublicDeck();

    List<Flashcard> findByWord(String word);

    Flashcard findById(Long id);

    Flashcard find(Long id);
}