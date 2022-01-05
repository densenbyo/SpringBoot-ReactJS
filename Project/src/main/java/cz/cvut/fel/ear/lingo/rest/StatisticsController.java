package cz.cvut.fel.ear.lingo.rest;

import cz.cvut.fel.ear.lingo.model.Statistic;
import cz.cvut.fel.ear.lingo.model.User;
import cz.cvut.fel.ear.lingo.security.CurrentUser;
import cz.cvut.fel.ear.lingo.security.model.UserDetailsImpl;
import cz.cvut.fel.ear.lingo.services.interfaces.StatisticService;
import cz.cvut.fel.ear.lingo.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@Validated
public class StatisticsController {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsController.class);
    private final StatisticService statService;
    private final UserService userService;

    @Autowired
    public StatisticsController(StatisticService statService, UserService userService){
        this.statService = statService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Statistic getStatistics(@PathVariable Long idUser, @CurrentUser UserDetailsImpl userDetails){
        User user = userService.findById(idUser);
        if (user == null) {
            LOG.info("The user with id {} not found.", idUser);
            return null;
        }
        if (userDetails.getUser().isUser() && !user.getStatistic().getId().equals(userDetails.getUser().getStatistic().getId())) {
            LOG.info("The users id of the authenticated user does not match id {}.", idUser);
            return null;
        }
        return user.getStatistic();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Statistic> allStatistics(@CurrentUser UserDetailsImpl userDetails){
        if (userDetails.getUser().isAdmin())
            return statService.findAll();
        else return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearStatistics(@PathVariable Long id, @CurrentUser UserDetailsImpl userDetails) {
        Statistic statistic = getStatistics(id, userDetails);
        statService.clearStatistic(statistic);
    }
}