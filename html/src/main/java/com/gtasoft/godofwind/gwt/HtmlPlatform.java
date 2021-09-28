package com.gtasoft.godofwind.gwt;


import com.gtasoft.godofwind.options.Options;
import com.gtasoft.godofwind.ressource.NativePlatform;

public class HtmlPlatform implements NativePlatform {


    public HtmlPlatform() {

    }


    public Options.GameplayMode  getPreferredGamePlay(){
        return Options.GameplayMode.PC;

    }


    public String getVersion() {
        return "HTML";
    }
}
