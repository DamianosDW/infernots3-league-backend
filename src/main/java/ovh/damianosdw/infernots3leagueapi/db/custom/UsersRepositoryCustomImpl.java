package ovh.damianosdw.infernots3leagueapi.db.custom;

import org.springframework.stereotype.Repository;
import ovh.damianosdw.infernots3leagueapi.db.dbmodels.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class UsersRepositoryCustomImpl implements UsersRepositoryCustom
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateUsers(User user)
    {
        entityManager.createNativeQuery("UPDATE users SET USERNAME = ?, PASSWORD = ?, TS3_NICKNAME = ?, LOL_NICKNAME = ? WHERE USER_ID = ?")
                .setParameter(1, user.getUsername())
                .setParameter(2, user.getPassword())
                .setParameter(3, user.getTs3Nickname())
                .setParameter(4, user.getLolNickname())
                .setParameter(5, user.getUserId())
                .executeUpdate();

    }

    @Override
    public List<String> getAllLolNicknames()
    {
        List data = entityManager.createNativeQuery("SELECT LOL_NICKNAME FROM USERS").getResultList();
        List<String> lolNicknames = new LinkedList<>();

        for(Object lolNickname : data)
        {
            lolNicknames.add(String.valueOf(lolNickname));
        }
        return lolNicknames;
    }
}
