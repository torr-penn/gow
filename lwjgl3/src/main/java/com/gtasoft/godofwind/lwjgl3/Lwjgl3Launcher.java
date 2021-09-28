package com.gtasoft.godofwind.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.gtasoft.godofwind.GodOfWind;

/**
 * Launches the desktop (LWJGL3) application.
 */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        DesktopPlatform dp = new DesktopPlatform();
        return new Lwjgl3Application(new GodOfWind(dp), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("God Of Wind");
        configuration.setWindowedMode(1280, 768);
        configuration.setForegroundFPS(60);

        configuration.setWindowIcon("gow_launcher128.png", "gow_launcher64.png", "gow_launcher32.png", "gow_launcher16.png");
        return configuration;
    }
}