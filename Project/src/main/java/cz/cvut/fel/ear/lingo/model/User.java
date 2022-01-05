package cz.cvut.fel.ear.lingo.model;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fel.ear.lingo.model.abstracts.AbstractClass;
import cz.cvut.fel.ear.lingo.model.enums.UserRole;
import cz.cvut.fel.ear.lingo.model.util.Views;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "POCKET_USER")
@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.isRemoved = false AND u.username = :username")

public class User extends AbstractClass {

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    @JsonView(Views.Public.class)
    private String username;

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private String password;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    @JsonView(Views.Public.class)
    private String mail;

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private Boolean isActive;

    @Basic(optional = false)
    @Column(nullable = false)
    @JsonView(Views.Public.class)
    private Boolean isRemoved;

    @Enumerated(EnumType.STRING)
    @JsonView(Views.Public.class)
    private UserRole role;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Repo repo;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonView(Views.Public.class)
    private Statistic statistic;

    public User() {
        this.role = UserRole.VIEWER;
        this.isActive = true;
        this.isRemoved = false;
        this.repo = new Repo();
        this.statistic = new Statistic();
    }

    public User(String username, String mail, String password) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.role = UserRole.VIEWER;
        this.isActive = true;
        this.isRemoved = false;
        this.repo = new Repo();
        this.statistic = new Statistic();
    }

    public void addFlashcard(Flashcard flashcard) {
        repo.addFlashcard(flashcard);
        statistic.addFlashcard(flashcard);
    }

    public void removeFlashcard(Flashcard flashcard) {
        repo.removeFlashcard(flashcard);
        statistic.removeFlashcard(flashcard);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void erasePassword() {
        this.password = null;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(Boolean removed) {
        isRemoved = removed;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isUser() {
        return role == UserRole.USER;
    }

    public Repo getRepository() {
        return repo;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return Objects.equals(mail, user.mail);
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", isActive=" + isActive +
                ", role=" + role +
                ", repository=" + repo +
                ", statistic=" + statistic +
                '}';
    }
}