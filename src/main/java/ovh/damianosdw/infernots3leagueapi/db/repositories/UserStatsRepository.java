/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.damianosdw.infernots3leagueapi.db.custom.UserStatsRepositoryCustom;
import ovh.damianosdw.infernots3leagueapi.db.dbmodels.UserStats;

public interface UserStatsRepository extends JpaRepository<UserStats, Integer>, UserStatsRepositoryCustom
{

}
