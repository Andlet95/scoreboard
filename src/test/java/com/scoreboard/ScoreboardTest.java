package com.scoreboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.match.Match;
import com.team.Team;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.CompareTo;

class ScoreboardIntegrationTest {
    @Test
    void specificTeamsInSpecificMatchsAreSortedInASpecificOrder() {
        Team mexico = new Team("Mexico");
        Team canada = new Team("Canada");
        Team spain = new Team("Spain");
        Team brazil = new Team("Brazil");
        Team germany = new Team("Germany");
        Team france = new Team("France");
        Team uruguay = new Team("Uruguay");
        Team italy = new Team("Italy");
        Team argentina = new Team("Argentina");
        Team australia = new Team("Australia");

        Match firstMatch = new Match(mexico, canada);
        Match secondMatch = new Match(spain, brazil);
        Match thirdMatch = new Match(germany, france);
        Match fourthMatch = new Match(uruguay, italy);
        Match fifthMatch = new Match(argentina, australia);

        Scoreboard scoreboard = new Scoreboard();

        scoreboard.addMatch(firstMatch);
        scoreboard.addMatch(secondMatch);
        scoreboard.addMatch(thirdMatch);
        scoreboard.addMatch(fourthMatch);
        scoreboard.addMatch(fifthMatch);

        List<String> expected = new ArrayList<>();
        expected.add("Argentina 0 - Australia 0");
        expected.add("Uruguay 0 - Italy 0");
        expected.add("Germany 0 - France 0");
        expected.add("Spain 0 - Brazil 0");
        expected.add("Mexico 0 - Canada 0");

        assertIterableEquals(expected, scoreboard.getMatches());

        firstMatch.setScore(0, 5);
        secondMatch.setScore(10, 2);
        thirdMatch.setScore(2, 2);
        fourthMatch.setScore(6, 6);
        fifthMatch.setScore(3, 1);

        expected.clear();
        expected.add("Uruguay 6 - Italy 6");
        expected.add("Spain 10 - Brazil 2");
        expected.add("Mexico 0 - Canada 5");
        expected.add("Argentina 3 - Australia 1");
        expected.add("Germany 2 - France 2");

        assertIterableEquals(expected, scoreboard.getMatches());
    }

    @Test
    void gettingExistingTeamScoreCollectsScoreOfRelevantTeam() {
        Team mexico = new Team("Mexico");
        Team canada = new Team("Canada");
        Team spain = new Team("Spain");
        Team brazil = new Team("Brazil");

        Match firstMatch = new Match(mexico, canada);
        Match secondMatch = new Match(spain, brazil);

        Scoreboard scoreboard = new Scoreboard();

        scoreboard.addMatch(firstMatch);
        scoreboard.addMatch(secondMatch);

        canada.setScore(8);
        mexico.setScore(5);

        assertEquals(8, scoreboard.getTeamScore("Canada"));
        assertEquals(5, scoreboard.getTeamScore("Mexico"));
    }

    @Test
    void gettingNonExistantTeamThrowsException() {
        Scoreboard scoreboard = new Scoreboard();

        assertThrows(RuntimeException.class, () -> scoreboard.getTeamScore("USA"));
    }
}

class ScoreboardTest {
    Scoreboard scoreboard;
    @Mock Match match0;
    @Mock Match match1;
    @Mock Match match2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        scoreboard = new Scoreboard();
        when(match0.getMatch()).thenReturn("matchZero");
        when(match1.getMatch()).thenReturn("matchOne");
        when(match2.getMatch()).thenReturn("matchTwo");
    }

    @Test
    void emptyScoreBoardReturnsEmptyString() {
        assertIterableEquals(List.of(), scoreboard.getMatches());
    }

    @Test
    void endingMatchRemovesItFromList() {
        scoreboard.addMatch(match0);
        scoreboard.addMatch(match1);
        scoreboard.addMatch(match2);

        List<String> expected =
            new ArrayList<String>(Arrays.asList("matchTwo", "matchOne", "matchZero"));
        List<String> result = scoreboard.getMatches();
        assertTrue(result.size() == expected.size() && result.containsAll(expected)
            && expected.containsAll(result));

        scoreboard.endMatch(match1);
        expected = new ArrayList<String>(Arrays.asList("matchTwo", "matchZero"));
        result = scoreboard.getMatches();
        assertTrue(result.size() == expected.size() && result.containsAll(expected)
            && expected.containsAll(result));
    }

    @Test
    void matchCanOnlyBeAddedOnce() {
        scoreboard.addMatch(match0);

        List<String> expected = new ArrayList<String>(Arrays.asList("matchZero"));
        assertIterableEquals(expected, scoreboard.getMatches());
        scoreboard.addMatch(match0);

        expected = new ArrayList<String>(Arrays.asList("matchZero"));
        assertIterableEquals(expected, scoreboard.getMatches());
    }

    @Test
    void endingNonListedMatchDoesNotChangeList() {
        List<String> expected = new ArrayList<String>();
        assertIterableEquals(expected, scoreboard.getMatches());

        scoreboard.endMatch(match0);
        assertIterableEquals(expected, scoreboard.getMatches());

        scoreboard.addMatch(match0);
        expected = new ArrayList<String>(Arrays.asList("matchZero"));
        assertIterableEquals(expected, scoreboard.getMatches());

        scoreboard.endMatch(match1);
        assertIterableEquals(expected, scoreboard.getMatches());
    }
}
