/*
 * Created by DamianosDW
 * https://damianosdw.ovh
 */

package ovh.damianosdw.infernots3leagueapi.endpoints;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ovh.damianosdw.infernots3leagueapi.db.dbmodels.Match;
import ovh.damianosdw.infernots3leagueapi.db.repositories.MatchesRepository;
import ovh.damianosdw.infernots3leagueapi.misc.UserInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matches")
@AllArgsConstructor
public class MatchesController
{
    private final MatchesRepository matchesRepository;

    @GetMapping("")
    public List<Match> getAllCurrentMatches()
    {
        return matchesRepository.findAll().stream()
                .filter(match -> match.getMatchStartDate().isAfter(LocalDateTime.now().minusWeeks(1)))
                .collect(Collectors.toList());
    }

    @GetMapping("user/{userId}/opponent")
    public UserInfo getAvailableOpponent(@PathVariable("userId") int userId)
    {
        return matchesRepository.getOpponent(userId);
    }

    @GetMapping("{userId}/nextMatch")
    public Match getUserNextMatchByUserId(@PathVariable("userId") int userId)
    {
        List<Match> currentMatches = getAllCurrentMatches();

        List<Match> nextMatches = currentMatches.stream()
                .filter(match -> match.getFirstPlayer().getUserId() == userId || match.getSecondPlayer().getUserId() == userId)
                .collect(Collectors.toList());

        if(!nextMatches.isEmpty())
            return nextMatches.get(0);
        else
            return null;
    }

    @PostMapping("create")
    public void createMatch(@RequestBody Match match)
    {
        matchesRepository.save(match);
    }

    @PutMapping("{matchId}")
    public boolean endMatch(@PathVariable("matchId") int matchId)
    {
        try {
            matchesRepository.endMatch(matchId);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
