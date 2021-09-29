package com.gtasoft.godofwind.splash;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class NoInternetScreen implements Screen, ApplicationListener {
    private static ImageButton btnExit;
    private static ImageButton btnStart;
    private static ImageButton btnRestart;
    GodOfWind game;
    Stage stage;
    float w;
    float h;

    Texture imgWifi;
    Texture imgWifiup;
    Texture imgTitle;
    Label presentation;
    Label lblConnect;
    Label lblPlayLocal;
    boolean connected = false;
    Skin skin;
    boolean translated = false;
    private SpriteBatch sb;
    private int actionCode = 0;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private Screen nextScreen;


    public NoInternetScreen(GodOfWind game) {
        w = 1280;
        h = 768;
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 768, camera);


    }

    private void performAction() {
        if (actionCode == 0) {
            return;
        }
        if (actionCode == 2) {
            if (game.isNetworkConnected()) {
                presentation.setText("Connected");
                presentation.setColor(Color.GREEN);
                connected = true;
                float fpw = presentation.getPrefWidth();
                presentation.setPosition(w * 0.5f - fpw / 2f, h / 4f);
                btnStart.setPosition(w / 2 - 48, h / 2);
                //stage.addActor(presentation);
                stage.addActor(btnStart);
                lblPlayLocal.setPosition(w / 2, h / 2 + btnStart.getHeight() + 25, Align.center);
                //stage.addActor(btnExit);
                btnRestart.remove();
                lblConnect.remove();
                lblPlayLocal.setText("Play!");
            } else {

                GodOfWind.goodSound.play();
            }
            actionCode = 0;
            return;
        }

        if (actionCode == 4) {
            game.setScreen(this.getNextScreen());
            return;
        }

        if (actionCode == 3) {
            game.setScreen(this.getNextScreen());
            return;
        }

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void create() {
        skin = game.getGT().getSkin();
        imgWifi = new Texture(Gdx.files.internal("img/menu/wifi-down.png"));
        imgWifiup = new Texture(Gdx.files.internal("img/menu/wifi-up.png"));

        imgTitle = new Texture(Gdx.files.internal("img/menu/title-menu.png"));


        skin.add("imgwifi", imgWifi);
        skin.add("imgwifiup", imgWifiup);
        connected = false;

        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = this.skin.getFont("explanation");

        ImageButton.ImageButtonStyle ibtnexit = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle ibtnStyleStart = new ImageButton.ImageButtonStyle();

        ibtnStyleStart.up = skin.getDrawable("imgPlay");
        btnStart = new ImageButton(ibtnStyleStart);

        ibtnexit.up = skin.getDrawable("imgExit");
        btnExit = new ImageButton(ibtnexit);

        ImageButton.ImageButtonStyle ibtnStyle = new ImageButton.ImageButtonStyle();
        ibtnStyle.up = skin.getDrawable("imgwifi");

        ImageButton.ImageButtonStyle ibtnStyleup = new ImageButton.ImageButtonStyle();
        ibtnStyleup.up = skin.getDrawable("imgwifiup");

        ImageButton.ImageButtonStyle ibtnStyleRestart = new ImageButton.ImageButtonStyle();
        ibtnStyleRestart.up = skin.getDrawable("imgRestart");
        setNextScreen(game.mainMenuScreen);
        btnRestart = new ImageButton(ibtnStyleRestart);
        btnStart.setPosition((w / 2) + 200, h / 4);
        btnStart.setSize(96, 96);
        btnStart.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                actionCode = 3;
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }
        });

        sb = new SpriteBatch();

        stage = new Stage();
        stage.setViewport(viewport);
        presentation = new Label("No Internet", skin);
        presentation.setStyle(lblStyle);
        float fpw = presentation.getMinWidth();
        presentation.setPosition(w * 0.5f - fpw / 2f, h - h / 4f - 100);
        presentation.setSize(fpw + 20, 120);
        btnRestart.setPosition((w / 2) - 248, h / 4);
        btnRestart.setSize(96, 96);
        btnRestart.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                actionCode = 2;
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;
            }
        });


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


        Label.LabelStyle lblStylePlay = new Label.LabelStyle();
        lblStylePlay.fontColor = Color.WHITE;
        lblStylePlay.font = this.skin.getFont("btn_menu");

        lblConnect = new Label("Connect", skin);
        lblConnect.setAlignment(Align.center);
        lblConnect.setPosition((w / 2) - 240, h / 4 + btnRestart.getHeight() + 10);
        lblConnect.setStyle(lblStylePlay);

        lblPlayLocal = new Label("Play Local", skin);
        lblPlayLocal.setAlignment(Align.center);
        lblPlayLocal.setPosition((w / 2 + 200), h / 4 + btnStart.getHeight() + 10);
        lblPlayLocal.setStyle(lblStylePlay);

        stage.addActor(lblConnect);
        stage.addActor(lblPlayLocal);
        stage.addActor(presentation);
        stage.addActor(btnStart);
        stage.addActor(btnRestart);
        stage.addActor(btnExit);
    }

    @Override
    public void render() {


    }

    @Override
    public void render(float delta) {

        performAction();
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
        sb.draw(imgTitle, (int) ((w - imgTitle.getWidth()) / 2), h - imgTitle.getHeight() - 50);
        if (connected) {
            sb.draw(imgWifiup, (int) ((w - 96) / 2), h / 5 - 20);
        } else {
            sb.draw(imgWifi, (int) ((w - 96) / 2), h - h / 2 + 40);
        }

        sb.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        if (sb == null) {
            create();
        }
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {


    }


    public Screen getNextScreen() {
        return nextScreen;
    }

    public void setNextScreen(Screen nextScreen) {
        this.nextScreen = nextScreen;
    }
}
