/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.endpoints;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ovh.damianosdw.infernots3leagueapi.db.dbmodels.User;
import ovh.damianosdw.infernots3leagueapi.db.repositories.UsersRepository;
import ovh.damianosdw.infernots3leagueapi.exceptions.NotFoundException;
import ovh.damianosdw.infernots3leagueapi.misc.UserCredentials;
import ovh.damianosdw.infernots3leagueapi.misc.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController
{
    private final UsersRepository usersRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("{username}")
    public User getUserByUsername(@PathVariable("username") String username)
    {
        List<User> users = usersRepository.findAll().stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if(users.isEmpty())
            return null;
        else
            return users.get(0);
    }

    @PostMapping("logIn")
    public boolean logIn(@RequestBody UserCredentials userCredentials)
    {
        User userInDB = getUserByUsername(userCredentials.getLogin());

        if(userInDB != null)
            // Check if password is correct
            return passwordEncoder().matches(userCredentials.getPassword(), userInDB.getPassword());
        else
            return false;
    }

    @GetMapping("{username}/info")
    public UserInfo getUserInfo(@PathVariable("username") String username)
    {
        User userInDB = getUserByUsername(username);

        if(userInDB != null)
            return new UserInfo(userInDB.getUserId(), userInDB.getUsername(), userInDB.getTs3Nickname(), userInDB.getLolNickname(), userInDB.getCsgoNickname());
        else
            throw new NotFoundException();
    }

    @PutMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean createUser(@RequestBody User user)
    {
        List<User> users = usersRepository.findAll()
                .stream()
                .filter(tempUser -> tempUser.getUsername().equals(user.getUsername()) || tempUser.getTs3Nickname().equals(user.getTs3Nickname()))
                .collect(Collectors.toList());

        if(users.isEmpty())
        {
            User tempUser = new User(user.getUserId(), user.getUsername(), passwordEncoder().encode(user.getPassword()), user.getTs3Nickname(), user.getLolNickname(), user.getCsgoNickname());

            usersRepository.save(tempUser);
            return true;
        }
        else
            return false;
    }

    @PutMapping("info/update")
    public boolean updateUserInfo(@RequestBody UserInfo userInfo)
    {
        User currentUser = getUserByUsername(userInfo.getUsername());

        if(currentUser != null)
        {
            usersRepository.updateUsers(new User(userInfo.getUserId(), userInfo.getUsername(), currentUser.getPassword(), userInfo.getTs3Nickname(), userInfo.getLolNickname(), userInfo.getCsgoNickname()));
            return true;
        }
        else
            return false;
    }
}
