package com.gtasoft.godofwind.lwjgl3;


import com.gtasoft.godofwind.desktop.ScoreUtil;
import com.gtasoft.godofwind.options.Options;
import com.gtasoft.godofwind.ressource.IScoreUtil;
import com.gtasoft.godofwind.ressource.NativePlatform;


public class DesktopPlatform implements NativePlatform {
    private ScoreUtil scoreUtil;

    public DesktopPlatform() {
        scoreUtil = new ScoreUtil();
    }


    public Options.GameplayMode getPreferredGamePlay() {
        return Options.GameplayMode.PC;

    }


    public String getVersion() {
        return "Desktop";
    }

    public IScoreUtil getScoreUtil() {
        return scoreUtil;
    }
}
