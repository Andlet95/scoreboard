package com.match;

import com.team.Team;

public class Match implements Comparable<Match> {
    static int idCounter = 0;
    int thisMatchId;
    Team homeTeam;
    Team awayTeam;

    public Match(Team homeTeam, Team awayTeam) {
        thisMatchId = idCounter;
        idCounter++;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public void setScore(int homeScore, int awayScore) {
        homeTeam.setScore(homeScore);
        awayTeam.setScore(awayScore);
    }

    public int getHomeScore() {
        return homeTeam.getScore();
    }

    public int getAwayScore() {
        return awayTeam.getScore();
    }

    public int getTotalScore() {
        return homeTeam.getScore() + awayTeam.getScore();
    }

    public int getId() {
        return thisMatchId;
    }

    public String getMatch() {
        String matchString = homeTeam.getName() + " " + homeTeam.getScore() + " - "
            + awayTeam.getName() + " " + awayTeam.getScore();
        return matchString;
    }

    @Override
    public int compareTo(Match match) {
        int comparison = Integer.compare(this.getTotalScore(), match.getTotalScore());
        if (comparison != 0) {
            return comparison;
        }
        return Integer.compare(this.getId(), match.getId());
    }
}
