package com.example.myFirstGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */

public class Main extends ApplicationAdapter {

    private OrthographicCamera camera;
    private Viewport viewport;
    private GameRenderer renderer;
    private GameWorld world;
    private Player player;
    private Vector3 touch = new Vector3();

    float timer = 0f;
    float fade = 0f;
    boolean fading = false;
    boolean showMenu = false;

    enum GameState {
        SPLASH,
        MENU,
        GAME
    }

    boolean paused = false;

    private GameState state = GameState.SPLASH;

    boolean buttonPressed = false;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1536, 1024, camera);
        viewport.apply();

        renderer = new GameRenderer(camera);

        world = new GameWorld();
        player = new Player();

    }

    @Override
    public void render() {
        float delta = paused ? 0f : Gdx.graphics.getDeltaTime();
        // ЛОГИКА (оставляем здесь)
        if (!showMenu) {
            timer += delta;
            if (timer > 3F) {
                fading = true;
            }
            if (fading) {
                fade += delta * 0.5F;
                if (fade > 1F) {
                    fade = 1F;
                    showMenu = true;
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
        }

        player.update(delta, camera.viewportWidth);
        world.update(delta);

        switch (state) {
            case SPLASH:
                timer += delta;
                if (timer > 3F) {
                    fading = true;
                }
                if (fading) {
                    fade += delta * 0.5f;
                    if (fade > 1f) {
                        fade = 1f;
                        state = GameState.MENU;
                    }
                }
                break;

            case MENU:

                if (Gdx.input.justTouched()) {
                    touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(touch);

                    float x = touch.x;
                    float y = touch.y;

                    if (x > 600 && x < 900 && y > 400 && y < 550) {
                        state = GameState.GAME;
                    }
                }

                break;

            case GAME:
                if (!paused) {
                    player.update(delta, camera.viewportWidth);
                    world.update(delta);
                }
                break;
        }

        if (paused && Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            float x = touch.x;
            float y = touch.y;
            if (x > 600 && x < 900 && y > 200 && y < 320) {
                Gdx.app.exit();
            }
        }

        renderer.render(state, fade, world, player, buttonPressed, paused);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

}
