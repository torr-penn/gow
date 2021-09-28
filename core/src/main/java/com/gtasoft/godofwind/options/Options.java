package com.gtasoft.godofwind.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gtasoft.godofwind.ressource.FileTools;

import java.util.*;

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
    boolean isPlaySound = true;
    private User user;

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


    public void displayOptions() {
        System.out.println("******** -  options here : ");

        System.out.println(" - difficulty of the board :" + this.getDifficulty());


    }

    public boolean isAudible() {
        return isPlaySound;
    }

    public void loadPrefs() {
        String name = System.getProperty("user.name");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        try {
            String fpref = FileTools.readFile(OP_FNAME);
            if (fpref != null) {
                this.user = gson.fromJson(fpref, User.class);

            }
            //    System.out.println("file " + " - preferences.dat - " + "contains : " + fpref);

        } catch (Exception e) {
            System.out.println("file " + " - preferences.dat - " + "cannot be read");
            e.printStackTrace();
        }
        if (user == null) {
            user = new User(name);
            user.setPrefLevel(1);
            user.setConsentGDPR(false);
        }

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


    public enum GameplayMode {
        PC, TABLET
    }

    public enum ErrorMode {
        HARD, MULTIPLE, FUN
    }
}
