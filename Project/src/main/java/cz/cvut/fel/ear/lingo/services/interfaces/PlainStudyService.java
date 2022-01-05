package cz.cvut.fel.ear.lingo.services.interfaces;

import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;
import cz.cvut.fel.ear.lingo.model.Statistic;

public interface PlainStudyService {

    Flashcard getNext();

    void knowIt();

    void dontKnowIt();

    void start(FlashcardDeck deck, Statistic statistic);
}