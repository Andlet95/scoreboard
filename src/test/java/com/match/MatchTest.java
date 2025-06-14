package com.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MatchTest {
    Match match;
    @Mock Team homeTeam;
    @Mock Team awayTeam;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        match = new Match(homeTeam, awayTeam);
    }

    @Test
    void getHomeScoreReflectsHomeTeamScore() {
        when(homeTeam.getScore()).thenReturn(0);
        assertEquals(0, match.getHomeScore());
        when(homeTeam.getScore()).thenReturn(5);
        assertEquals(5, match.getHomeScore());
        when(homeTeam.getScore()).thenReturn(99999999);
        assertEquals(99999999, match.getHomeScore());
        when(homeTeam.getScore()).thenReturn(-100);
        assertEquals(-100, match.getHomeScore());
    }

    @Test
    void getAwayScoreReflectsAwayTeamScore() {
        when(awayTeam.getScore()).thenReturn(0);
        assertEquals(0, match.getHomeScore());
        when(awayTeam.getScore()).thenReturn(5);
        assertEquals(5, match.getHomeScore());
        when(awayTeam.getScore()).thenReturn(99999999);
        assertEquals(99999999, match.getHomeScore());
        when(awayTeam.getScore()).thenReturn(-100);
        assertEquals(-100, match.getHomeScore());
    }

    @Test
    void getTotalScoreReflectsCombinedScore() {
        when(homeTeam.getScore()).thenReturn(0);
        when(awayTeam.getScore()).thenReturn(0);
        assertEquals(0, match.getTotalScore());

        when(homeTeam.getScore()).thenReturn(0);
        when(awayTeam.getScore()).thenReturn(10);
        assertEquals(10, match.getTotalScore());

        when(homeTeam.getScore()).thenReturn(15);
        when(awayTeam.getScore()).thenReturn(0);
        assertEquals(15, match.getTotalScore());

        when(homeTeam.getScore()).thenReturn(10);
        when(awayTeam.getScore()).thenReturn(10);
        assertEquals(20, match.getTotalScore());

        when(homeTeam.getScore()).thenReturn(10000);
        when(awayTeam.getScore()).thenReturn(-10000);
        assertEquals(0, match.getTotalScore());

        when(homeTeam.getScore()).thenReturn(1000);
        when(awayTeam.getScore()).thenReturn(10);
        assertEquals(1010, match.getTotalScore());
    }

    @Test
    void MatchIdIncrementsForEachNewMatch() {
        assertEquals(0, match.getId());
        match = new Match(homeTeam, awayTeam);
        assertEquals(1, match.getId());
        match = new Match(homeTeam, awayTeam);
        assertEquals(2, match.getId());
        match = new Match(homeTeam, awayTeam);
        assertEquals(3, match.getId());
        match = new Match(homeTeam, awayTeam);
        assertEquals(4, match.getId());
        for (int i = 0; i < (1000 - 4); i++) {
            match = new Match(homeTeam, awayTeam);
        }
        assertEquals(1000, match.getId());
    }

    @Test
    void getMatchReturnsTeamsAndScoreAsString() {
        when(homeTeam.getName()).thenReturn("Barcelona");
        when(homeTeam.getScore()).thenReturn(20);
        when(awayTeam.getName()).thenReturn("Sweden");
        when(awayTeam.getScore()).thenReturn(2);

        assertEquals("Barcelona 20 - Sweden 2", match.getMatch());

        when(homeTeam.getName()).thenReturn("Canada");
        assertEquals("Canada 20 - Sweden 2", match.getMatch());

        when(homeTeam.getScore()).thenReturn(1);
        assertEquals("Canada 1 - Sweden 2", match.getMatch());
    }

    @Test
    void setScoreUpdatesHomeAndAwayTeamScores() {
        match.setScore(0, 10);
        verify(homeTeam).setScore(0);
        verify(awayTeam).setScore(10);

        match.setScore(-999, 88);
        verify(homeTeam).setScore(-999);
        verify(awayTeam).setScore(88);

        match.setScore(999999999, -999999999);
        verify(homeTeam).setScore(999999999);
        verify(awayTeam).setScore(-999999999);
    }
}
