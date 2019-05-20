package ovh.damianosdw.infernots3leagueapi.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.damianosdw.infernots3leagueapi.db.dbmodels.User;

public interface UsersRepository extends JpaRepository<User, Integer>
{

}
