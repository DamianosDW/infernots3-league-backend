/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.db.dbmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue
    @Column(name = "USER_ID", nullable = false, unique = true)
    private int userId;
    @Column(nullable = false)
    private String username;
    private String password;
    @Column(name = "ts3_nickname", nullable = false)
    private String ts3Nickname;
    private String lolNickname;
}
