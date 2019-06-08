/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.db.custom;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ovh.damianosdw.infernots3leagueapi.db.dbmodels.User;
import ovh.damianosdw.infernots3leagueapi.db.repositories.UserStatsRepository;
import ovh.damianosdw.infernots3leagueapi.db.repositories.UsersRepository;
import ovh.damianosdw.infernots3leagueapi.misc.UserInfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Repository
@Transactional
@AllArgsConstructor
public class MatchesRepositoryCustomImpl implements MatchesRepositoryCustom
{
    @PersistenceContext
    private EntityManager entityManager;

    private final UsersRepository usersRepository;
    private final UserStatsRepository userStatsRepository;

    @Override
    public UserInfo getOpponent(int userId)
    {
        // Get registered player ids
        List<Integer> userIds = userStatsRepository.getAllByParticipatesInTournamentIsTrue()
                .stream()
                .map(userStats -> userStats.getUser().getUserId())
                .collect(Collectors.toList());

        // Get unavailable player ids
        List<Integer> unavailablePlayerIds = new LinkedList<>();
        List resultList = entityManager.createNativeQuery("SELECT FIRST_PLAYER_USER_ID, SECOND_PLAYER_USER_ID FROM matches WHERE MATCH_ENDED = false AND MATCH_START_DATE > ?")
                .setParameter(1, LocalDateTime.now().minusHours(1))
                .getResultList();

        for(Object unavailablePlayerId : resultList)
        {
            Object[] playerIds = (Object[])unavailablePlayerId;
            Object firstPlayerId = playerIds[0];
            Object secondPlayerId = playerIds[1];

            unavailablePlayerIds.add((int)firstPlayerId);
            unavailablePlayerIds.add((int)secondPlayerId);
        }

        // Select one player and get player info
        UserInfo playerInfo;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int playerNumber = 0;

        // Select random registered player (instead of logged in player)
        if(unavailablePlayerIds.isEmpty())
        {
            // Select player
            if(userIds.size() > 1)
            {
                do
                {
                    playerNumber = random.nextInt(0, userIds.size() - 1);
                } while(userIds.get(playerNumber) == userId);
            }
            else if(userIds.get(0) == userId)
            {
                return null;
            }

            // Save player info
            User user = usersRepository.getOne(userIds.get(playerNumber));
            playerInfo = new UserInfo(user.getUserId(), user.getUsername(), user.getTs3Nickname(), user.getLolNickname());
        }
        else
        {
            // Remove unavailable players from list
            List<Integer> availablePlayerIds = userIds.stream().filter(id -> !unavailablePlayerIds.contains(id) && !unavailablePlayerIds.contains(userId)).collect(Collectors.toList());

            if(!availablePlayerIds.isEmpty())
            {
                // Select player
                if(availablePlayerIds.size() > 1)
                    playerNumber = random.nextInt(0, availablePlayerIds.size() - 1);
                else if(availablePlayerIds.get(0) == userId)
                    return null;

                // Save player info
                User user = usersRepository.getOne(availablePlayerIds.get(playerNumber));
                playerInfo = new UserInfo(user.getUserId(), user.getUsername(), user.getTs3Nickname(), user.getLolNickname());
            }
            else
                return null;
        }

        return playerInfo;
    }

    @Override
    public void endMatch(int matchId)
    {
        entityManager.createNativeQuery("UPDATE MATCHES SET MATCH_ENDED = true WHERE ID = ?")
                .setParameter(1, matchId)
                .executeUpdate();
    }


}
