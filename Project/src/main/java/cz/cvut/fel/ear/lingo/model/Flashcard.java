package cz.cvut.fel.ear.lingo.model;

import com.fasterxml.jackson.annotation.*;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractClass;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractContent;
import cz.cvut.fel.ear.lingo.model.util.Views;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedNativeQuery(
        name = "findByWord",
        query = "SELECT * " +
                "FROM Flashcard f " +
                "WHERE f.word LIKE ? " +
                "AND f.isRemoved = false",
        resultSetMapping = "findByWordMapping"
)
@SqlResultSetMapping(
        name = "findByWordMapping",
        entities = {
                @EntityResult(
                        entityClass = Flashcard.class,
                        fields = {
                                @FieldResult(name = "word", column = "word"),
                                @FieldResult(name = "isRemoved", column = "isRemoved"),
                                @FieldResult(name = "translation", column = "translation"),
                                @FieldResult(name = "id", column = "id")
                        }
                )
        }
)
@Table(name = "FLASHCARD")
public class Flashcard extends AbstractClass {

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private String word;

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private boolean isRemoved;

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private String translation;

    @OneToMany
    @JsonIgnore
    private List<AbstractContent> contents;

    @OneToMany
    @JsonView(Views.Public.class)
    private List<FlashcardProgress> flashcardProgresses;

    @OneToOne
    @JsonIgnore
    private User creator;

    public Flashcard() {
        this.isRemoved = false;
    }

    public Flashcard(String word, String translation, User creator) {
        this.word = word;
        this.translation = translation;
        this.creator = creator;
        this.isRemoved = false;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public String getWord(){
        return word;
    }

    public void setWord(String name){
        this.word = name;
    }

    public String getTranslation(){
        return translation;
    }

    public void setTranslation(String translation){
        this.translation = translation;
    }

    public List<FlashcardProgress> getFlashcardProgresses() {
        return flashcardProgresses;
    }

    public List<AbstractContent> getContents() {
        return contents;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void addContent(AbstractContent abstractContent){
        Objects.requireNonNull(abstractContent);
        if (contents == null)
            contents = new ArrayList<>();
        contents.add(abstractContent);
    }

    public void removeContent(AbstractContent abstractContent){
        if(contents != null)
            contents.removeIf(c-> Objects.equals(c.getId(), abstractContent.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flashcard flashcard = (Flashcard) o;
        if (isRemoved != flashcard.isRemoved) return false;
        if (!Objects.equals(word, flashcard.word)) return false;
        if (!Objects.equals(translation, flashcard.translation))
            return false;
        if (!Objects.equals(contents, flashcard.contents)) return false;
        if (!Objects.equals(flashcardProgresses, flashcard.flashcardProgresses))
            return false;
        return Objects.equals(creator, flashcard.creator);
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (isRemoved ? 1 : 0);
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        result = 31 * result + (flashcardProgresses != null ? flashcardProgresses.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "word='" + word + '\'' +
                ", isRemoved=" + isRemoved +
                ", translation='" + translation + '\'' +
                ", contents=" + contents +
                ", flashcardProgresses=" + flashcardProgresses +
                ", creator=" + creator +
                '}';
    }
}