/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.misc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo
{
    private int userId;
    private String username;
    private String ts3Nickname;
    private String lolNickname;
    private String csgoNickname;
}
