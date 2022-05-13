package com.example.breadbomb;

public class Player {
    private String name;
    private int lives;
    private String order;

    public Player(String n, int l, String o) {
        name = n;
        lives = l;
        order = o;
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
}
