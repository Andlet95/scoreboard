package com.team;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TeamTest {
    Team team = new Team("teamName");

    @Test
    void scoreSavedWhenSettingScore() {
        assertEquals(0, team.getScore());
        team.setScore(10);
        assertEquals(10, team.getScore());
        team.setScore(999999999);
        assertEquals(999999999, team.getScore());
        team.setScore(-10);
        assertEquals(-10, team.getScore());
    }

    @Test
    void teamNameCanBeAcquired() {
        team = new Team("thisIsNewTeam");
        assertEquals("thisIsNewTeam", team.getName());
        team = new Team("");
        assertEquals("", team.getName());
    }
}
