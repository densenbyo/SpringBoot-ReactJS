package cz.cvut.fel.ear.lingo.rest;

import cz.cvut.fel.ear.lingo.model.abstracts.AbstractContent;
import cz.cvut.fel.ear.lingo.model.contents.AudioContent;
import cz.cvut.fel.ear.lingo.model.contents.ContextSentenceContent;
import cz.cvut.fel.ear.lingo.model.contents.ImageContent;
import cz.cvut.fel.ear.lingo.model.contents.MnemonicContent;
import cz.cvut.fel.ear.lingo.security.CurrentUser;
import cz.cvut.fel.ear.lingo.security.model.UserDetailsImpl;
import cz.cvut.fel.ear.lingo.services.interfaces.ContentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/content")
@Validated
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public class ContentController {

    private final ContentService service;

    public ContentController(ContentService service) {
        this.service = service;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<AbstractContent> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public AbstractContent findById(@PathVariable Long id, @CurrentUser UserDetailsImpl userDetails) {
        if(userDetails.getUser().isAdmin()) {
            return service.findById(id);
        } else {
            return service.findByIdAndCreatorId(id, userDetails.getId());
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable Long id, @CurrentUser UserDetailsImpl userDetails) {
        if(userDetails.getUser().isAdmin()) {
            service.deleteById(id);
        } else {
            if(service.findById(id).getCreator().equals(userDetails.getUser())) {
                service.deleteById(id);
            }
        }
    }

    @PostMapping(value = "/audio", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AudioContent saveAudio(@RequestBody AudioContent content, @CurrentUser UserDetailsImpl userDetails) {
        content.setCreator(userDetails.getUser());
        service.save(content);
        return content;
    }

    @PostMapping(value = "/image", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ImageContent saveImage(@RequestBody ImageContent content, @CurrentUser UserDetailsImpl userDetails) {
        content.setCreator(userDetails.getUser());
        service.save(content);
        return content;
    }

    @PostMapping(value = "/mnemonic", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MnemonicContent saveMnemonic(@RequestBody MnemonicContent content, @CurrentUser UserDetailsImpl userDetails) {
        content.setCreator(userDetails.getUser());
        service.save(content);
        return content;
    }

    @PostMapping(value = "/context", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ContextSentenceContent saveContext(@RequestBody ContextSentenceContent content, @CurrentUser UserDetailsImpl userDetails) {
        content.setCreator(userDetails.getUser());
        service.save(content);
        return content;
    }

    @GetMapping(value = "/audio")
    public List<AudioContent> findAllAudio(@CurrentUser UserDetailsImpl userDetails) {
        List<AbstractContent> contents = service.findAll();
        List<AudioContent> result = new ArrayList<>();
        for(AbstractContent c : contents) {
            if(c instanceof AudioContent && c.getCreator().equals(userDetails.getUser())) {
                result.add((AudioContent) c);
            }
        }
        return result;
    }

    @GetMapping(value = "/image")
    public List<ImageContent> findAllImage(@CurrentUser UserDetailsImpl userDetails) {
        List<AbstractContent> contents = service.findAll();
        List<ImageContent> result = new ArrayList<>();
        for(AbstractContent c : contents) {
            if(c instanceof ImageContent && c.getCreator().equals(userDetails.getUser())) {
                result.add((ImageContent) c);
            }
        }
        return result;
    }

    @GetMapping(value = "/mnemonic")
    public List<MnemonicContent> findAllMnemonic(@CurrentUser UserDetailsImpl userDetails) {
        List<AbstractContent> contents = service.findAll();
        List<MnemonicContent> result = new ArrayList<>();
        for(AbstractContent c : contents) {
            if(c instanceof MnemonicContent && c.getCreator().equals(userDetails.getUser())) {
                result.add((MnemonicContent) c);
            }
        }
        return result;
    }

    @GetMapping(value = "/context")
    public List<ContextSentenceContent> findAllContext(@CurrentUser UserDetailsImpl userDetails) {
        List<AbstractContent> contents = service.findAll();
        List<ContextSentenceContent> result = new ArrayList<>();
        for(AbstractContent c : contents) {
            if(c instanceof ContextSentenceContent && c.getCreator().equals(userDetails.getUser())) {
                result.add((ContextSentenceContent) c);
            }
        }
        return result;
    }

}
