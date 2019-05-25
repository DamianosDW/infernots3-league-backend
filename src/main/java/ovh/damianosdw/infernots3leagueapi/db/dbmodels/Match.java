/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.db.dbmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "matches")
public class Match
{
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private LocalDateTime matchStartDate;
    @ManyToOne
    private User firstPlayer;
    @ManyToOne
    private User secondPlayer;
    @Column(nullable = false)
    @ColumnDefault("0")
    private int firstPlayerScore;
    @Column(nullable = false)
    @ColumnDefault("0")
    private int secondPlayerScore;
    @Column(nullable = false)
    private boolean bo3;
}
