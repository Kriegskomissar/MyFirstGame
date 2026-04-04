package com.example.myFirstGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player {
    public float x = 200; // фиксированная позиция по X
    public float y = 200;

    public float velocityY = 0;
    public float velocityX = 0;
    private float moveSpeed = 300f;

    private float gravity = -800f;
    private float jumpForce = 400f;

    public boolean facingRight = true;

    private float playerWidth = 100;
    private float screenWidth = Gdx.graphics.getWidth();

    private float groundY = 180; // уровень земли (подгони под свою текстуру)

    public void update(float delta, float screenWidth) {
        // Прыжок
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            if (y <= groundY + 1) { // чтобы не прыгать в воздухе
                velocityY = jumpForce;
            }
        }

        velocityX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -moveSpeed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = moveSpeed;
        }

        x += velocityX * delta;

        // Гравитация
        velocityY += gravity * delta;
        y += velocityY * delta;

        // "Коллизия" с землёй (на самом деле clamp)
        if (y < groundY) {
            y = groundY;
            velocityY = 0;
        }

        if (velocityX > 0)
            facingRight = true;
        if (velocityX < 0)
            facingRight = false;

        float leftBound = 0;
        float rightBound = screenWidth - playerWidth;

        if (x < leftBound)
            x = leftBound;
        if (x > rightBound)
            x = rightBound;

    }

}
