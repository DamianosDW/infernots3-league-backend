/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.endpoints;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ovh.damianosdw.infernots3leagueapi.db.dbmodels.User;
import ovh.damianosdw.infernots3leagueapi.db.dbmodels.UserStats;
import ovh.damianosdw.infernots3leagueapi.db.repositories.UserStatsRepository;
import ovh.damianosdw.infernots3leagueapi.db.repositories.UsersRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/userstats")
@AllArgsConstructor
public class UserStatsController
{
    private final UserStatsRepository userStatsRepository;
    private final UsersRepository usersRepository;

    @GetMapping("")
    public List<UserStats> getAllUserStats()
    {
        return userStatsRepository.findAll();
    }

    @GetMapping("lolNicknames")
    public List<String> getAllLolNicknames()
    {
        return usersRepository.getAllLolNicknames();
    }

    @GetMapping("/ranking")
    public List<UserStats> getAllUserStatsOrderedByLeaguePoints()
    {
        List<UserStats> userStats = userStatsRepository.findAll()
                .stream()
                .filter(UserStats::isParticipatesInTournament)
                .sorted(Comparator.comparingInt(UserStats::getLeaguePoints))
                .collect(Collectors.toList());
        Collections.reverse(userStats);
        return userStats;
    }

    @GetMapping("{ts3Nickname}")
    public UserStats getUserStatsByTs3Nickname(@PathVariable("ts3Nickname") String ts3Nickname)
    {
        List<UserStats> userStatsList = userStatsRepository.findAll()
                .stream()
                .filter(userStats -> userStats.getUser().getTs3Nickname().equals(ts3Nickname))
                .collect(Collectors.toList());

        return (!userStatsList.isEmpty()) ? userStatsList.get(0) : null;
    }

    @PutMapping("update/points/")
    public void updateUserLeaguePoints(int userId, int points)
    {
        userStatsRepository.updateUserLeaguePoints(userId, points);
    }

    @PutMapping("update/participation/{userId}")
    public boolean updateUserParticipationInTournament(@PathVariable("userId") int userId)
    {
        User user = usersRepository.getOne(userId);
        if(user.getLolNickname() == null)
        {
            return false;
        }
        else
        {
            userStatsRepository.updateUserParticipationInTournament(userId, true);
            return true;
        }
    }

    @PutMapping("addLeaguePoint")
    public boolean addLeaguePoint(int matchId, int userId, int playerNumber)
    {
        try {
            userStatsRepository.addLeaguePoint(matchId, userId, playerNumber);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @PutMapping("removeLeaguePoint")
    public boolean removeLeaguePoint(int matchId, int userId, int playerNumber)
    {
        try {
            userStatsRepository.removeLeaguePoint(matchId, userId, playerNumber);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

}
