package cz.cvut.fel.ear.lingo.model;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractClass;
import cz.cvut.fel.ear.lingo.model.util.Views;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Statistic extends AbstractClass {

    @ElementCollection
    @JsonView(Views.Public.class)
    private List<String> achievements;

    @OneToMany
    @JsonView(Views.Public.class)
    private List<FlashcardProgress> flashcardProgresses;

    public Statistic() {
        this.achievements = new ArrayList<>();
    }

    public List<FlashcardProgress> getProgressesOfDeck(FlashcardDeck deck) {
        List<FlashcardProgress> progresses = new ArrayList<>();
        for(Flashcard f : deck.getFlashcards()) {
            if(contains(f)) {
                progresses.add(flashcardProgresses.stream()
                        .filter(v -> v.getFlashcard().equals(f))
                        .findFirst()
                        .get());
            } else {
                progresses.add(new FlashcardProgress(f));
            }
        }

        return progresses;
    }

    public Boolean contains(Flashcard flashcard) {
        return flashcardProgresses.stream().map(FlashcardProgress::getFlashcard).anyMatch(v -> v.equals(flashcard));
    }

    public void addFlashcard(Flashcard flashcard) {
        flashcardProgresses.add(
                new FlashcardProgress(flashcard)
        );
    }

    public void removeFlashcard(Flashcard flashcard) {
        int i = -1;
        for (FlashcardProgress fp : flashcardProgresses) {
            if(fp.getFlashcard().equals(flashcard)) {
                i = flashcardProgresses.indexOf(fp);
            }
        }
        if(i != -1) {
            flashcardProgresses.remove(i);
        }
    }

    public List<String> getAchievements(){
        return achievements;
    }

    public List<FlashcardProgress> getFlashcardProgresses() {
        return flashcardProgresses;
    }

    public void addAchievements(String toAdd){
        Objects.requireNonNull(toAdd);
        if (achievements == null)
            achievements = new ArrayList<>();
        achievements.add(toAdd);
    }

    public void removeAchievements(String toDelete){
        Objects.requireNonNull(toDelete);
        if(achievements == null)
            return;
        achievements.remove(toDelete);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        if (!Objects.equals(achievements, statistic.achievements))
            return false;
        return Objects.equals(flashcardProgresses, statistic.flashcardProgresses);
    }

    @Override
    public int hashCode() {
        int result = achievements != null ? achievements.hashCode() : 0;
        result = 31 * result + (flashcardProgresses != null ? flashcardProgresses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "achievements=" + achievements +
                ", flashcardProgresses=" + flashcardProgresses +
                '}';
    }
}
