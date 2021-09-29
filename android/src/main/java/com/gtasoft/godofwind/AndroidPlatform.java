package com.gtasoft.godofwind;


import com.gtasoft.godofwind.android.ScoreUtil;
import com.gtasoft.godofwind.options.Options;
import com.gtasoft.godofwind.ressource.IScoreUtil;
import com.gtasoft.godofwind.ressource.NativePlatform;

public class AndroidPlatform implements NativePlatform {

    ScoreUtil scoreUtil;

    public AndroidPlatform() {
        scoreUtil = new ScoreUtil();
    }


    public Options.GameplayMode getPreferredGamePlay() {
        return Options.GameplayMode.PC;

    }

    public IScoreUtil getScoreUtil() {
        return scoreUtil;
    }


    public String getVersion() {
        return "HTML";
    }
}
