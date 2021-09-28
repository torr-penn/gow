package com.gtasoft.godofwind.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.gtasoft.godofwind.options.Options;
import com.gtasoft.godofwind.ressource.FileTools;
import com.gtasoft.godofwind.ressource.Point;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;

public class Score {
    public static String FILENAMESCORE = "highscore.dat";
    @Expose
    private long position = 0;
    @Expose
    private long score = 0;
    @Expose
    private int bounce = 0;
    @Expose
    private String name = "Anonymous";
    @Expose
    private long time = 0;
    @Expose
    private long starttime = 0;
    @Expose
    private long stoptime = 0;
    @Expose
    private long level = 1;
    @Expose
    private boolean published = true;
    private int localRank = -1;
    private Options op = null;
    private boolean beep = true;
    private int nbFound = 1;
    private long increment = 1;
    private long decrement = 10;

    public Score(Options op, String name) {
        level = op.getUser().getPrefLevel();
        this.op = op;
        nbFound = 1;
        increment = increment * level + level - 1;
        decrement = decrement * 2;
        this.name = name;
    }

    public Score(Options op) {

        this.op = op;
        level = op.getUser().getPrefLevel();
        nbFound = 1;
        increment = increment * level + level - 1;
        decrement = decrement * 2;
        name = "!test";

        beep = false;
    }

    public Score() {
        op = new Options();

        level = 1;


        nbFound = 1;
        increment = increment * level + level - 1;
        decrement = decrement * 2;
        name = "!test";
        beep = false;
    }

    public static void writeFile(String fileName, String s) {
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(s, true);
        System.out.println(" write in " + fileName + " the string [" + s + "]");
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

    public void setTime(long time) {
        this.time = time;
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
        String display = String.format("%02d:%02d:%02d.%03d", secs / 3600, (secs % 3600) / 60, (secs % 60), milisec);
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
        String display = String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, (secs % 60));
        return display;
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


    public void initFileScore() {
        String score = FileTools.readFile(FILENAMESCORE);
        if (score == null) {
            writeFile(FILENAMESCORE, "");
            System.out.println(" --- create empty score file " + FILENAMESCORE);
        }


    }

    public void endGame() {
        stoptime = System.currentTimeMillis();
    }


    public void saveLocalScore() {
        stoptime = System.currentTimeMillis();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        FileReader fr;
        try {
            fr = new FileReader("highscore.dat");
            Score[] sc = gson.fromJson(fr, Score[].class);
            ArrayList<Score> asc = null;
            fr.close();
            if (sc != null) {
                asc = new ArrayList<Score>(Arrays.asList(sc));
            }


            if (asc != null) {
                asc.add(this);
                FileWriter fw = new FileWriter("highscore.dat", false);
                fw.append(gson.toJson(asc.toArray()));
                fw.close();
            } else {
                asc = new ArrayList<Score>();
                asc.add(this);
                ScoreComparator comp = new ScoreComparator();
                Collections.sort(asc, comp);
                FileWriter fw = new FileWriter("highscore.dat");
                fw.write(gson.toJson(asc.toArray()));
                fw.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Point getNumberOneScore(ArrayList<Score> asc) {
        int maxScore = 0;
        int maxtime = 0;
        int mintime = 100000000;
        int error = 100;
        ScoreComparator sc = new ScoreComparator();
        if (asc != null) {
            Collections.sort(asc, sc);

            for (int i = 0; i < asc.size(); i++) {
                if (asc.get(i).getLevel() == getLevel()) {
                    Score s1 = (Score) asc.get(i);
                    int mt = (int) ((long) s1.getStoptime() - (long) s1.getStarttime());
                    if (mt < mintime) {
                        mintime = (int) ((long) s1.getStoptime() - (long) s1.getStarttime());
                        maxScore = (int) s1.getScore();
                        //
                    }
                    //    System.out.println("i:" + i + " with  time _" + mintime + "] score :" + maxScore + " date :" + ScoreUtil.printableDate(s1.getStarttime()) + " or  starttime : " + s1.getStarttime() + " and full time " + s1.getFullTime());
                }
            }
        }
        return new Point(maxScore, mintime);
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public int getLocalRank() {
        return localRank;
    }

    public void setLocalRank(int localRank) {
        this.localRank = localRank;
    }
}
