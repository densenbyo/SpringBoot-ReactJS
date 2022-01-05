package cz.cvut.fel.ear.lingo.model;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractClass;
import cz.cvut.fel.ear.lingo.model.util.Views;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class FlashcardProgress extends AbstractClass {

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private Double progressDegree;

    @ManyToOne
    @JsonView(Views.Public.class)
    private Flashcard flashcard;

    public FlashcardProgress() {
        progressDegree = 0.;
    }

    public FlashcardProgress(Flashcard flashcard) {
        this.flashcard = flashcard;
        this.progressDegree = 0.;
    }

    public Flashcard getFlashcard() {
        return flashcard;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    public void setProgressDegree(Double progressDegree) {
        this.progressDegree = progressDegree;
    }

    public Double getProgressDegree() {
        return progressDegree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlashcardProgress that = (FlashcardProgress) o;
        if (!Objects.equals(progressDegree, that.progressDegree))
            return false;
        return Objects.equals(flashcard, that.flashcard);
    }

    @Override
    public int hashCode() {
        int result = progressDegree != null ? progressDegree.hashCode() : 0;
        result = 31 * result + (flashcard != null ? flashcard.hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        return "FlashcardProgress{" +
                "progressDegree=" + progressDegree +
                ", flashcard=" + flashcard +
                '}';
    }
}