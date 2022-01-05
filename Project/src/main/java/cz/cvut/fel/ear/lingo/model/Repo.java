package cz.cvut.fel.ear.lingo.model;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractClass;
import cz.cvut.fel.ear.lingo.model.util.Views;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
public class Repo extends AbstractClass {

    @ManyToMany
    @OrderBy("name")
    @JsonView(Views.Public.class)
    private List<FlashcardDeck> flashcardDecks;

    @ManyToMany
    @JsonView(Views.Public.class)
    private List<Flashcard> flashcards;

    public Repo() {
        this.flashcardDecks = new ArrayList<>();
        this.flashcards = new ArrayList<>();
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public List<FlashcardDeck> getFlashcardDecks() {
        return flashcardDecks;
    }

    public void addFlashcardDeck(FlashcardDeck toAdd) {
        flashcardDecks.add(toAdd);
        if(toAdd.getFlashcards().size() != 0){
            for (Flashcard flashcard : toAdd.getFlashcards()) {
                addFlashcard(flashcard);
            }
        }
    }

    public void removeFlashcardDeck(FlashcardDeck flashcardDeck) {
        if(flashcardDecks != null)
            flashcardDecks.removeIf(c -> Objects.equals(c.getId(), flashcardDeck.getId()));
    }

    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    public void removeFlashcard(Flashcard flashcard) {
        if(flashcards != null)
            flashcards.removeIf(c -> Objects.equals(c.getId(), flashcard.getId()));
    }

    public boolean contains(Flashcard flashcard) {
        final Iterator<Flashcard> it = flashcards.iterator();
        while (it.hasNext()) {
            final Flashcard curr = it.next();
            if (Objects.equals(flashcard.getId(), curr.getId()))
                return true;
        }
        return false;
    }

    public boolean contains(FlashcardDeck flashcardDeck) {
        final Iterator<FlashcardDeck> it = flashcardDecks.iterator();
        while (it.hasNext()) {
            final FlashcardDeck curr = it.next();
            if (Objects.equals(flashcardDeck.getId(), curr.getId()))
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repo repo = (Repo) o;
        if (!Objects.equals(flashcardDecks, repo.flashcardDecks))
            return false;
        return Objects.equals(flashcards, repo.flashcards);
    }

    @Override
    public int hashCode() {
        int result = flashcardDecks != null ? flashcardDecks.hashCode() : 0;
        result = 31 * result + (flashcards != null ? flashcards.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Repo{" +
                "flashcardDecks=" + flashcardDecks +
                ", flashcards=" + flashcards +
                '}';
    }
}