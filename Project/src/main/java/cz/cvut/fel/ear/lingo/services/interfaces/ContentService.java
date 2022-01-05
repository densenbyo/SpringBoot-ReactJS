package cz.cvut.fel.ear.lingo.services.interfaces;

import cz.cvut.fel.ear.lingo.model.abstracts.AbstractContent;

import java.util.List;

public interface ContentService {

    AbstractContent save(AbstractContent content);

    void deleteById(Long id);

    AbstractContent findById(Long id);

    AbstractContent findByIdAndCreatorId(Long con_id, Long cre_id);

    List<AbstractContent> findAll();
}