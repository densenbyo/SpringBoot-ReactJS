package cz.cvut.fel.ear.lingo.services;

import cz.cvut.fel.ear.lingo.dao.RepoDao;
import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;
import cz.cvut.fel.ear.lingo.model.Repo;
import cz.cvut.fel.ear.lingo.services.interfaces.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class RepoServiceImpl implements RepoService {

    private final RepoDao repoDao;

    @Autowired
    public RepoServiceImpl(RepoDao repoDao) {
        this.repoDao = repoDao;
    }

    @Override
    @Transactional
    public void addFlashcardDeck(Repo repo, FlashcardDeck flashcardDeck) {
        Objects.requireNonNull(repo);
        Objects.requireNonNull(flashcardDeck);
        if (!repo.contains(flashcardDeck)) {
            repo.addFlashcardDeck(flashcardDeck);
            repoDao.update(repo);
        }
    }

    @Override
    @Transactional
    public void removeFlashcardDeck(Repo repo, FlashcardDeck flashcardDeck) {
        Objects.requireNonNull(repo);
        Objects.requireNonNull(flashcardDeck);
        repo.removeFlashcardDeck(flashcardDeck);
        repoDao.update(repo);
    }

    @Override
    @Transactional
    public void addFlashcard(Repo repo, Flashcard flashcard) {
        Objects.requireNonNull(repo);
        Objects.requireNonNull(flashcard);
        if (!repo.contains(flashcard)) {
            repo.addFlashcard(flashcard);
            repoDao.update(repo);
        }
    }

    @Override
    @Transactional
    public void removeFlashcard(Repo repo, Flashcard flashcard) {
        Objects.requireNonNull(repo);
        Objects.requireNonNull(flashcard);
        repo.removeFlashcard(flashcard);
        repoDao.update(repo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Repo> findAll(){
        return repoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Repo findById(long id) {
        return repoDao.find(id);
    }
}