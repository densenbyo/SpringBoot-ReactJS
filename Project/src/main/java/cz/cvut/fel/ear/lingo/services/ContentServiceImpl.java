package cz.cvut.fel.ear.lingo.services;

import cz.cvut.fel.ear.lingo.dao.ContentDao;
import cz.cvut.fel.ear.lingo.exception.NotFoundException;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractContent;
import cz.cvut.fel.ear.lingo.rest.StatisticsController;
import cz.cvut.fel.ear.lingo.services.interfaces.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsController.class);
    private final ContentDao repo;

    public ContentServiceImpl(ContentDao repo) {
        this.repo = repo;
    }

    @Override
    public AbstractContent save(@NotNull AbstractContent content) {
        repo.save(content);
        return content;
    }

    @Override
    public void deleteById(@NotNull Long id) {
        if(!repo.existsById(id)) {
            LOG.debug("Cant find context content with id: " + id + " to delete");
        }
        repo.deleteById(id);
    }

    @Override
    public AbstractContent findById(@NotNull Long id) {
        Optional<AbstractContent> content = repo.findById(id);
        if(content.isPresent()) {
            return content.get();
        } else {
            LOG.debug("Cant find audio content with id: " + id);
            return null;
        }
    }

    @Override
    public AbstractContent findByIdAndCreatorId(@NotNull Long con_id, @NotNull Long cre_id) {
        Optional<AbstractContent> content = repo.findByIdAndCreatorId(con_id, cre_id);
        if(content.isPresent()) {
            return content.get();
        } else {
            LOG.debug("Cant find audio content with id: " + con_id);
            return null;
        }
    }

    @Override
    public List<AbstractContent> findAll() {
        return new ArrayList<>(repo.findAll());
    }
}