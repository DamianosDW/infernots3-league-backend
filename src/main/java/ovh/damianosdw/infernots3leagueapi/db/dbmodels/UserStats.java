/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.db.dbmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_stats")
public class UserStats
{
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private long leaguePoints;
    @Column(nullable = false)
    private boolean participatesInTournament;
}
