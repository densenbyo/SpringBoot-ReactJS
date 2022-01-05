package cz.cvut.fel.ear.lingo.model;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractClass;
import cz.cvut.fel.ear.lingo.model.util.Views;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
public class Tag extends AbstractClass {

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    @JsonView(Views.Public.class)
    private String name;

    @ManyToMany
    private List<Tag> relatedTags;

    @ManyToMany
    @JsonView(Views.Public.class)
    private List<Flashcard> flashcards;

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private Boolean isRemoved;

    public Tag(String name) {
        this.name = name;
        this.isRemoved = false;
    }

    public Tag() {}

    public List<Tag> getRelatedTags() {
        return relatedTags;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Boolean getRemoved() {
        return isRemoved;
    }

    public void setRemoved(Boolean removed) {
        isRemoved = removed;
    }

    public void addRelatedTag(Tag tag) {
        Objects.requireNonNull(tag);
        if (relatedTags == null)
            relatedTags = new ArrayList<>();
        relatedTags.add(tag);
    }

    public void removeRelatedTag(Tag tag) {
        Objects.requireNonNull(tag);
        if(relatedTags == null || tag.relatedTags == null)
            return;
        relatedTags.remove(tag);
    }

    public void addFlashcard(Flashcard flashcard) {
        Objects.requireNonNull(flashcard);
        if (flashcards == null)
            flashcards =  new ArrayList<>();
        flashcards.add(flashcard);
    }

    public void removeFlashcard(Flashcard flashcard) {
        if(flashcards != null)
            flashcards.removeIf(c-> Objects.equals(c.getId(), flashcard.getId()));
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
        Tag tag = (Tag) o;
        if (!Objects.equals(name, tag.name)) return false;
        if (!Objects.equals(relatedTags, tag.relatedTags)) return false;
        return Objects.equals(flashcards, tag.flashcards);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (relatedTags != null ? relatedTags.hashCode() : 0);
        result = 31 * result + (flashcards != null ? flashcards.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", relatedTags=" + relatedTags +
                ", flashcards=" + flashcards +
                '}';
    }
}