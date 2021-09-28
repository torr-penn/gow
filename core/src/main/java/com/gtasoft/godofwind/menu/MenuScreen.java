package com.gtasoft.godofwind.menu;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.gtasoft.godofwind.game.help.HelpDialog;
import com.gtasoft.godofwind.game.help.PreferenceDialog;
import com.gtasoft.godofwind.ressource.LanguageManager;

public class MenuScreen implements Screen, ApplicationListener, InputProcessor {
    private static final int MenuScreenID = 100;
    private static ImageButton btnPlay;
    private static ImageButton btnExit;
    private static ImageButton btnSettings;
    private static ImageButton btnHelp;
    private static ImageButton btnRanking;
    Label lbl_title;
    Stage stage;
    Label lbl_version;
    Label lbl_play;
    Skin skin;
    Texture imgSettings;
    Texture imgRanking;
    Texture imgHelp;
    int w;
    int h;
    float stateTime;
    LanguageManager lang;
    GodOfWind game;
    boolean translated = false;
    InputMultiplexer multiplexer;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;


    public MenuScreen(GodOfWind game) {
        multiplexer = new InputMultiplexer();
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 768, camera);

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
        lang = LanguageManager.getInstance();

        skin = game.getGT().getSkin();

        ImageButton.ImageButtonStyle ibtnStylePlay = new ImageButton.ImageButtonStyle();
        ibtnStylePlay.up = skin.getDrawable("imgPlay");
        btnPlay = new ImageButton(ibtnStylePlay);


        ImageButton.ImageButtonStyle ibtnStyle = new ImageButton.ImageButtonStyle();
        ibtnStyle.up = skin.getDrawable("imgExit");
        btnExit = new ImageButton(ibtnStyle);

        imgSettings = new Texture(Gdx.files.internal("img/menu/settings-icon.png"));
        skin.add("imgSettings", imgSettings);
        ImageButton.ImageButtonStyle ibtnStyleSettings = new ImageButton.ImageButtonStyle();
        ibtnStyleSettings.up = skin.getDrawable("imgSettings");
        btnSettings = new ImageButton(ibtnStyleSettings);

        imgHelp = new Texture(Gdx.files.internal("img/menu/help-icon.png"));
        skin.add("imgHelp", imgHelp);
        ImageButton.ImageButtonStyle ibtnStyleHelp = new ImageButton.ImageButtonStyle();
        ibtnStyleHelp.up = skin.getDrawable("imgHelp");
        btnHelp = new ImageButton(ibtnStyleHelp);

        imgRanking = new Texture(Gdx.files.internal("img/menu/ranking-icon.png"));
        skin.add("imgRanking", imgRanking);
        ImageButton.ImageButtonStyle ibtnStyleRanking = new ImageButton.ImageButtonStyle();
        ibtnStyleRanking.up = skin.getDrawable("imgRanking");
        btnRanking = new ImageButton(ibtnStyleRanking);


        Label.LabelStyle lblStyleMe = new Label.LabelStyle();
        lblStyleMe.fontColor = Color.DARK_GRAY;
        lblStyleMe.font = this.skin.getFont("junction");


        lbl_version = new Label(game.getVersion(), skin);
        lbl_version.setPosition(20, 20);
        lbl_version.setStyle(lblStyleMe);

        stage = new Stage();


        int wmiddle = (int) w / 2;
        int hmiddle = (int) h / 2;


        lbl_title = new Label("GOD OF WIND", skin, "title");

        lbl_title.setAlignment(Align.center);
        lbl_title.setPosition(w / 2, h - lbl_title.getHeight() + 10, Align.center);

        btnExit.setSize(32, 32);
        btnExit.setPosition(w - 24 - 32, h - 24 - 32);
        btnExit.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                game.dispose();
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
                game.setScreen(game.startScreen);

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });
        btnHelp.setSize(96, 96);
        btnHelp.setPosition(w / 2 - 296, hmiddle - 200);
        btnHelp.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {

                new HelpDialog("How to play?", skin, "default").show(stage);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });
        btnSettings.setSize(96, 96);
        btnSettings.setPosition(w / 2 + 200, hmiddle - 200);
        btnSettings.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                new PreferenceDialog("Super Advanced Settings system", skin, "default").show(stage);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });
        btnRanking.setSize(96, 96);
        btnRanking.setPosition(w / 2 - 48, hmiddle - 200);
        btnRanking.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                game.setScreen(game.scoreScreen);
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
                game.setScreen(game.startScreen);

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        stage.setViewport(viewport);
        stage.addActor(btnPlay);
        stage.addActor(btnHelp);
        stage.addActor(btnSettings);
        stage.addActor(btnRanking);
        stage.addActor(btnExit);
        stage.addActor(lbl_version);
        stage.addActor(lbl_play);
        stage.addActor(lbl_title);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this); // Your screen
        Gdx.input.setInputProcessor(multiplexer);
//        Gdx.input.setInputProcessor(this.stage);

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
        if (sb == null) {

            create();
        }

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
