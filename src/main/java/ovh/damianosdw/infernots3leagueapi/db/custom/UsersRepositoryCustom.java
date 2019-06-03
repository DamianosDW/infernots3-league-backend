/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.db.custom;

import ovh.damianosdw.infernots3leagueapi.db.dbmodels.User;

import java.util.List;

public interface UsersRepositoryCustom
{
    void updateUsers(User user);
    List<String> getAllLolNicknames();
}
