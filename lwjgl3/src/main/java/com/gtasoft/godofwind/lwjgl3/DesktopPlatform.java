package com.gtasoft.godofwind.lwjgl3;


import com.gtasoft.godofwind.options.Options;
import com.gtasoft.godofwind.ressource.NativePlatform;

public class DesktopPlatform implements NativePlatform {


    public DesktopPlatform() {

    }


    public Options.GameplayMode  getPreferredGamePlay(){
        return Options.GameplayMode.PC;

    }


    public String getVersion() {
        return "Desktop";
    }
}
