package com.gtasoft.godofwind.html;

import com.badlogic.gdx.utils.Array;
import com.gtasoft.godofwind.ressource.IScoreUtil;
import com.gtasoft.godofwind.score.Score;
import com.gtasoft.godofwind.score.ScoreComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class ScoreUtil implements IScoreUtil {
    Array<Score> allScore;

    public ScoreUtil() {
        allScore = new Array<Score>();
    }


    @Override
    public void saveScore(Score score) {
        allScore.add(score);
    }

    @Override
    public int getLocalRank(Score sc) {


        if (allScore != null) {
            //   System.out.println(" call local rank " + score1 + " [" + score1.printDiff() + "]");
            return getLocalRank(allScore, sc);
        }
        return 1;
    }

    public int getLocalRank(Array<Score> asc, Score sco) {
        int rk = 1;
        if (asc == null || asc.size == 0) {
            return 0;
        }

        for (int i = 0; i < asc.size; i++) {
            if (asc.get(i).getLevel() == sco.getLevel()) {
                if (sco.getBounce() > asc.get(i).getBounce()) {
                    rk++;
                } else {
                    if (sco.getBounce() == asc.get(i).getBounce()) {
                        if (sco.getDiffMs() > asc.get(i).getDiffMs()) {
                            rk++;
                        }
                    }
                }
            }
        }
        return rk;

    }

    public Score getTop(int level) {

        if (allScore == null || allScore.size == 0) {
            return null;
        }
        Score mys = null;
        for (int i = 0; i < allScore.size; i++) {
            mys = new Score();
            mys.setLevel(level);
            mys.setBounce(10000);
            for (int j = 0; j < allScore.size; j++) {
                Score sctest = (Score) allScore.get(j);
                if (sctest.getLevel() == level) {
                    if (sctest.getBounce() < mys.getBounce()) {
                        mys = sctest;
                    } else {
                        if (mys.getScoreTime() > sctest.getScoreTime()) {
                            mys = sctest;
                        }
                    }

                }
            }
        }
        if (mys.getBounce() == 10000)
            return null;
        return mys;
    }
}
