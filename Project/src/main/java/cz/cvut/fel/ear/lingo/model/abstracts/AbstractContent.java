package cz.cvut.fel.ear.lingo.model.abstracts;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fel.ear.lingo.model.User;
import cz.cvut.fel.ear.lingo.model.util.Views;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractContent extends AbstractClass {

    @ManyToOne
    @JsonView(Views.Public.class)
    private User creator;

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}