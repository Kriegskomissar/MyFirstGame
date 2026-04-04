package com.example.myFirstGame;

public class GameWorld {
    public float worldSpeed = 200f;
    public float worldOffset = 0f;

    public void update(float delta) {
        worldOffset += worldSpeed * delta;
    }
}
