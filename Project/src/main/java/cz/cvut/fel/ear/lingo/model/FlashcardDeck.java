package cz.cvut.fel.ear.lingo.model;

import com.fasterxml.jackson.annotation.*;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractClass;
import cz.cvut.fel.ear.lingo.model.util.Views;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
public class FlashcardDeck extends AbstractClass {

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private Boolean isPublic;

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private Boolean isRemoved;

    @ManyToMany
    @OrderBy("word")
    @JsonView(Views.Public.class)
    private List<Flashcard> flashcards;

    @OneToOne
    @JsonView(Views.Internal.class)
    private User creator;

    public FlashcardDeck() {
        this.isPublic = false;
        this.isRemoved = false;
    }

    public FlashcardDeck(String name, String description, User creator) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.isPublic = false;
        this.isRemoved = false;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRemoved() {
        return isRemoved;
    }

    public void setRemoved(Boolean removed) {
        isRemoved = removed;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void addFlashcard(Flashcard flashcard){
        if (flashcards == null)
            flashcards = new ArrayList<>();
        flashcards.add(flashcard);
    }

    public void removeFlashcard(Flashcard flashcard){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlashcardDeck that = (FlashcardDeck) o;
        boolean result = Objects.equals(name, that.name);
        if (!Objects.equals(description, that.description)) result = false;
        if (!isPublic.equals(that.isPublic)) result = false;
        if (!isRemoved.equals(that.isRemoved)) result = false;
        if (!Objects.equals(flashcards, that.flashcards)) result = false;
        return result;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + isPublic.hashCode();
        result = 31 * result + isRemoved.hashCode();
        result = 31 * result + (flashcards != null ? flashcards.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FlashcardDeck{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isPublic=" + isPublic +
                ", isRemoved=" + isRemoved +
                ", flashcards=" + flashcards +
                ", creator=" + creator +
                '}';
    }
}