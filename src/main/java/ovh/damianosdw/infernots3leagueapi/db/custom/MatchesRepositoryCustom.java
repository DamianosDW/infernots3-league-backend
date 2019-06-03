/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.db.custom;

import ovh.damianosdw.infernots3leagueapi.misc.UserInfo;

public interface MatchesRepositoryCustom
{
    UserInfo getOpponent(int userId);
    void endMatch(int matchId);
}
