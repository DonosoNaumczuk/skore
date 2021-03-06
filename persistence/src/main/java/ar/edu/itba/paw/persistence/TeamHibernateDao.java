package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.exceptions.invalidstate.TeamInvalidStateException;
import ar.edu.itba.paw.exceptions.notfound.SportNotFoundException;
import ar.edu.itba.paw.exceptions.notfound.TeamNotFoundException;
import ar.edu.itba.paw.exceptions.notfound.UserNotFoundException;
import ar.edu.itba.paw.interfaces.PremiumUserDao;
import ar.edu.itba.paw.interfaces.SportDao;
import ar.edu.itba.paw.interfaces.TeamDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.PremiumUser;
import ar.edu.itba.paw.models.Sport;
import ar.edu.itba.paw.models.Team;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Optional;

@Repository
public class TeamHibernateDao implements TeamDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamHibernateDao.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PremiumUserDao premiumUserDao;

    @Autowired
    private SportDao sportDao;

    @Override
    public Optional<Team> create(final String leaderName, final long leaderId,
                                 final String acronym, final String teamName,
                                 final boolean isTemp, final String sportName,
                                 final byte[] file) {
        LOGGER.trace("Try to find leader: {}", leaderName);
        PremiumUser leader = null;
        if(leaderName != null) {
            leader = premiumUserDao.findByUserName(leaderName)
                    .orElseThrow(() -> UserNotFoundException.ofUsername(leaderName));
        }
        LOGGER.trace("Find leader: {}", leaderName);
        LOGGER.trace("Try to find sport: {}", sportName);
        Sport sport = sportDao.findByName(sportName)
                .orElseThrow(() -> SportNotFoundException.ofId(sportName));
        LOGGER.trace("Find sport: {}", sportName);

        Team team = new Team(leader, acronym, teamName, isTemp, sport, file);
        em.persist(team);
        return Optional.of(team);
    }

    @Override
    public Optional<Team> findByTeamName(final String teamName) {
        LOGGER.trace("Try to find team: {}", teamName);
        Team team = em.find(Team.class, teamName);
        return (team == null)?Optional.empty():Optional.of(team);
    }

    @Override
    public boolean remove(final String teamName) {
        LOGGER.trace("Try to delete team: {}", teamName);
        Optional<Team> team = findByTeamName(teamName);
        boolean ans = false;
        if(team.isPresent()) {
            em.remove(team.get());
            ans = true;
        }
        return ans;
    }

    @Override
    public Optional<Team> updateTeamInfo(final String newTeamName, final String newAcronym,
                                         final String newLeaderName, final String newSportName,
                                         final String oldTeamName) {
        LOGGER.trace("Try to modify: {}", oldTeamName);
        Team team = findByTeamName(oldTeamName)
                .orElseThrow(() -> TeamNotFoundException.ofId(oldTeamName));
        team.setName(newTeamName);
        team.setAcronym(newAcronym);

        LOGGER.trace("Try to fin new leader: {}", newLeaderName);
        PremiumUser newLeader = premiumUserDao.findByUserName(newLeaderName)
                .orElseThrow(() -> UserNotFoundException.ofUsername(newLeaderName));
        team.setLeader(newLeader);

        LOGGER.trace("Try to fin new sport: {}", newSportName);
        Sport newSport = sportDao.findByName(newSportName)
                .orElseThrow(() -> SportNotFoundException.ofId(newSportName));
        team.setSport(newSport);

        em.merge(team);
        return  Optional.of(team);
    }

    @Override
    public Optional<Team> addPlayer(final String teamName, final long userId) {
        LOGGER.trace("Try to add player: {} to team: {}", userId, teamName);
        Team team = findByTeamName(teamName)
                .orElseThrow(() -> TeamNotFoundException.ofId(teamName));

        User user = userDao.findById(userId)
                .orElseThrow(() -> UserNotFoundException.ofId(userId));

        team.addPlayer(user);
        em.merge(team);
        return Optional.of(team);
    }

    @Override
    public Optional<Team> removePlayer(final String teamName, final long userId) {
        LOGGER.trace("Try to add player: {} to team: {}", userId, teamName);
        Team team = findByTeamName(teamName)
                .orElseThrow(() -> TeamNotFoundException.ofId(teamName));

        if(!team.removePlayer(userId)) {
            throw UserNotFoundException.ofId(userId);
        }

        em.merge(team);
        LOGGER.trace("Successfully add player: {} from team: {}", userId, teamName);
        return Optional.of(team);
    }
}
