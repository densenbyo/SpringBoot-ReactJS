package cz.cvut.fel.ear.lingo.rest;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fel.ear.lingo.model.User;
import cz.cvut.fel.ear.lingo.model.enums.UserRole;
import cz.cvut.fel.ear.lingo.model.util.Views;
import cz.cvut.fel.ear.lingo.security.CurrentUser;
import cz.cvut.fel.ear.lingo.security.model.RegistrationRequest;
import cz.cvut.fel.ear.lingo.security.model.Response;
import cz.cvut.fel.ear.lingo.security.model.UserDetailsImpl;
import cz.cvut.fel.ear.lingo.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000/")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.Public.class)
    public User getCurrent(@CurrentUser UserDetailsImpl userDetails){
        return userDetails.getUser();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/add/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addAdmin(@PathVariable String role, @RequestBody RegistrationRequest request) {
        if(userService.findByUsername(request.getUsername()) != null)
            return new ResponseEntity(new Response(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        if(userService.findByMail(request.getMail()) != null)
            return new ResponseEntity(new Response(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        User user = new User(request.getUsername(), request.getMail(), request.getPassword());
        if (role.equals("ADMIN")) user.setRole(UserRole.ADMIN);
        userService.persist(user);
        LOG.debug("Admin added user {} successfully.", user);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/all");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @JsonView(Views.Public.class)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return userService.findAllUser();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "find/findById/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable Long id){
        return userService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "find/findByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "find/findByMail/{mail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findByMail(@PathVariable String mail){
        return userService.findByMail(mail);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void blockUser(@PathVariable Long id){
        User user = userService.findById(id);
        if (user == null) {
            LOG.info("User with id : {} not found.", id);
            return;
        }
        userService.block(user);
        LOG.info("Blocked {}.", user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/unblock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unblockUser(@PathVariable Long id){
        User user = userService.findById(id);
        if (user == null) {
            LOG.info("User with id : {} not found.", id);
            return;
        }
        userService.unblock(user);
        LOG.info("Unblocked {}.", user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/role/{role}")
    public void setRole(@PathVariable Long id, @PathVariable String role){
        User user = userService.findById(id);
        if (user == null) {
            LOG.info("User with id : {} not found.", id);
            return;
        }
        userService.setRole(role, user);
        LOG.info("The role of the user with id: {} has changed.", id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') || #id == authentication.principal.id")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@PathVariable Long id, @RequestBody(required = false) User user){
        final User originalUser = userService.findById(id);
        if (originalUser == null) {
            LOG.info("User identifier in the data does not match the one in the request URL.");
            return;
        }
        userService.update(originalUser, user);
        LOG.info("{} updated.", user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') || #id == authentication.principal.id")
    @DeleteMapping(value = "/{id}")
    public void removeUser(@PathVariable Long id) {
        final User user = userService.findById(id);
        if (user == null) {
            LOG.info("User with id : {} not found.", id);
            return;
        }
        userService.remove(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/restore")
    public void restoreUser(@PathVariable Long id) {
        final User user = userService.find(id);
        if (user == null) {
            LOG.info("User with id : {} not found.", id);
            return;
        }
        userService.restore(user);
    }
}