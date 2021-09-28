package com.gtasoft.godofwind.score;


import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
    @Override
    public int compare(Score score1, Score score2) {

        if (score1.getLevel() < score2.getLevel()) {
            return 1;
        }

        if (score1.getLevel() > score2.getLevel()) {
            return -1;
        }

        int e1 = score1.getBounce();
        int e2 = score2.getBounce();
        if (e1 > e2) return 1;
        if (e2 > e1) return -1;
        long t1 = score1.getStoptime() - score1.getStarttime();
        long t2 = score2.getStoptime() - score2.getStarttime();
        if (t1 < t2) {
            return -1;
        } else if (t1 > t2) {
            return +1;
        }
        return 0;
    }

}
