package com.example.breadbomb;

public class Player {
    private int turn;
    private String name;
    private int lives;
    private String order;
    private String prev;
    private int score;

    public Player(String n) {
        name = n;
    }

    public Player(String n, int i, int id) {
        name = n;
        lives = i;
        turn = id;
    }


    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }
    public void setLives(int i) {
        lives = i;
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

    public int getId()
    {
        return turn;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int s) {
        score += s;
    }

    public String getPrev() { return prev; }
}
