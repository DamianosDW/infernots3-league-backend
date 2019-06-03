/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.db.custom;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserStatsRepositoryCustomImpl implements UserStatsRepositoryCustom
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateUserLeaguePoints(int userId, int points)
    {
        entityManager.createNativeQuery("UPDATE user_stats SET league_points = ? WHERE user_user_id = ?")
                .setParameter(1, points)
                .setParameter(2, userId)
                .executeUpdate();
    }

    @Override
    public void updateUserParticipationInTournament(int userId, boolean participation)
    {
        entityManager.createNativeQuery("UPDATE user_stats SET participates_in_tournament = ? WHERE user_user_id = ?")
                .setParameter(1, participation)
                .setParameter(2, userId)
                .executeUpdate();
    }

    @Override
    public void addLeaguePoint(int matchId, int userId, int playerNumber)
    {
        // Get proper data from database
        Object userLeaguePoints = entityManager.createNativeQuery("SELECT LEAGUE_POINTS FROM USER_STATS WHERE USER_USER_ID = ?")
                .setParameter(1, userId)
                .getSingleResult();

        int playerScore = 0;
        if(playerNumber == 1)
        {
            playerScore = (int)entityManager.createNativeQuery("SELECT FIRST_PLAYER_SCORE FROM MATCHES WHERE ID = ?")
                    .setParameter(1, matchId)
                    .getSingleResult();
        }
        else if(playerNumber == 2)
        {
            playerScore = (int)entityManager.createNativeQuery("SELECT SECOND_PLAYER_SCORE FROM MATCHES WHERE ID = ?")
                    .setParameter(1, matchId)
                    .getSingleResult();
        }

        // Update user_stats and matches
        entityManager.createNativeQuery("UPDATE USER_STATS SET LEAGUE_POINTS = ? WHERE USER_USER_ID = ?")
                .setParameter(1, (int)userLeaguePoints + 1)
                .setParameter(2, userId)
                .executeUpdate();

        if(playerNumber == 1)
        {
            entityManager.createNativeQuery("UPDATE MATCHES SET FIRST_PLAYER_SCORE = ? WHERE ID = ?")
                    .setParameter(1, playerScore + 1)
                    .setParameter(2, matchId)
                    .executeUpdate();
        }
        else if(playerNumber == 2)
        {
            entityManager.createNativeQuery("UPDATE MATCHES SET SECOND_PLAYER_SCORE = ? WHERE ID = ?")
                    .setParameter(1, playerScore + 1)
                    .setParameter(2, matchId)
                    .executeUpdate();
        }
    }

    @Override
    public void removeLeaguePoint(int matchId, int userId, int playerNumber)
    {
        // Get proper data from database
        int userLeaguePoints = (int)entityManager.createNativeQuery("SELECT LEAGUE_POINTS FROM USER_STATS WHERE USER_USER_ID = ?")
                .setParameter(1, userId)
                .getSingleResult();

        int playerScore = 0;
        if(playerNumber == 1)
        {
            playerScore = (int)entityManager.createNativeQuery("SELECT FIRST_PLAYER_SCORE FROM MATCHES WHERE ID = ?")
                    .setParameter(1, matchId)
                    .getSingleResult();
        }
        else if(playerNumber == 2)
        {
            playerScore = (int)entityManager.createNativeQuery("SELECT SECOND_PLAYER_SCORE FROM MATCHES WHERE ID = ?")
                    .setParameter(1, matchId)
                    .getSingleResult();
        }

        // Update score and league points
        if(userLeaguePoints > 0 && playerScore > 0)
        {
            // Update user_stats
            entityManager.createNativeQuery("UPDATE USER_STATS SET LEAGUE_POINTS = ? WHERE USER_USER_ID = ?")
                    .setParameter(1, userLeaguePoints - 1)
                    .setParameter(2, userId)
                    .executeUpdate();

            // Update matches
            if(playerNumber == 1)
            {
                entityManager.createNativeQuery("UPDATE MATCHES SET FIRST_PLAYER_SCORE = ? WHERE ID = ?")
                        .setParameter(1, playerScore - 1)
                        .setParameter(2, matchId)
                        .executeUpdate();
            }
            else
            {
                entityManager.createNativeQuery("UPDATE MATCHES SET SECOND_PLAYER_SCORE = ? WHERE ID = ?")
                        .setParameter(1, playerScore - 1)
                        .setParameter(2, matchId)
                        .executeUpdate();
            }
        }
    }
}
