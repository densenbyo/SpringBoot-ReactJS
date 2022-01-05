package cz.cvut.fel.ear.lingo.services.interfaces;

import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.Tag;

import java.util.List;

public interface TagService {

    void persist(Tag tag);

    void remove(Tag tag);

    void update(Tag originalTag, Tag tag);

    void addFlashcard(Tag tag, Flashcard flashcard);

    void removeFlashcard(Tag tag, Flashcard flashcard);

    Tag getTagByName(String name);

    List<Tag> findAll();

    Tag findById(Long id);

    List<Tag> findSimilarTags(Long tag);

    void removeTagFromTag(Tag sourceTag, Tag targetTag);

    void addRelation(Tag sourceTag, Tag targetTag);
}