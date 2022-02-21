package cz.cvut.fel.ear.lingo.dao;

import cz.cvut.fel.ear.lingo.model.abstracts.AbstractContent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContentDao extends CrudRepository<AbstractContent, Long> {

    Optional<AbstractContent> findById(Long id);

    List<AbstractContent> findAll();

    Optional<AbstractContent> findByIdAndCreatorId(Long con_id, Long cre_id);
}
