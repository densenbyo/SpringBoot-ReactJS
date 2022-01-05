package cz.cvut.fel.ear.lingo.rest;

import cz.cvut.fel.ear.lingo.model.User;
import cz.cvut.fel.ear.lingo.security.model.RegistrationRequest;
import cz.cvut.fel.ear.lingo.security.model.Response;
import cz.cvut.fel.ear.lingo.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class RegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        if((userService.findByUsername(request.getUsername()) != null) || (userService.findByMail(request.getMail()) != null)){
            return new ResponseEntity<>(new Response(false, "Bad Credentials!"), HttpStatus.BAD_REQUEST);
        }
        User user = new User(request.getUsername(), request.getMail(), request.getPassword());
        userService.persist(user);
        LOG.debug("User {} successfully registered.", user);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}