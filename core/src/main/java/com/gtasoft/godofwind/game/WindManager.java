package com.gtasoft.godofwind.game;

import com.gtasoft.godofwind.game.utils.LevelUtil;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class WindManager {
    public static int RED = 0;
    public static int PINK = 1;
    public static int GREEN = 2;
    public static int YELLOW = 3;

    private float windPower = 50f;
    private float windDirectionRad[];
    private int masterDirection = GREEN;

    public WindManager() {
        windDirectionRad = new float[4];
        windDirectionRad[RED] = 0f;
        windDirectionRad[PINK] = (float) (Math.PI / 2f);
        windDirectionRad[YELLOW] = (float) (Math.PI);
        windDirectionRad[GREEN] = (float) (3 * Math.PI / 2f);
    }

    public static String getColorName(int idx) {
        if (idx == PINK) {
            return "pink";
        }
        if (idx == RED) {
            return "red";
        }
        if (idx == YELLOW) {
            return "yellow";
        }
        if (idx == GREEN) {
            return "green";
        }
        return " cid:" + idx;
    }

    public void adjustSpeed(int wspeed) {
        windPower = wspeed;

    }

    public float getWindPower() {
        return windPower;
    }

    public void setWindPower(float windPower) {
        this.windPower = windPower;
    }

    public float getWindDirectionRad(int color) {
        if (color > 3 || color < 0) {
            return windDirectionRad[0];
        } else {
            return windDirectionRad[color];
        }
    }

    public void setWindDirection(int idx) {
        if (idx > 3 || idx < 0) {

        } else {

        }


    }

    //public void setWindDirection(float windDirectionRad, int idx1, int idx2, int idx3, int idx4) {
    //idx correspond to color index in trigonometric order
    public void setupWind(float windDirectionRad, int idx1, int idx2, int idx3, int idx4) {

        this.windDirectionRad[idx1] = windDirectionRad;
        this.windDirectionRad[idx2] = windDirectionRad - (float) (Math.PI / 2f);
        this.windDirectionRad[idx3] = windDirectionRad - (float) (Math.PI);
        this.windDirectionRad[idx4] = windDirectionRad + (float) (Math.PI / 2f);
    }

    public int getMasterDirection() {
        return masterDirection;
    }

    public void setMasterDirection(int masterDirection) {
        this.masterDirection = masterDirection;
    }

    public float getMainWindDirection() {
        return windDirectionRad[masterDirection];

    }

    public float getMainWindDirectionDegree() {
        return (float) (windDirectionRad[masterDirection] * 180 / Math.PI);

    }

}
