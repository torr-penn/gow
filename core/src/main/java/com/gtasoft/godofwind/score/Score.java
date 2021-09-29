package com.gtasoft.godofwind.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;


import com.gtasoft.godofwind.options.Options;
import com.gtasoft.godofwind.ressource.Point;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Score {
    public static String FILENAMESCORE = "highscore.dat";
    private long position = 0;
    private long score = 0;
    private int bounce = 0;
    private String name = "Anonymous";

    private long starttime = 0;
    private long stoptime = 0;
    private long level = 1;
    private int nbFound = 1;
    private long increment = 1;
    private long decrement = 10;

    public Score(Options op, String name) {
        level = op.getUser().getPrefLevel();

        nbFound = 1;
        increment = increment * level + level - 1;
        decrement = decrement * 2;
        this.name = name;
    }

    public Score(Options op) {


        level = op.getUser().getPrefLevel();
        nbFound = 1;
        increment = increment * level + level - 1;
        decrement = decrement * 2;
        name = "!test";


    }

    public Score() {


        level = 1;


        nbFound = 1;
        increment = increment * level + level - 1;
        decrement = decrement * 2;
        name = "!test";

    }

    public static void writeFile(String fileName, String s) {
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(s, true);
        System.out.println(" write in " + fileName + " the string [" + s + "]");
    }

    public static String format(final String format, final String... args) {
        String[] split = format.split("%s");
        final StringBuffer msg = new StringBuffer();
        for (int pos = 0; pos < split.length - 1; pos += 1) {
            msg.append(split[pos]);
            msg.append(args[pos]);
        }
        msg.append(split[split.length - 1]);
        if (args.length == split.length) msg.append(args[args.length - 1]);
        return msg.toString();
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public void start() {
        starttime = System.currentTimeMillis();
    }

    public long getTime() {
        long diff;
        if (stoptime == 0) {
            diff = System.currentTimeMillis() - starttime;
        } else {
            diff = stoptime - starttime;
        }
        diff = Math.round(diff / (100));
        return diff;
    }

    public long getDiffMs() {
        long diff;
        if (stoptime == 0) {
            diff = System.currentTimeMillis() - starttime;
        } else {
            diff = stoptime - starttime;
        }
        return diff;
    }

    public long getFullTime() {
        long diff;
        if (stoptime == 0) {
            diff = System.currentTimeMillis() - starttime;
        } else {
            diff = stoptime - starttime;
        }
        return diff;
    }

    public long getScoreTime() {
        long diff;
        if (stoptime == 0) {
            diff = System.currentTimeMillis() - starttime;
        } else {
            diff = stoptime - starttime;
        }
        diff = Math.round(diff / (100));
        return diff * level / 5;
    }

    public String printScore() {
        if (stoptime == 0) {
            return (Math.max(0, score - (getTime() * level / 5))) + "";
        } else {
            return score + "";
        }
    }

    public String printDiff() {
        long diff;
        if (stoptime == 0) {
            diff = System.currentTimeMillis() - starttime;
        } else {
            diff = stoptime - starttime;
        }
        long secs = diff / 1000;
        long milisec = diff - (Math.round(secs) * 1000);
        //String display = String.format("%02d:%02d:%02d.%03d", secs / 3600, (secs % 3600) / 60, (secs % 60), milisec);

        String display = format("%s:%s:%s.%s", pad2Number(secs / 3600), pad2Number((secs % 3600) / 60), pad2Number(secs % 60), pad3Number(milisec));
        return display;
    }

    public String printDiffWithoutMs() {
        long diff;
        if (stoptime == 0) {
            diff = System.currentTimeMillis() - starttime;
        } else {
            diff = stoptime - starttime;
        }
        long secs = diff / 1000;
        long milisec = diff - (Math.round(secs) * 1000);
//        String display = String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, (secs % 60));

        String display = format("%s:%s:%s", pad2Number(secs / 3600), pad2Number((secs % 3600) / 60), pad2Number(secs % 60));
        return display;
    }

    public String pad2Number(float f) {
        int i = (int) Math.floor(f);
        if (i < 10) {
            return "0" + i;
        }
        if (i > 99) {
            Gdx.app.log("[pad2Number]", " warning number too big");
            return "" + i;
        }
        return "" + i;
    }

    public String pad3Number(float f) {
        int i = (int) Math.floor(f);
        if (i < 10) {
            return "00" + i;
        }
        if (i < 100) {
            return "0" + i;
        }
        if (i < 1000) {
            return "" + i;
        }
        Gdx.app.log("[pad3Number]", "warning number too big");

        return "" + i;
    }

    public void fail() {
        bounce = bounce + 1;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getNbFound() {
        return nbFound;
    }

    public void endGame() {
        stoptime = System.currentTimeMillis();
    }


    public int getBounce() {
        return bounce;
    }

    public void setBounce(int bounce) {
        this.bounce = bounce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStoptime() {
        return stoptime;
    }

    public void setStoptime(long stoptime) {
        this.stoptime = stoptime;
    }


    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }


}
