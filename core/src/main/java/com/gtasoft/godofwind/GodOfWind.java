package com.gtasoft.godofwind;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import com.gtasoft.godofwind.game.StartScreen;
import com.gtasoft.godofwind.game.utils.LevelUtil;
import com.gtasoft.godofwind.options.Options;
import com.gtasoft.godofwind.ressource.*;
import com.gtasoft.godofwind.score.Score;
import com.gtasoft.godofwind.score.ScoreScreen;
import com.gtasoft.godofwind.splash.NoInternetScreen;
import com.gtasoft.godofwind.splash.SplashScreen;
import com.gtasoft.godofwind.game.GameScreen;
import com.gtasoft.godofwind.menu.MenuScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class GodOfWind extends Game implements ApplicationListener {

    static public Sound snd_top1 = null;
    static public Sound snd_top2 = null;
    static public Sound snd_top3 = null;
    static public Sound snd_top5 = null;
    static public Sound snd_top10 = null;
    static public Sound snd_local = null;

    static public Sound snd_bong[] = null;
    static public Sound snd_victory[] = null;


    static public Sound number_1 = null;
    static public Sound number_2 = null;
    static public Sound number_3 = null;

    static public Sound failSound = null;
    static public Sound goodSound = null;
    static public Sound foundSound = null;
    static public Sound crocSound = null;
    static public Sound explodeSound = null;
    static public Music gameMusic;

    //   public IGameServiceClient gsClient;
    public static int STATUS_OFF = -2;
    public static int STATUS_INITOK = -1;
    public static int STATUS_READY = 0;
    public MenuScreen mainMenuScreen;
    public NoInternetScreen noInternetScreen;
    public GameScreen gameScreen;
    public SplashScreen splashScreen;
    public StartScreen startScreen;
    public ScoreScreen scoreScreen;

    public NetworkTools ntools;
    public Options op;
    private IScoreUtil scoreUtil;
    private LevelUtil lu;

    private NativePlatform np = null;
    private GraphicTools gt;
    private int w = 1280;
    private int h = 768;
    private Score score;
    private boolean playMusic = false;

    public GodOfWind(NativePlatform np) {

        this.np = np;


    }

    public GraphicTools getGT() {
        return gt;
    }

    /*

     */
    @Override
    public void create() {
        gt = new GraphicTools();
        ntools = new NetworkTools();
        loadOp();
        this.splashScreen = new SplashScreen(this);
        this.setScreen(this.splashScreen);


    }

    public boolean isAndroid() {
        if (np.getPreferredGamePlay() == Options.GameplayMode.TABLET)
            return true;

        return false;
    }

    public void loadOp() {
        if (op == null)
            this.op = new Options();

    }

    @Override
    public void resume() {


    }

    public void ressourcesInit() {


        snd_local = Gdx.audio.newSound(Gdx.files.internal("sound/win/local.ogg"));
        snd_bong = new Sound[6];
        snd_bong[0] = Gdx.audio.newSound(Gdx.files.internal("sound/game/bong1.ogg"));
        snd_bong[1] = Gdx.audio.newSound(Gdx.files.internal("sound/game/bong2.ogg"));
        snd_bong[2] = Gdx.audio.newSound(Gdx.files.internal("sound/game/bong3.ogg"));
        snd_bong[3] = Gdx.audio.newSound(Gdx.files.internal("sound/game/bong4.ogg"));
        snd_bong[4] = Gdx.audio.newSound(Gdx.files.internal("sound/game/bong5.ogg"));
        snd_bong[5] = Gdx.audio.newSound(Gdx.files.internal("sound/game/bong6.ogg"));

        snd_top1 = Gdx.audio.newSound(Gdx.files.internal("sound/win/top1.ogg"));
        snd_top2 = Gdx.audio.newSound(Gdx.files.internal("sound/win/top2.ogg"));
        snd_top3 = Gdx.audio.newSound(Gdx.files.internal("sound/win/top3.ogg"));
        snd_top5 = Gdx.audio.newSound(Gdx.files.internal("sound/win/top5.ogg"));
        snd_top10 = Gdx.audio.newSound(Gdx.files.internal("sound/win/top10.ogg"));


        snd_victory = new Sound[5];
        snd_victory[0] = Gdx.audio.newSound(Gdx.files.internal("sound/game/sploutch1.ogg"));
        snd_victory[1] = Gdx.audio.newSound(Gdx.files.internal("sound/game/sploutch2.ogg"));
        snd_victory[2] = Gdx.audio.newSound(Gdx.files.internal("sound/game/sploutch3.ogg"));
        snd_victory[3] = Gdx.audio.newSound(Gdx.files.internal("sound/game/sploutch4.ogg"));
        snd_victory[4] = Gdx.audio.newSound(Gdx.files.internal("sound/game/sploutch5.ogg"));

        number_1 = Gdx.audio.newSound(Gdx.files.internal("sound/win/number_1.ogg"));
        number_2 = Gdx.audio.newSound(Gdx.files.internal("sound/win/number_2.ogg"));
        number_3 = Gdx.audio.newSound(Gdx.files.internal("sound/win/number_3.ogg"));

        failSound = Gdx.audio.newSound(Gdx.files.internal("sound/game/fail.ogg"));
        goodSound = Gdx.audio.newSound(Gdx.files.internal("sound/game/bip.ogg"));
        foundSound = Gdx.audio.newSound(Gdx.files.internal("sound/game/found.ogg"));
        crocSound = Gdx.audio.newSound(Gdx.files.internal("sound/game/croc.ogg"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/game/ecmContemplationAndVictory.ogg"));
        playMusic();
        explodeSound = Gdx.audio.newSound(Gdx.files.internal("sound/game/explode.ogg"));

        setScoreUtil(np.getScoreUtil());
        scoreScreen = new ScoreScreen(this);
        setLu(new LevelUtil());

    }

    public void playMusic() {
        if (gameMusic == null) {
            gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/echo_blue_music.ogg"));
        }
        gameMusic.setLooping(true);
        gameMusic.play();
        setPlayMusic(true);
    }

    public void stopMusic() {
        if (gameMusic != null) {
            gameMusic.stop();
            setPlayMusic(false);
        }

    }

    public void toggleMusic() {
        if (!isPlayMusic()) {
            playMusic();
            setPlayMusic(true);
        } else {
            stopMusic();
            setPlayMusic(false);
        }

    }

    public int initClient() {
        if (isAndroid()) {
            return initClient(true);
        } else {
            return initClient(false);
        }
    }

    public int initClient(boolean googlecheck) {
        System.out.println("//////////////// Entry in INITCLIENT");
        return 0;
    }

    public void custominit() {
        this.startScreen = new StartScreen(this, 0);
        this.gameScreen = new GameScreen(this);
        this.mainMenuScreen = new MenuScreen(this);
        this.noInternetScreen = new NoInternetScreen(this);

    }

    public boolean isNetworkConnected() {
        return ntools.isNetworkConnected();
    }


    @Override
    public void dispose() {

        snd_local.dispose();
        snd_top3.dispose();
        snd_top1.dispose();
        snd_top2.dispose();
        snd_top5.dispose();
        Gdx.app.exit();
    }

    public Options getOp() {
        return this.op;
    }

    public void setOp(Options op) {
        if (op != null) {


            this.op = op;
            op.setGameplay(np.getPreferredGamePlay());
        }

    }

    public void disconnect() {

    }


    public String getVersion() {
        return np.getVersion();
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }


    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public IScoreUtil getScoreUtil() {
        return scoreUtil;
    }

    public void setScoreUtil(IScoreUtil scoreUtil) {
        this.scoreUtil = scoreUtil;
    }

    public LevelUtil getLu() {
        return lu;
    }

    public void setLu(LevelUtil lu) {
        this.lu = lu;
    }

    public boolean isPlayMusic() {
        return playMusic;
    }

    public void setPlayMusic(boolean playMusic) {
        this.playMusic = playMusic;
    }
}
