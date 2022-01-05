package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.model.Flashcard;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class FlashcardDao extends BaseDao<Flashcard> {

    protected FlashcardDao() {
        super(Flashcard.class);
    }

    @Override
    public List<Flashcard> findAll() {
        try {
            return em.createQuery("SELECT f FROM Flashcard f WHERE NOT f.isRemoved", Flashcard.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Flashcard> findByWord(String word) {
        try {
            return (List<Flashcard>) em.createNamedQuery("findByWord")
                    .setParameter(1, word)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Flashcard> findCardsInPublicDecks(){
        try {
            return em.createQuery("SELECT f FROM Flashcard f JOIN FlashcardDeck fd WHERE f MEMBER OF fd.flashcards AND fd.isPublic = true ", Flashcard.class)
                    .getResultList();
        } catch (NoResultException e){
            return null;
        }
    }
}