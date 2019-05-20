package ovh.damianosdw.infernots3leagueapi.endpoints;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ovh.damianosdw.infernots3leagueapi.db.dbmodels.User;
import ovh.damianosdw.infernots3leagueapi.db.repositories.UsersRepository;
import ovh.damianosdw.infernots3leagueapi.exceptions.UserFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController
{
    private final UsersRepository usersRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("{username}/user")
    public User getUserByUsername(@PathVariable("username") String username)
    {
        List<User> users = usersRepository.findAll().stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if(users.isEmpty())
            return null;
        else
            return users.get(0);
    }

    @PutMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user)
    {
        List<User> users = usersRepository.findAll().stream().filter(tempUser -> tempUser.getUsername().equals(user.getUsername())).collect(Collectors.toList());

        if(users.isEmpty())
        {
            User tempUser = new User(user.getUserId(), user.getUsername(), passwordEncoder().encode(user.getPassword()), user.getTs3Nickname(), user.getLolNickname(), user.getCsgoNickname());

            usersRepository.save(tempUser);
        }
        else
            throw new UserFoundException();
    }
}
