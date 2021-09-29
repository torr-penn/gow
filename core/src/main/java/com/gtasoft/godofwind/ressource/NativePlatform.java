package com.gtasoft.godofwind.ressource;

import com.gtasoft.godofwind.options.Options;

public interface NativePlatform {

    public Options.GameplayMode getPreferredGamePlay();

    public String getVersion();

    public IScoreUtil getScoreUtil();
}
