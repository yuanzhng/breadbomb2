package com.example.breadbomb;

public class Player {
    private String name;
    private int lives;
    private String order;
    private String prev;
    private int score;

    public Player(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public String getOrder() {
        return order;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int s) {
        score += s;
    }

    public String getPrev() { return prev; }
}
