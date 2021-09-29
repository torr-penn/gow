package com.gtasoft.godofwind.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.godofwind.GodOfWind;
import com.gtasoft.godofwind.ressource.Point;
import com.gtasoft.godofwind.score.Score;


public class StartScreen implements Screen, ApplicationListener, InputProcessor {
    public final static int LVLMAX = 8;
    private static ImageButton btnPlay;
    private static ImageButton btnBack;
    private static ImageButton btnNext;
    private static ImageButton btnPrevious;
    private static ImageButton btnNoMusic;
    private static ImageButton btnMusic;

    Label lbl_title;
    Label lbl_level;
    Label lbl_def_wind;
    Stage stage;
    Label lbl_play;
    Skin skin;
    Texture imgLevel;
    Texture imgHelm;
    Texture imgHelmArrows;
    Sprite helmrotation;

    int w;
    int h;
    boolean dragging = false;
    Point lastPointer;
    float stateTime;

    GodOfWind game;
    boolean translated = false;
    InputMultiplexer multiplexer;
    float startAngle = 0;//(rad)
    WindManager wm;
    private int level;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;
    private int createdLvl = 0;


    public StartScreen(GodOfWind game, int level) {

        multiplexer = new InputMultiplexer();
        this.game = game;
        this.setLevel(level);
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 768, camera);
        wm = new WindManager();


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
        createdLvl = level;
        stateTime = 0f;
        sb = new SpriteBatch();

        skin = game.getGT().getSkin();

        ImageButton.ImageButtonStyle ibtnStylePlay = new ImageButton.ImageButtonStyle();
        ibtnStylePlay.up = skin.getDrawable("imgPlay");
        btnPlay = new ImageButton(ibtnStylePlay);


        btnBack = new ImageButton(skin, "back");
        btnNext = new ImageButton(skin, "next");
        btnPrevious = new ImageButton(skin, "previous");
        btnMusic = new ImageButton(skin, "music");
        btnNoMusic = new ImageButton(skin, "nomusic");

        Label.LabelStyle lblStyleMe = new Label.LabelStyle();
        lblStyleMe.fontColor = Color.DARK_GRAY;
        lblStyleMe.font = this.skin.getFont("babel");

        imgHelm = new Texture(Gdx.files.internal("img/board/helm_" + game.getLu().helmName(getLevel()) + ".png"));
        imgLevel = new Texture(Gdx.files.internal("img/board/preview_lvl" + getLevel() + ".png"));
        imgHelmArrows = new Texture(Gdx.files.internal("img/board/arrows.png"));
        helmrotation = new Sprite(imgHelm, 240, 240);
        helmrotation.setOrigin(120, 120);
        helmrotation.setOriginBasedPosition(w / 5 + 120, h / 4 + 120);
        stage = new Stage();


        int wmiddle = (int) w / 2;
        int hmiddle = (int) h / 2;


        lbl_title = new Label("GOD OF WIND", skin, "title");

        lbl_title.setAlignment(Align.center);
        lbl_title.setPosition(w / 2, h - lbl_title.getHeight() + 10, Align.center);

        lbl_level = new Label("LEVEL " + getLevel(), skin, "subtitle");

        lbl_level.setColor(skin.getColor("orange"));
        lbl_level.setAlignment(Align.center);
        lbl_level.setPosition(w / 2, h - 150 - lbl_level.getHeight(), Align.center);


        lbl_def_wind = new Label("Define Wind direction for that game", skin, "btn-explain");
        lbl_def_wind.setAlignment(Align.center);
        lbl_def_wind.setPosition(w / 5 + imgHelm.getWidth() / 2, h / 4 - 25, Align.center);


        btnBack.setSize(32, 32);
        btnBack.setPosition(w - 24 - 32, h - 24 - 32);
        btnBack.addListener(new ClickListener() {
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

        btnPlay.setPosition(wmiddle - 48, hmiddle);
        btnPlay.setSize(96, 96);
        btnPlay.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                Gdx.gl.glClearColor(0f, 0f, 0f, 0.22f);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                readyTogo();
                game.setScreen(game.gameScreen);

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });

        btnNext.setPosition(wmiddle + 104, hmiddle + hmiddle / 2 - 30);
        btnNext.setSize(64, 64);
        btnNext.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                changeLevel(1);

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });
        btnPrevious.setPosition(wmiddle - 168, hmiddle + hmiddle / 2 - 30);
        btnPrevious.setSize(64, 64);
        btnPrevious.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                changeLevel(-1);

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });

        btnMusic.setPosition(10, h - 10 - 32);
        btnMusic.setSize(32, 32);
        btnMusic.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                game.toggleMusic();
                if (stage.getActors().contains(btnMusic, true)) {
                    btnMusic.remove();
                }
                stage.addActor(btnNoMusic);

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });
        btnNoMusic.setPosition(10, h - 10 - 32);
        btnNoMusic.setSize(32, 32);
        btnNoMusic.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                game.toggleMusic();
                if (stage.getActors().contains(btnNoMusic, true)) {
                    btnNoMusic.remove();
                }
                stage.addActor(btnMusic);
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

        lbl_play = new Label("Play!", skin);
        lbl_play.setAlignment(Align.center);
        lbl_play.setPosition((w / 2 - 20), h / 2 + btnPlay.getHeight() + 10);
        lbl_play.setStyle(lblStylePlay);

        lbl_play.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                Gdx.gl.glClearColor(0f, 0f, 0f, 0.22f);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                readyTogo();
                game.setScreen(game.gameScreen);

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        stage.setViewport(viewport);
        stage.addActor(btnPlay);
        stage.addActor(btnBack);
        stage.addActor(btnNext);
        stage.addActor(btnPrevious);
        stage.addActor(lbl_play);
        stage.addActor(lbl_title);
        stage.addActor(lbl_level);
        stage.addActor(lbl_def_wind);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this); // Your screen
        Gdx.input.setInputProcessor(multiplexer);


    }

    public void changeLevel(int ldiff) {

        int lvlMin = 0;
        int nblvl = LVLMAX - lvlMin;
        if (getLevel() == lvlMin && ldiff < 0) {
            game.snd_bong[2].play();
            return;
        }

        setLevel((ldiff + (getLevel())) % (nblvl + 1));

        imgHelm = new Texture(Gdx.files.internal("img/board/helm_" + game.getLu().helmName(getLevel()) + ".png"));
        imgLevel = new Texture(Gdx.files.internal("img/board/preview_lvl" + getLevel() + ".png"));
        helmrotation.setRegion(imgHelm);

        lbl_level.setText("LEVEL " + getLevel());
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
        sb.draw(imgHelmArrows, w / 5, h / 4);

        if (startAngle == 0) {
            sb.draw(imgHelm, w / 5, h / 4);
        } else {
            helmrotation.draw(sb);
        }

        sb.draw(imgLevel, 3 * w / 5, h / 4);
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
        wm = new WindManager();
        if (sb == null || (createdLvl != level)) {
            create();
        }
        game.setScore(new Score(game.getOp()));
        helmrotation.setRotation(0);
        if (game.isPlayMusic()) {
            stage.addActor(btnMusic);
            if (stage.getActors().contains(btnNoMusic, true)) {
                btnNoMusic.remove();
            }
        } else {
            stage.addActor(btnNoMusic);
            if (stage.getActors().contains(btnMusic, true)) {
                btnMusic.remove();
            }

        }
        Gdx.input.setInputProcessor(multiplexer);
        game.gameScreen.setWorld(null);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.mainMenuScreen);
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            game.toggleMusic();
            if (game.isPlayMusic()) {
                stage.addActor(btnMusic);
                if (stage.getActors().contains(btnNoMusic, true)) {
                    btnNoMusic.remove();
                }
            } else {
                stage.addActor(btnNoMusic);
                if (stage.getActors().contains(btnMusic, true)) {
                    btnMusic.remove();
                }

            }


            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            changeLevel(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            changeLevel(-1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            helmrotation.rotate(2f);
            startAngle = helmrotation.getRotation() % 360;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            helmrotation.rotate(-2f);
            startAngle = helmrotation.getRotation() % 360;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {

            helmrotation.rotate(0.1f);
            startAngle = helmrotation.getRotation() % 360;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            helmrotation.rotate(-0.1f);
            startAngle = helmrotation.getRotation() % 360;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            readyTogo();
            game.setScreen(game.gameScreen);
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            helmrotation.rotate(-45f);
            startAngle = helmrotation.getRotation() % 360;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            helmrotation.rotate(-90f);
            startAngle = helmrotation.getRotation() % 360;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
            helmrotation.rotate(90f);
            startAngle = helmrotation.getRotation() % 360;
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

        return false;
    }

    @Override
    public boolean touchDown(int scX, int scY, int pointer, int button) {

        Vector2 mousePos = viewport.unproject(new Vector2(scX, scY));


        int screenX = Math.round(mousePos.x);
        int screenY = (int) Math.round(mousePos.y);
        if ((screenX > w / 5) && (screenX < w / 5 + 240) && screenY > (h / 4) && screenY < (h / 4 + 240)) {
            //System.out.println(" starting dragging ");
            dragging = true;
            lastPointer = new Point(screenX, screenY);
            return true;
        } else {
            dragging = false;
            lastPointer = null;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        dragging = false;
        lastPointer = null;
        return true;
    }

    @Override
    public boolean touchDragged(int scX, int scY, int pointer) {
        if (dragging) {
            Vector2 mousePos = viewport.unproject(new Vector2(scX, scY));


            int screenX = Math.round(mousePos.x);
            int screenY = (int) Math.round(mousePos.y);
            if (lastPointer != null) {
                double dist = Math.sqrt((screenX - lastPointer.getX()) * (screenX - lastPointer.getX()) +
                        (screenY - lastPointer.getY()) * (screenY - lastPointer.getY()));
                boolean clockwise = true;
                if (Math.abs(screenX - lastPointer.getX()) > Math.abs(screenY - lastPointer.getY())) {
                    if (screenY > (h / 4 + 120)) { //top
                        if (screenX < lastPointer.getX()) {
                            clockwise = false;
                        }
                    } else {
                        if (screenX > lastPointer.getX()) {
                            clockwise = false;
                        }
                    }
                } else {
                    if (screenX > w / 5 + 120) { // right
                        if (screenY > lastPointer.getY()) {
                            clockwise = false;
                        }
                    } else {
                        if (screenY < lastPointer.getY()) {
                            clockwise = false;
                        }
                    }
                }

                if (dist > 2) {
                    lastPointer = new Point(screenX, screenY);


                    if (clockwise) {
                        helmrotation.rotate(-0.05f * ((int) Math.round(dist)));
                    } else {
                        helmrotation.rotate(+0.05f * ((int) Math.round(dist)));
                    }
                    startAngle = helmrotation.getRotation() % 360;
                    //System.out.println(" as of now : rotation is : " + startAngle);
                }
            }
        }

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

    public void readyTogo() {
        float rotDeg = helmrotation.getRotation() % 360;
        game.getLu().setupWind(getLevel(), wm, rotDeg);

        game.gameScreen.setWm(wm);
        game.gameScreen.setLevel(getLevel());
        game.getScore().setLevel(level);
        game.getScore().start();
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
