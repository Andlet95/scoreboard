package com.team;

public class Team {
    String name;
    int score = 0;
    public Team(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
