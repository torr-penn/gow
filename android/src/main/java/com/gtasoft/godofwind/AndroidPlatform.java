package com.gtasoft.godofwind;


import com.gtasoft.godofwind.options.Options;
import com.gtasoft.godofwind.ressource.NativePlatform;

public class AndroidPlatform implements NativePlatform {


    public AndroidPlatform() {

    }


    public Options.GameplayMode  getPreferredGamePlay(){
        return Options.GameplayMode.PC;

    }


    public String getVersion() {
        return "HTML";
    }
}
