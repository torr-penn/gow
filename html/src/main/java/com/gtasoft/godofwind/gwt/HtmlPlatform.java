package com.gtasoft.godofwind.gwt;


import com.gtasoft.godofwind.html.ScoreUtil;
import com.gtasoft.godofwind.options.Options;
import com.gtasoft.godofwind.ressource.IScoreUtil;
import com.gtasoft.godofwind.ressource.NativePlatform;

public class HtmlPlatform implements NativePlatform {

    ScoreUtil scoreUtil;

    public HtmlPlatform() {
        scoreUtil = new ScoreUtil();
    }


    public Options.GameplayMode getPreferredGamePlay() {
        return Options.GameplayMode.PC;

    }


    public String getVersion() {
        return "HTML";
    }

    public IScoreUtil getScoreUtil() {
        return scoreUtil;
    }
}
