package com.scoreboard;

import com.match.Match;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scoreboard {
    List<Match> matches;

    public Scoreboard() {
        matches = new ArrayList<>();
    }

    public void addMatch(Match match) {
        if (!matches.contains(match)) {
            matches.add(match);
        }
    }

    public void endMatch(Match match) {
        matches.remove(match);
    }

    List<String> getMatches() {
        Collections.sort(matches, Collections.reverseOrder());
        List<String> stringList = new ArrayList<String>();
        for (Match match : matches) {
            stringList.add(match.getMatch());
        }
        return stringList;
    }

    public int getTeamScore(String team) {
        for (Match match : matches) {
            if (match.getAwayTeam().getName() == team) {
                return match.getAwayTeam().getScore();
            }
            if (match.getHomeTeam().getName() == team) {
                return match.getHomeTeam().getScore();
            }
        }
        throw new RuntimeException("Could not find team: " + team);
    }
}
