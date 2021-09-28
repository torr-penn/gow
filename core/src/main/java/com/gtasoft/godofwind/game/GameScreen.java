package com.gtasoft.godofwind.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gtasoft.godofwind.GodOfWind;

import com.gtasoft.godofwind.game.contact.MyContactListener;
import com.gtasoft.godofwind.game.entity.Cloud;
import com.gtasoft.godofwind.game.entity.Player;
import com.gtasoft.godofwind.game.entity.WinnerZoneBody;
import com.gtasoft.godofwind.game.utils.CameraStyles;
import com.gtasoft.godofwind.game.utils.CloudManager;
import com.gtasoft.godofwind.game.utils.TiledObjectUtil;
import com.gtasoft.godofwind.ressource.Constants;

import java.util.Timer;
import java.util.TimerTask;

import static com.gtasoft.godofwind.ressource.Constants.PPM;

public class GameScreen implements Screen, ApplicationListener {


    public boolean win = false;
    InputMultiplexer multiplexer;
    private WindManager wm = new WindManager();
    private CloudManager cloudm;
    private boolean DEBUG = false;
    private OrthographicCamera camera;
    private OrthographicCamera infoCamera;
    private Box2DDebugRenderer b2dr;
    private World world;
    private Player player;

    private GodOfWind gow;
    private SpriteBatch batch;
    private SpriteBatch infobatch;
    private Texture compass;
    private Sprite rotCompass;
    private OrthogonalTiledMapRenderer otmr;
    private TiledMap map;
    private int cameraStyle;
    private Vector2 target;
    private Level currentLevel;
    private Stage stage;
    private WinnerZoneBody wzb = null;
    private FitViewport viewport;
    private Label lbl_time;
    private int level = 1;
    private float delt = 0;
    private Label lbl_victory;
    private Label lbl_bounce;
    private Label lbl_rank;
    private Label lbl_time_end;
    private ImageButton btnNext;
    private ImageButton btnBack;
    private ImageButton btnRetry;
    private int nbstar = -1;
    // private Cloud cloud1;

    public GameScreen(GodOfWind game) {
        this.gow = game;
        multiplexer = new InputMultiplexer();

    }

    public void setLevel(int lvl) {
        this.level = lvl;
    }

    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        w = gow.getW();
        h = gow.getH();
        cameraStyle = 2;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / Constants.SCALE, h / Constants.SCALE);


        infoCamera = new OrthographicCamera();
        infoCamera.setToOrtho(false, w, h);
        stage = new Stage();
        viewport = new FitViewport(w, h, infoCamera);
        stage.setViewport(viewport);

        setWorld(new World(new Vector2(0, 0), false));
        getWorld().setContactListener(new MyContactListener());
        b2dr = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        infobatch = new SpriteBatch();
        compass = new Texture("img/board/arrow_wind.png");
        rotCompass = new Sprite(compass, 64, 64);
        rotCompass.setOrigin(32, 32);
        rotCompass.setOriginBasedPosition(16 + 32, h - 64 - 16);

        currentLevel = new Level("tile/level" + level + ".tmx", new Vector2(0, 0));

        map = new TmxMapLoader().load(currentLevel.getTiledmapname());
        otmr = new OrthogonalTiledMapRenderer(map);
        TiledObjectUtil.parseTiledObjectLayer(getWorld(), map.getLayers().get("obstacle").getObjects());
        TiledObjectUtil.parseTiledColorLayer(getWorld(), map.getLayers().get("pink").getObjects(), WindManager.PINK);
        TiledObjectUtil.parseTiledColorLayer(getWorld(), map.getLayers().get("red").getObjects(), WindManager.RED);
        TiledObjectUtil.parseTiledColorLayer(getWorld(), map.getLayers().get("green").getObjects(), WindManager.GREEN);
        TiledObjectUtil.parseTiledColorLayer(getWorld(), map.getLayers().get("yellow").getObjects(), WindManager.YELLOW);
        wzb = TiledObjectUtil.parseTiledWinnerLayer(getWorld(), map.getLayers().get("end").getObjects());

        setPlayer(new Player(getWorld(), gow, level));


        wm.adjustSpeed(gow.getLu().getWindSpeed(level));
        cloudm = new CloudManager(getWorld(), gow, level);
        cloudm.setWindManager(wm);

        target = getPlayer().getPosition().scl(PPM);

        lbl_victory = new Label(" VICTORY ", gow.getGT().getSkin(), "victory");

        lbl_victory.setAlignment(Align.center);

        lbl_victory.setPosition(w / 2, h - lbl_victory.getHeight() - 10, Align.center);


        lbl_time = new Label("--:--:--", gow.getGT().getSkin(), "time");
        lbl_time.setAlignment(Align.left);
        lbl_time.setPosition(25, h - lbl_time.getHeight() - 10, Align.left);

        lbl_bounce = new Label("0", gow.getGT().getSkin(), "bounce");
        lbl_bounce.setAlignment(Align.left);
        lbl_bounce.setPosition(w / 2 + 100, h / 2, Align.left);

        lbl_time_end = new Label("--:--:--", gow.getGT().getSkin(), "bounce");
        lbl_time_end.setAlignment(Align.left);
        lbl_time_end.setPosition(w / 2 + 100, h / 2 - 60, Align.left);

        lbl_rank = new Label("0", gow.getGT().getSkin(), "bounce");
        lbl_rank.setAlignment(Align.left);
        lbl_rank.setPosition(w / 2 + 100, h / 2 + 60, Align.left);


        btnBack = new ImageButton(gow.getGT().getSkin(), "back");
        btnNext = new ImageButton(gow.getGT().getSkin(), "next");
        btnRetry = new ImageButton(gow.getGT().getSkin(), "retry");

        btnBack.setSize(32, 32);
        btnBack.setPosition(w - 24 - 32, h - 24 - 32);
        btnBack.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                gow.setScreen(gow.startScreen);

                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });
        btnNext.setSize(64, 64);
        btnNext.setPosition(w - 164, h / 6 - 32);
        btnNext.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {

                gow.startScreen.changeLevel(1);
                gow.setScreen(gow.startScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });
        btnRetry.setSize(64, 64);
        btnRetry.setPosition(w / 2 - 32, h / 6 - 32);
        btnRetry.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                gow.setScreen(gow.startScreen);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });
        stage.addActor(lbl_time);
        stage.addActor(btnBack);

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / 2, height / 2);
        viewport.update(width, height);

    }

    @Override
    public void dispose() {
        b2dr.dispose();
        otmr.dispose();
        map.dispose();
        getPlayer().dispose();
        getWorld().dispose();
        batch.dispose();
        infobatch.dispose();
        compass.dispose();

    }

    public boolean update(float delta) {

        getWorld().step(1 / 60f, 6, 2);
        cameraUpdate(delta);
        infoCamera.update();
        otmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);
        infobatch.setProjectionMatrix(infoCamera.combined);
        if (!getPlayer().isVictorious()) {
            getPlayer().moveit();

            cloudm.moveit();
            if (getPlayer().getCollisionNb() != gow.getScore().getBounce()) {
                gow.getScore().setBounce(getPlayer().getCollisionNb());
            }
        } else {
            if (!win) {
                win = true;
                processVictory();

            } else {
                cloudm.moveit();
            }
        }
        lbl_time.setText(gow.getScore().printDiffWithoutMs() + "\t" + gow.getScore().getBounce());
        rotCompass.setRotation(wm.getMainWindDirectionDegree());
        return inputUpdate(delta);

    }

    public void processVictory() {
        gow.getScore().setBounce(getPlayer().getCollisionNb());
        gow.getScore().endGame();
        int rank = gow.getScoreUtil().getLocalRank(gow.getScore());
        if (rank == 0) rank = 1;
        gow.getScoreUtil().saveScore(gow.getScore());
        lbl_bounce.setText(" Bounce \t:  " + getPlayer().getCollisionNb() + "            ");
        lbl_bounce.pack();
        lbl_time_end.setText(" Time   \t: " + gow.getScore().printDiff() + "  ");
        lbl_time_end.pack();
        lbl_rank.setText(" Rank   \t: # " + (rank) + "           ");
        lbl_rank.pack();
        stage.addActor(lbl_rank);
        stage.addActor(lbl_bounce);
        stage.addActor(lbl_time_end);
        win = true;
        if (getPlayer().getCollisionNb() == 0) {
            if (!stage.getActors().contains(btnNext, true)) {
                stage.addActor(btnNext);
            }
            stage.addActor(lbl_victory);
            if (stage.getActors().contains(lbl_time, true)) {
                lbl_time.remove();
            }
            if (stage.getActors().contains(btnRetry, true)) {
                btnRetry.remove();
            }
            int i = (int) (Math.random() * 4);
            if (i == 0) {
                gow.snd_top1.play();
            } else if (i == 1) {
                gow.snd_top2.play();
            } else if (i == 2) {
                gow.snd_top3.play();
            } else if (i == 3) {
                gow.snd_top5.play();
            }
            Timer validTimer = new Timer();
            if (rank == 1) {
                validTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            gow.number_1.play();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, 1500);
            } else if (rank == 2) {
                validTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            gow.number_2.play();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, 1500);
            } else if (rank == 3) {
                validTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {

                            gow.number_3.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, 1500);
            }
        } else {
            if (!stage.getActors().contains(btnRetry, true)) {
                stage.addActor(btnRetry);
            }
            if (stage.getActors().contains(btnNext, true)) {
                btnNext.remove();
            }
            int i = (int) (Math.random() * 5);
            gow.snd_victory[i].play();
        }
        if (gow.getScore().getBounce() == 0) {
            nbstar = 1;
            if (gow.getLu().getStarTwoTime(level) > gow.getScore().getDiffMs()) {
                nbstar = nbstar + 1;
            }
            if (gow.getLu().getStarThreeTime(level) > gow.getScore().getDiffMs()) {
                nbstar = nbstar + 1;
            }

        } else {
            nbstar = 0;
        }

    }

    public boolean inputUpdate(float delta) {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
//            cameraStyle = (cameraStyle + 1) % 5;
//
//        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            gow.setScreen(gow.mainMenuScreen);
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            gow.toggleMusic();
            return true;
        }

        getPlayer().controller(delta);
        return false;
    }

    public void cameraUpdate(float delta) {

        camera.zoom = 1;
        switch (cameraStyle) {
            case 0:
                CameraStyles.lockOnTarget(camera, target);
                //cameraType = "Lock On - Room Center";
                break;
            case 1:
                CameraStyles.lerpToTarget(camera, target);
                //cameraType = "Lerp - Room Center";
                break;
            case 2:
                CameraStyles.lerpAverageBetweenTargets(camera, target, getPlayer().getPosition().scl(PPM));
                //cameraType = "Lock Average - Player/Room";
                break;
            case 3:
                boolean found = CameraStyles.searchFocalPoints(camera, currentLevel.getFocalPoints(), getPlayer().getPosition().scl(PPM), 180);
                if (!found) {
                    CameraStyles.lerpToTarget(camera, getPlayer().getPosition().scl(PPM));
                }
                //cameraType = "Focal Search - Player/Center of Room";
                break;

            case 4:
                CameraStyles.shake(camera, new Vector2(10 /
                        PPM, 0), 1);
                cameraStyle = 0;
                //cameraType = "Lock Average - Player/Room";
                break;

        }


    }


    @Override
    public void show() {
        win = false;
        nbstar = -1;
        if (getWorld() == null) {
            if (stage != null) {
                stage.clear();
            }
            create();
        }
        if (wm != null) {

            getPlayer().setWindManager(wm);
            cloudm.setWindManager(wm);
        }
        multiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render() {
        if (update(Gdx.graphics.getDeltaTime())) {
            return;
        }


        // Render
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(gow.getGT().getImgSea(), -gow.getW() / 2, -gow.getH() / 2, gow.getW() * 3, gow.getH() * 3, 0, 96, 72, 0);
        batch.end();
        if (!getPlayer().isVictorious() || gow.getScore().getBounce() > 0) {

            otmr.render();
            getPlayer().render(batch);
            cloudm.render(batch);
        }

        infobatch.begin();
        if (getPlayer().isApplyWind()) {
            if (getPlayer().isVictorious()) {
                if (gow.getScore().getBounce() != 0) {
                    rotCompass.draw(infobatch);
                }
            } else {
                rotCompass.draw(infobatch);
            }
        }
        if (getPlayer().isVictorious()) {
            for (int i = 0; i < 3; i++) {
                if (nbstar > i) {
                    infobatch.draw(gow.getGT().getImgStar(), 100 + gow.getW() / 2 + i * (1.5f * gow.getGT().getImgStar().getWidth()), 200);

                } else {
                    infobatch.draw(gow.getGT().getImgNoStar(), 100 + gow.getW() / 2 + i * (1.5f * gow.getGT().getImgStar().getWidth()), 200);
                }
            }
        }
        if (getPlayer().isVictorious() && gow.getScore().getBounce() == 0) {
            infobatch.draw(gow.getGT().getImgVictory(), gow.getW() / 3 - gow.getGT().getImgVictory().getWidth() / 2, (gow.getH() - gow.getGT().getImgVictory().getHeight()) / 2);
        } else {
            infobatch.draw(gow.getGT().getImgScoreBox(), 0, gow.getH() - 38, 100, 30);
        }
        infobatch.end();

        stage.act(delt);
        stage.draw();

    }

    @Override
    public void render(float delta) {
        render();
        delt = delta;


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    public WindManager getWm() {
        return wm;
    }

    public void setWm(WindManager wm) {
        this.wm = wm;
    }


    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
