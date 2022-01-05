package cz.cvut.fel.ear.lingo.services.interfaces;

import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;
import cz.cvut.fel.ear.lingo.model.Repo;

import java.util.List;

public interface RepoService {

    void addFlashcardDeck(Repo repo, FlashcardDeck flashcardDeck);

    void removeFlashcardDeck(Repo repo, FlashcardDeck flashcardDeck);

    void addFlashcard(Repo repo, Flashcard flashcard);

    void removeFlashcard(Repo repo, Flashcard flashcard);

    List<Repo> findAll();

    Repo findById(long id);
}