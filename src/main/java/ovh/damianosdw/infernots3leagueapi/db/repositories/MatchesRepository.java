/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.damianosdw.infernots3leagueapi.db.custom.MatchesRepositoryCustom;
import ovh.damianosdw.infernots3leagueapi.db.dbmodels.Match;

public interface MatchesRepository extends JpaRepository<Match, Long>, MatchesRepositoryCustom
{

}
