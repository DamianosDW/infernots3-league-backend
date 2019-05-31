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
}
