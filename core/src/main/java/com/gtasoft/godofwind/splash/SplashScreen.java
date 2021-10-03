package com.gtasoft.godofwind.splash;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.godofwind.GodOfWind;
import com.gtasoft.godofwind.ressource.GraphicTools;


public class SplashScreen implements Screen, ApplicationListener {
    boolean translated = false;
    Texture imgBack;
    float w;
    float h;
    GodOfWind game;
    Texture splashImage;
    long splashTime = 0;
    boolean go = false;
    Stage stage;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private GraphicTools graphicTools;
    private Timer ressourceLoadTimer;


    public SplashScreen(GodOfWind game) {
        w = 1280;
        h = 768;

        this.game = game;
        stage = new Stage();

        graphicTools = game.getGT();

        splashImage = new Texture(Gdx.files.internal("img/menu/splash.png"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 768, camera);
        stage.setViewport(viewport);


        imgBack = new Texture(Gdx.files.internal("img/menu/bg_space_1.png"));
        imgBack.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        splashTime = System.currentTimeMillis() + 1000;
        Timer t = new Timer();
        t.scheduleTask(new Timer.Task() {
            public void run() { /* some code */
                game.ressourcesInit();
                game.custominit();
            }
        }, /* Note that libgdx uses float seconds, not integer milliseconds: */ 1.5f);

    }

    @Override
    public void dispose() {

        batch.dispose();
        splashImage.dispose();

    }

    @Override
    public void render() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.8f, 1f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!translated) {
            camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
            translated = true;
        }
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBack, 0, 0, w, h, 0, 20, 12, 0);
        batch.draw(splashImage, w / 2 - 512 / 2, h / 2 - 512 / 2);
        if (System.currentTimeMillis() > splashTime - 1000 && splashTime > 0) {
            graphicTools.isLoadingImg(batch, (int) (w - 48) / 2, (int) 100);
        }
        if (System.currentTimeMillis() > splashTime - 500 && splashTime > 0) {
            graphicTools.isLoadingTextWithAnimation(true, stage, (int) (w - 75) / 2, (int) 50, true);
        }
        batch.end();
        stage.act(delta);
        stage.draw();

        if (go) {
            if (game.mainMenuScreen != null && game.noInternetScreen != null) {
                if (game.isNetworkConnected()) {
                    game.setScreen(game.mainMenuScreen);
                    return;

                } else {
                    game.noInternetScreen.setNextScreen(game.mainMenuScreen);
                    game.setScreen(game.noInternetScreen);
                    return;

                }
            }
        }
        if (System.currentTimeMillis() > splashTime && splashTime > 0) {
            go = true;
        }

        if (Gdx.input.justTouched()) {
            go = true;
        }

    }

    @Override
    public void show() {
        game.toggleFullScreen();
        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

    }

    @Override
    public void hide() {

    }

    @Override
    public void create() {

    }

}
