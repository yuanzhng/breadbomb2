package com.example.breadbomb;

public class Player {
    private String name;
    private int lives;
    private String order;
    private String prev;
    private int score;

    public Player(String n) {
        name = n;
        lives = 2; //default lives number
    }

    public Player(String n, int i) {
        name = n;
        lives = i;
    }

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }
    public void addLives(int i) {
        lives += i;
    }

    public void removeLives(int i) {
        lives -= i;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String o) {order=o;}

    public int getScore() {
        return score;
    }

    public void addScore(int s) {
        score += s;
    }

    public String getPrev() { return prev; }
}
