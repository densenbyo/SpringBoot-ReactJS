package cz.cvut.fel.ear.lingo.rest;

import cz.cvut.fel.ear.lingo.model.Flashcard;
import cz.cvut.fel.ear.lingo.model.Tag;
import cz.cvut.fel.ear.lingo.security.CurrentUser;
import cz.cvut.fel.ear.lingo.security.model.UserDetailsImpl;
import cz.cvut.fel.ear.lingo.services.interfaces.FlashcardService;
import cz.cvut.fel.ear.lingo.services.interfaces.TagService;
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
@RequestMapping("/tag")
@Validated
public class TagController {

    private final static Logger LOG = LoggerFactory.getLogger(RepoController.class);
    private final TagService tagService;
    private final FlashcardService fService;

    @Autowired
    public TagController(TagService tagService, FlashcardService fService){
        this.tagService = tagService;
        this.fService = fService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTag(@RequestBody Tag tag){
        tagService.persist(tag);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", tag.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Tag getTag(@PathVariable Long id){
        return tagService.findById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/{id}/flashcards", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Flashcard> getFlashcards(@PathVariable Long id){
        Tag tag = getTag(id);
        if (tag != null){
            return tag.getFlashcards().stream().filter(flashcard -> !flashcard.isRemoved()).collect(Collectors.toList());
        }
        LOG.info("Tag with id {} not found.", id);
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTag(@PathVariable Long id, @RequestBody Tag tag){
        Tag originalTag = getTag(id);
        tagService.update(originalTag, tag);
        LOG.info("{} updated.", tag);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTag(@PathVariable Long id){
        Tag tag = getTag(id);
        tagService.remove(tag);
        LOG.info("{} deleted", tag);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tag> findAll(){
        return tagService.findAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/find/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Tag findByName(@PathVariable String name){
        return tagService.getTagByName(name);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/findSimilarTags/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tag> findSimilarTag(@PathVariable String name){
        Tag tag = findByName(name);
        if (tag!= null)
            return tag.getRelatedTags();
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value = "/{id}/flashcard/{idCard}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFlashcardToTag(@PathVariable Long id, @PathVariable Long idCard, @CurrentUser UserDetailsImpl userDetails){
        Flashcard flashcard = fService.findById(idCard);
        if (flashcard.getCreator().getId().equals(userDetails.getId()) || userDetails.getUser().isAdmin()) {
            Tag tag = getTag(id);
            tagService.addFlashcard(tag, flashcard);
            LOG.info("To flashcard {} was added new tag {}.", flashcard, tag);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}/flashcard/{idCard}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFlashcardToTag(@PathVariable Long id, @PathVariable Long idCard){
        Flashcard flashcard = fService.findById(idCard);
        Tag tag = getTag(id);
        tagService.removeFlashcard(tag, flashcard);
        LOG.info("To flashcard {} was removed tag {}.", flashcard, tag);
    }
}