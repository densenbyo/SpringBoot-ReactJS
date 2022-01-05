package cz.cvut.fel.ear.lingo.services;

import cz.cvut.fel.ear.lingo.dao.TagDao;
import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.Tag;
import cz.cvut.fel.ear.lingo.services.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public void persist(Tag tag) {
        Objects.requireNonNull(tag);
        tag.setRemoved(false);
        tagDao.persist(tag);
    }

    @Override
    @Transactional
    public void remove(Tag tag) {
        Objects.requireNonNull(tag);
        tag.setRemoved(true);
        tagDao.update(tag);
    }

    @Override
    @Transactional
    public void update(Tag originalTag, Tag tag) {
        Objects.requireNonNull(originalTag);
        Objects.requireNonNull(tag);
        originalTag.setName(tag.getName());
        tagDao.update(originalTag);
    }

    @Override
    @Transactional
    public void addFlashcard(Tag tag, Flashcard flashcard) {
        Objects.requireNonNull(tag);
        Objects.requireNonNull(flashcard);
        // TODO +relation tag-tag
        if (!tag.contains(flashcard)) {
            tag.addFlashcard(flashcard);
            tagDao.update(tag);
        }
    }

    /**
     * Adds tag to another tag.
     *
     * @param sourceTag Tag to add
     * @param targetTag Tag target
     */
    public void addRelation(Tag sourceTag, Tag targetTag) {
        Objects.requireNonNull(sourceTag);
        Objects.requireNonNull(targetTag);
        targetTag.addRelatedTag(sourceTag);
        sourceTag.addRelatedTag(targetTag);
        tagDao.update(sourceTag);
        tagDao.update(targetTag);
    }

    @Override
    @Transactional
    public void removeFlashcard(Tag tag, Flashcard flashcard) {
        Objects.requireNonNull(tag);
        Objects.requireNonNull(flashcard);
        tag.removeFlashcard(flashcard);
        // TODO -relation tag-tag
        tagDao.update(tag);
    }

    /**
     * Removes tag from another tag.
     *
     * @param sourceTag Tag to remove
     * @param targetTag Tag target
     */
    @Override
    @Transactional
    public void removeTagFromTag(Tag sourceTag, Tag targetTag) {
        Objects.requireNonNull(sourceTag);
        Objects.requireNonNull(targetTag);
        targetTag.removeRelatedTag(sourceTag);
        tagDao.update(sourceTag);
        tagDao.update(targetTag);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getTagByName(String name) {
        Objects.requireNonNull(name);
        return tagDao.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Tag findById(Long id) {
        Objects.requireNonNull(id);
        return tagDao.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findSimilarTags(Long tag) {
        Objects.requireNonNull(tag);
        return tagDao.findAllRelatedTag(tag);
    }
}