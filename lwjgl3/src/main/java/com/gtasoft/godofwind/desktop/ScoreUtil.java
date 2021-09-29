package com.gtasoft.godofwind.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.gtasoft.godofwind.ressource.IScoreUtil;
import com.gtasoft.godofwind.score.Score;
import com.gtasoft.godofwind.score.ScoreComparator;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ScoreUtil implements IScoreUtil {
    public ScoreUtil() {

    }

    @Override

    public void saveScore(Score score) {
        if (score.getStoptime() == 0) {
            System.out.println(" error score invalid ");
            return;
        }

        Json json = new Json();
        score.setScore(score.getScore() - Math.abs(score.getTime() * score.getLevel() / 5));
        try {
            FileHandle file;
            file = Gdx.files.local(Score.FILENAMESCORE);
            if (file == null || !file.exists()) {
                FileHandle filew = Gdx.files.local(score.FILENAMESCORE);
                filew.writeString("", false);
            }
            file = Gdx.files.local(Score.FILENAMESCORE);
            if (file != null && file.exists()) {
                String s = file.readString();
                if (!s.isEmpty()) {
                    Score[] sc = json.fromJson(Score[].class, s);

                    ArrayList<Score> asc = null;
                    if (sc != null) {
                        asc = new ArrayList<Score>(Arrays.asList(sc));
                    }
                    setLocalPos(asc, score);

                    if (asc != null) {
                        asc.add(score);
                        FileHandle filew = Gdx.files.local(score.FILENAMESCORE);
                        if (asc.size() > 5 * 10 * 3 * 3) {
                            asc = this.cleanupScore(asc, score);
                        }
                        filew.writeString(json.toJson(asc.toArray()), false);
                    } else {
                        asc = new ArrayList<Score>();
                        asc.add(score);
                        ScoreComparator comp = new ScoreComparator();
                        Collections.sort(asc, comp);
                        FileHandle filew = Gdx.files.local(score.FILENAMESCORE);
                        filew.writeString(json.toJson(asc.toArray()), false);
                    }
                } else {
                    ArrayList<Score> asc = new ArrayList<Score>();
                    asc.add(score);
                    ScoreComparator comp = new ScoreComparator();
                    Collections.sort(asc, comp);
                    setLocalPos(asc, score);
                    FileHandle filew = Gdx.files.local(score.FILENAMESCORE);
                    filew.writeString(json.toJson(asc.toArray()), false);
                }
            } else {
                FileHandle filew = Gdx.files.local(score.FILENAMESCORE);
                filew.writeString("", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Score getTop(int lvl) {
        FileReader fr;

        Json json = new Json();
        try {
            FileHandle file;
            file = Gdx.files.local(Score.FILENAMESCORE);
            if (file != null && file.exists()) {
                String s = file.readString();
                if (!s.isEmpty()) {
                    Score[] sc = json.fromJson(Score[].class, s);

                    ArrayList<Score> asc = null;
                    if (sc != null) {
                        asc = new ArrayList<Score>(Arrays.asList(sc));
                    }
                    if (asc != null) {
                        Score mys = new Score();
                        mys.setLevel(lvl);
                        ScoreComparator scoreComparator = new ScoreComparator();
                        Collections.sort(asc, scoreComparator);
                        for (int j = 0; j < asc.size(); j++) {
                            Score sctest = (Score) asc.get(j);
                            if (sctest.getLevel() == lvl) {
                                return sctest;
                            }
                        }
                    }
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public ArrayList<Score> cleanupScore(ArrayList<Score> asc, Score newScore) {
        if (asc != null) {
            int nb = asc.size();
            ScoreComparator comp = new ScoreComparator();
            Collections.sort(asc, comp);
            int ofthiskind = 0;
            ArrayList<Score> toremove = new ArrayList<Score>();
            for (int i = 0; i < nb; i++) {
                Score score1 = asc.get(i);
                if (score1.getLevel() == newScore.getLevel()) {
                    ofthiskind++;
                    if (ofthiskind > 10) {
                        toremove.add(score1);
                    }
                }

            }
            if (toremove != null && toremove.size() > 0) {
                asc.removeAll(toremove);
            }
        }
        return asc;
    }

    public void setLocalPos(ArrayList<Score> asc, Score myscore) {
        int rk = 1;
        if (asc == null || asc.size() == 0) {
            return;
        }
        ScoreComparator comp = new ScoreComparator();
        Collections.sort(asc, comp);
        for (int i = 0; i < asc.size(); i++) {
            if (asc.get(i).getLevel() == myscore.getLevel()) {
                if (myscore.getBounce() > asc.get(i).getBounce()) {
                    rk++;
                } else if (myscore.getScoreTime() > asc.get(i).getScoreTime()) {
                    rk++;
                }
            }
        }


        myscore.setPosition(rk);

    }

    public int getLocalRank(Score score1) {
        FileReader fr;
        Json json = new Json();
        try {
            FileHandle file;
            file = Gdx.files.local(Score.FILENAMESCORE);
            if (file != null && file.exists()) {
                String s = file.readString();
                if (!s.isEmpty()) {
                    Score[] sc = json.fromJson(Score[].class, s);

                    ArrayList<Score> asc = null;
                    if (sc != null) {
                        asc = new ArrayList<Score>(Arrays.asList(sc));
                    }
                    if (asc != null) {
                        //   System.out.println(" call local rank " + score1 + " [" + score1.printDiff() + "]");
                        return getLocalRank(asc, score1);
                    }
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();

        }
        return 0;
    }

    public int getLocalRank(ArrayList<Score> asc, Score sco) {
        int rk = 1;
        if (asc == null || asc.size() == 0) {
            return 0;
        }
        ScoreComparator comp = new ScoreComparator();
        Collections.sort(asc, comp);

        for (int i = 0; i < asc.size(); i++) {
            if (asc.get(i).getLevel() == sco.getLevel()) {
                if (sco.getBounce() > asc.get(i).getBounce()) {
                    rk++;
                } else {
                    if (sco.getDiffMs() > asc.get(i).getDiffMs()) {
                        rk++;
                    }
                }
            }
        }
        return rk;

    }
}
