package cz.cvut.fel.ear.lingo.rest;

import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.security.CurrentUser;
import cz.cvut.fel.ear.lingo.security.model.UserDetailsImpl;
import cz.cvut.fel.ear.lingo.services.interfaces.FlashcardService;
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

@RestController
@RequestMapping("/flashcard")
@Validated
public class FlashcardController {

    private static final Logger LOG = LoggerFactory.getLogger(FlashcardController.class);
    private final FlashcardService fService;
    private final RepoService rService;

    @Autowired
    public FlashcardController(FlashcardService fService, RepoService rService){
        this.fService = fService;
        this.rService = rService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> crateFlashcard(@RequestBody Flashcard flashcard, @CurrentUser UserDetailsImpl userDetails) {
        flashcard.setCreator(userDetails.getUser());
        fService.persist(flashcard);
        rService.addFlashcard(userDetails.getRepo(), flashcard);
        LOG.debug("Created {} in user's repo with username {}.", flashcard, userDetails.getUsername());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", flashcard.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flashcard getFlashcard(@PathVariable Long id){
        return fService.findById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Flashcard> getFlashcards(){
        return fService.findAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Flashcard flashcard){
        if (!id.equals(flashcard.getId())) {
            LOG.info("The flashcard id of the current flashcard does not match id {} .", id);
            return;
        }
        Flashcard originFlashcard = getFlashcard(id);
        fService.update(originFlashcard, flashcard);
        LOG.info("Updated {}.", flashcard);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id){
        Flashcard flashcard = getFlashcard(id);
        fService.remove(flashcard);
        LOG.info("Removed {}.", flashcard);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PatchMapping(value ="/{id}/restore")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restoreFlashcard(@PathVariable Long id) {
        Flashcard flashcard = fService.findById(id);
        fService.restore(flashcard);
        LOG.info("Restored {}.", flashcard);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/find/{word}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Flashcard> findByWord(@PathVariable String word) {
        return fService.findByWord(word);
    }
}