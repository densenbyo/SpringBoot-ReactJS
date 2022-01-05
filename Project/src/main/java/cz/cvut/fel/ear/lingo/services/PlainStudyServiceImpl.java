package cz.cvut.fel.ear.lingo.services;

import cz.cvut.fel.ear.lingo.exception.LingoException;
import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;
import cz.cvut.fel.ear.lingo.model.FlashcardProgress;
import cz.cvut.fel.ear.lingo.model.Statistic;
import cz.cvut.fel.ear.lingo.services.interfaces.PlainStudyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlainStudyServiceImpl implements PlainStudyService {

    private FlashcardProgress currentFlashcardProgress;
    private Queue<FlashcardProgress> progresses;


    @Override
    public void start(FlashcardDeck deck, Statistic statistic) {

        if(deck.getFlashcards().isEmpty()) {
            throw new LingoException("Trying to study an empty flashcard deck!");
        }

        progresses = statistic.getProgressesOfDeck(deck).stream()
                .sorted(Comparator.comparing(FlashcardProgress::getProgressDegree))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Flashcard getNext() {
        currentFlashcardProgress = progresses.poll();
        assert currentFlashcardProgress != null;
        return currentFlashcardProgress.getFlashcard();
    }

    @Override
    public void knowIt() {
        currentFlashcardProgress.setProgressDegree(currentFlashcardProgress.getProgressDegree() + 0.1);
    }

    @Override
    public void dontKnowIt() {
        currentFlashcardProgress.setProgressDegree(currentFlashcardProgress.getProgressDegree() - 0.1);
    }

}
