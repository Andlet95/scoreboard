package com.scoreboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

import com.match.Match;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScoreboardTest {
    Scoreboard scoreboard;
    @Mock Match match0;
    @Mock Match match1;
    @Mock Match match2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        scoreboard = new Scoreboard();
        when(match0.getId()).thenReturn(0);
        when(match1.getId()).thenReturn(1);
        when(match2.getId()).thenReturn(2);
        when(match0.getTotalScore()).thenReturn(0);
        when(match1.getTotalScore()).thenReturn(0);
        when(match2.getTotalScore()).thenReturn(0);
        when(match0.getMatch()).thenReturn("matchZero");
        when(match1.getMatch()).thenReturn("matchOne");
        when(match2.getMatch()).thenReturn("matchTwo");
    }

    @Test
    void emptyScoreBoardReturnsEmptyString() {
        assertEquals(List.of(), scoreboard.getMatches());
    }

    @Test
    void scoresDetermineOrderOfMatches() {
        scoreboard.addMatch(match0);
        when(match0.getTotalScore()).thenReturn(10);
        List<String> expected = new ArrayList<String>(Arrays.asList("matchZero"));
        assertIterableEquals(expected, scoreboard.getMatches());

        scoreboard.addMatch(match1);
        when(match1.getTotalScore()).thenReturn(5);
        expected = new ArrayList<String>(Arrays.asList("matchZero", "matchOne"));
        assertIterableEquals(expected, scoreboard.getMatches());

        scoreboard.addMatch(match2);
        when(match2.getTotalScore()).thenReturn(7);
        expected = new ArrayList<String>(Arrays.asList("matchZero", "matchTwo", "matchOne"));
        assertIterableEquals(expected, scoreboard.getMatches());
    }

    @Test
    void whenScoresAreEqualMatchesSortedReverseById() {
        scoreboard.addMatch(match0);
        List<String> expected = new ArrayList<String>(Arrays.asList("matchZero"));
        assertIterableEquals(expected, scoreboard.getMatches());

        scoreboard.addMatch(match1);
        expected = new ArrayList<String>(Arrays.asList("matchOne", "matchZero"));
        assertIterableEquals(expected, scoreboard.getMatches());

        scoreboard.addMatch(match2);
        expected = new ArrayList<String>(Arrays.asList("matchTwo", "matchOne", "matchZero"));
        assertIterableEquals(expected, scoreboard.getMatches());
    }

    @Test
    void endingMatchRemovesItFromList() {
        scoreboard.addMatch(match0);
        scoreboard.addMatch(match1);
        scoreboard.addMatch(match2);

        List<String> expected =
            new ArrayList<String>(Arrays.asList("matchTwo", "matchOne", "matchZero"));
        assertIterableEquals(expected, scoreboard.getMatches());

        scoreboard.endMatch(match1);
        expected = new ArrayList<String>(Arrays.asList("matchTwo", "matchZero"));
        assertIterableEquals(expected, scoreboard.getMatches());
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
