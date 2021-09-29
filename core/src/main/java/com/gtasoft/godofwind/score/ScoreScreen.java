package com.gtasoft.godofwind.score;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.godofwind.GodOfWind;
import com.gtasoft.godofwind.game.StartScreen;

public class ScoreScreen implements Screen, ApplicationListener, InputProcessor {
    private static final int MenuScreenID = 100;

    private static ImageButton btnExit;
    Label lbl_title;
    Label lbl_score;
    Stage stage;

    Skin skin;
    int w;
    int h;
    float stateTime;

    GodOfWind game;
    boolean translated = false;
    InputMultiplexer multiplexer;
    int nbStar[] = new int[StartScreen.LVLMAX + 1];
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;

    // constructor to keep a reference to the main Game class
    public ScoreScreen(GodOfWind game) {
        multiplexer = new InputMultiplexer();
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 768, camera);
        for (int i = 0; i <= StartScreen.LVLMAX; i++) {
            nbStar[i] = -1;
        }

    }


    @Override
    public void dispose() {
        sb.dispose();
        stage.dispose();


        multiplexer.clear();

    }

    @Override
    public void create() {
        w = 1280;
        h = 768;
        stateTime = 0f;
        sb = new SpriteBatch();


        skin = game.getGT().getSkin();


        ImageButton.ImageButtonStyle ibtnStyle = new ImageButton.ImageButtonStyle();
        ibtnStyle.up = skin.getDrawable("imgBack");
        btnExit = new ImageButton(ibtnStyle);


        Label.LabelStyle lblStyleMe = new Label.LabelStyle();
        lblStyleMe.fontColor = Color.DARK_GRAY;
        lblStyleMe.font = this.skin.getFont("junction");


        stage = new Stage();


        int wmiddle = (int) w / 2;
        int hmiddle = (int) h / 2;


        lbl_title = new Label("GOD OF WIND", skin, "title");

        lbl_title.setAlignment(Align.center);
        lbl_title.setPosition(w / 2, h - lbl_title.getHeight() + 10, Align.center);

        lbl_score = new Label(" YOUR BEST PERFORMANCES ", skin, "subtitle");
        //lbl_level.setStyle(lblStyleTitle);
        lbl_score.setColor(skin.getColor("orange"));
        lbl_score.setAlignment(Align.center);
        lbl_score.setPosition(w / 2, h - 100 - lbl_score.getHeight(), Align.center);

        btnExit.setSize(64, 64);
        btnExit.setPosition(w - 24 - 64, h - 24 - 64);
        btnExit.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                game.setScreen(game.mainMenuScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });

        Label.LabelStyle lblStylePlay = new Label.LabelStyle();
        lblStylePlay.fontColor = skin.getColor("darkgreen");
        lblStylePlay.font = this.skin.getFont("monofont");
        stage.setViewport(viewport);

        for (int i = 0; i <= StartScreen.LVLMAX; i++) {
            Score s = game.getScoreUtil().getTop(i);
            if (s == null) {
                Label l = new Label("Level : " + i + " \t  --- ", skin, "bounce");
                l.setPosition(40, h - 200 - 50 * i);
                stage.addActor(l);
            } else {
                Label l = new Label("Level : " + i + " \t bounce : " + s.getBounce() + " \t time " + s.printDiff(), skin, "bounce");
                l.setPosition(40, h - 200 - 50 * i);
                stage.addActor(l);
                if (s.getBounce() == 0) {
                    nbStar[i] = 1;
                    if (game.getLu().getStarTwoTime(i) >= s.getDiffMs()) {
                        nbStar[i] = nbStar[i] + 1;
                    }
                    if (game.getLu().getStarThreeTime(i) >= s.getDiffMs()) {
                        nbStar[i] = nbStar[i] + 1;
                    }
                }
            }
        }

        stage.addActor(btnExit);
        stage.addActor(lbl_title);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this); // Your screen
        Gdx.input.setInputProcessor(multiplexer);


    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 0.22f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!translated) {
            camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
            translated = true;
        }

        camera.update();
        sb.setProjectionMatrix(camera.combined);


        sb.begin();
        sb.draw(game.getGT().getImgBackground(), 0, 0, w, h, 0, 20, 12, 0);
        for (int i = 0; i < StartScreen.LVLMAX + 1; i++) {

            int nbstar = nbStar[i];
            if (nbstar != -1) {
                for (int z = 0; z < 3; z++) {
                    if (nbstar > z) {
                        sb.draw(game.getGT().getImgStar(), 100 + game.getW() / 2 + z * (1.5f * game.getGT().getImgStar().getWidth()), h - 200 - 50 * i);
                    } else {
                        sb.draw(game.getGT().getImgNoStar(), 100 + game.getW() / 2 + z * (1.5f * game.getGT().getImgStar().getWidth()), h - 200 - 50 * i);
                    }
                }
            }
        }


        sb.end();
        stage.act(delta);
        stage.draw();
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
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);

        create();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.Q) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.dispose();
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(game.startScreen);
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            game.toggleMusic();
            return true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }
}
