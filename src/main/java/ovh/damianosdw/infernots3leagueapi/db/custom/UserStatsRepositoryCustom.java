/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.db.custom;

public interface UserStatsRepositoryCustom
{
    void updateUserLeaguePoints(int userId, int points);
    void updateUserParticipationInTournament(int userId, boolean value);
}
