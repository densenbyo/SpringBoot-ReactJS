package cz.cvut.fel.ear.lingo.rest;

import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.FlashcardDeck;
import cz.cvut.fel.ear.lingo.security.CurrentUser;
import cz.cvut.fel.ear.lingo.security.model.UserDetailsImpl;
import cz.cvut.fel.ear.lingo.services.interfaces.FlashcardDeckService;
import cz.cvut.fel.ear.lingo.services.interfaces.RepoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flashcardDeck")
@Validated
public class FlashcardDeckController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final FlashcardDeckService fdService;
    private final RepoService rService;

    @Autowired
    public FlashcardDeckController(FlashcardDeckService fdService, RepoService rService){
        this.fdService = fdService;
        this.rService = rService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> crateFlashcardDeck(@RequestBody FlashcardDeck flashcardDeck, @CurrentUser UserDetailsImpl userDetails) {
        flashcardDeck.setCreator(userDetails.getUser());
        fdService.persist(flashcardDeck);
        rService.addFlashcardDeck(userDetails.getRepo(), flashcardDeck);
        LOG.debug("Created {} in user's repo {}.", flashcardDeck, userDetails.getUsername());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", flashcardDeck.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FlashcardDeck> getFlashcardDecks(@CurrentUser UserDetailsImpl userDetails) {
        List<FlashcardDeck> flashcardDecks =  fdService.findAll();
        if (userDetails.getUser().isUser()) {
            return flashcardDecks.stream().filter(FlashcardDeck::isPublic).collect(Collectors.toList());
        }
        return flashcardDecks;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FlashcardDeck getFlashcardDeck(@PathVariable Long id, @CurrentUser UserDetailsImpl userDetails){
        FlashcardDeck flashcardDeck = fdService.findById(id);
        if (userDetails.getUser().isAdmin())
            return flashcardDeck;
        else
            return (flashcardDeck.isPublic() || flashcardDeck.getCreator().getId().equals(userDetails.getUser().getId()))
                    ? flashcardDeck : null;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFlashcardDeck(@PathVariable Long id, @RequestBody FlashcardDeck flashcardDeck,
                                    @CurrentUser UserDetailsImpl userDetails){
        FlashcardDeck originDeck = getFlashcardDeck(id, userDetails);
        if (originDeck != null && originDeck.getId().equals(id)) {
            fdService.update(originDeck, flashcardDeck);
            LOG.info("Updated {}.", flashcardDeck);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFlashcardDeck(@PathVariable Long id, @CurrentUser UserDetailsImpl userDetails) {
        FlashcardDeck flashcardDeck = getFlashcardDeck(id, userDetails);
        fdService.remove(flashcardDeck);
        LOG.info("Removed {}.", flashcardDeck);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PatchMapping(value ="/{id}/restore")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restoreFlashcardDeck(@PathVariable Long id) {
        FlashcardDeck flashcardDeck = fdService.find(id);
        fdService.restore(flashcardDeck);
        LOG.info("Restored {}.", flashcardDeck);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value = "/{id}/addFlashcard")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCard(@PathVariable Long id, @RequestBody Flashcard flashcard, @CurrentUser UserDetailsImpl userDetails) {
        FlashcardDeck flashcardDeck = getFlashcardDeck(id, userDetails);
        fdService.addFlashcard(flashcardDeck, flashcard);
        LOG.debug("Card {} added into deck {}.", flashcard, flashcardDeck);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}/removeFlashcard")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCard(@PathVariable Long id, @RequestBody Flashcard flashcard, @CurrentUser UserDetailsImpl userDetails) {
        FlashcardDeck flashcardDeck = getFlashcardDeck(id, userDetails);
        fdService.removeFlashcard(flashcardDeck, flashcard);
        LOG.debug("Card {} removed from deck {}.", flashcard, flashcardDeck);
    }
}