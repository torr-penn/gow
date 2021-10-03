package com.gtasoft.godofwind.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Options {


    public final static int NOSOUND = 1;
    public final static int SOUNDZIC = 2;
    public final static int SOUND = 3;
    public final static int ZIC = 4;
    private static String OP_FNAME = "preferences.dat";
    public int difficulty = 1;
    GameplayMode gameplay = GameplayMode.PC;
    ErrorMode error = ErrorMode.FUN;
    //  int sound = 3;
    boolean offline = false;
    boolean playSound = true;
    private User user;
    private float helmRotation = 0f;

    public Options() {
        this.loadPrefs();
    }

    public Options(int difficulty) {
        this.difficulty = difficulty;
        // loadPrefs();
    }


    public static void writeFile(String fileName, String s) {

        if (Gdx.files == null) {
            System.out.println("--error -   no GDX file handler  exist at this stage");
        }
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            //System.out.println("--- Writing to " + fileName + " exists");
            file.writeString(s, false);
        } else {
            System.out.println("--- NO " + fileName + " SO CREATION OF FILE");
            file.writeString(s, false);
        }

    }


    public boolean isAudible() {
        return playSound;
    }

    public void setPlaySound(boolean b) {
        playSound = b;
    }


    public void loadPrefs() {

        String name = "torr-penn";
        user = new User(name);
        user.setPrefLevel(0);
        user.setConsentGDPR(false);


    }


    public boolean saveUsers() {
        return true;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;

    }


    public GameplayMode getGameplay() {
        return this.gameplay;
    }

    public void setGameplay(GameplayMode gameplay) {
        this.gameplay = gameplay;
    }

    public ErrorMode getError() {
        return this.error;
    }

    public void setError(ErrorMode error) {
        this.error = error;
    }


    public boolean isOffline() {
        return this.offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getHelmRotation() {
        return helmRotation;
    }

    public void setHelmRotation(float helmRotation) {
        this.helmRotation = helmRotation;
    }


    public enum GameplayMode {
        PC, TABLET
    }

    public enum ErrorMode {
        HARD, MULTIPLE, FUN
    }
}
