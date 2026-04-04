package com.example.myFirstGame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.myFirstGame.Main.GameState;

public class GameRenderer {
    private SpriteBatch batch;
    private Texture splash;
    private Texture menuBackground;
    private OrthographicCamera camera;

    private Texture ground;
    private float tileWidth;
    private Texture playerTexture;
    boolean paused = false;
    private Texture playButton;
    private Texture exitButton;

    public GameRenderer(OrthographicCamera camera) {
        this.camera = camera;
        batch = new SpriteBatch();

        splash = new Texture("Byashka_panel_zaglushka.png");
        menuBackground = new Texture("menu_background.png");
        exitButton = new Texture("EXIT.png");

        ground = new Texture("Platform_block.png");
        tileWidth = ground.getWidth();

        playerTexture = new Texture("Sheep_stand.png");

        playButton = new Texture("play_the_game.png");
    }

    public void render(GameState state, float fade, GameWorld world, Player player, boolean buttonPressed,
            boolean paused) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        if (state == GameState.GAME) {
            batch.draw(menuBackground, 0, 0);
            renderGround(world.worldOffset);
            renderPlayer(player);
        }

        if (state == GameState.SPLASH) {
            renderSplash(fade);
        }

        if (state == GameState.MENU) {
            renderMenu();
            renderPlayButton(buttonPressed);
        }
        if (paused) {
            batch.setColor(0, 0, 0, 0.5f);
            batch.draw(menuBackground, 0, 0);
            batch.setColor(1, 1, 1, 1);
        }
        if (state == GameState.MENU) {
            renderMenu();
            renderPlayButton(buttonPressed);
            renderExitButton();
        }
        if (paused) {
            renderExitButton();
        }
        batch.end();
    }

    private void renderSplash(float fade) {
        batch.draw(splash, 0, 0);

        if (fade > 0) {
            batch.setColor(0, 0, 0, fade);
            batch.draw(splash, 0, 0);
            batch.setColor(1, 1, 1, 1);
        }
    }

    private void renderMenu() {
        batch.draw(menuBackground, 0, 0);

    }

    private void renderGround(float worldOffset) {
        float startX = -(worldOffset % tileWidth);

        for (int i = 0; i < 15; i++) {
            batch.draw(ground, startX + i * tileWidth, 0);
        }
    }

    private void renderPlayer(Player player) {
        float width = 100;
        float height = 80;

        if (player.facingRight) {
            batch.draw(playerTexture, player.x, player.y, width, height);
        } else {
            batch.draw(playerTexture, player.x + width, player.y, -width, height);
        }
    }

    private void renderPlayButton(boolean pressed) {
        float x = 600;
        float y = 400;
        float width = 300;
        float height = 150;

        if (pressed) {
            batch.setColor(1f, 1f, 0.6f, 1f); // лёгкий жёлтый оттенок
        }

        batch.draw(playButton, x, y, width, height);

        batch.setColor(1, 1, 1, 1);
    }

    private void renderExitButton() {
        float x = 600;
        float y = 200;
        float w = 300;
        float h = 120;

        batch.draw(exitButton, x, y, w, h);
    }

    public void dispose() {
        batch.dispose();
        splash.dispose();
        menuBackground.dispose();

        ground.dispose();
        playerTexture.dispose();
    }

}
