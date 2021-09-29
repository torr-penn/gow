package com.gtasoft.godofwind.ressource;

import com.gtasoft.godofwind.score.Score;

public interface IScoreUtil {

    public void saveScore(Score score);

    public int getLocalRank(Score score1);

    public Score getTop(int level);
}
