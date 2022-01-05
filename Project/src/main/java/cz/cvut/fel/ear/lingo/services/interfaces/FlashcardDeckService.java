package cz.cvut.fel.ear.lingo.services.interfaces;

import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;

import java.util.List;

public interface FlashcardDeckService {

    void persist(FlashcardDeck flashcardDeck);

    void remove(FlashcardDeck flashcardDeck);

    void restore(FlashcardDeck flashcardDeck);

    void update(FlashcardDeck originDeck, FlashcardDeck flashcardDeck);

    void addFlashcard(FlashcardDeck flashcardDeck, Flashcard flashcard);

    void removeFlashcard(FlashcardDeck flashcardDeck, Flashcard flashcard);

    List<FlashcardDeck> findAll();

    FlashcardDeck findById(Long deck);

    FlashcardDeck find(Long deck);
}