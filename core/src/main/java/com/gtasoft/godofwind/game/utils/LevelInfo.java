package com.gtasoft.godofwind.game.utils;

import com.google.gson.annotations.Expose;

public class LevelInfo {


    @Expose
    private int id = 0;
    @Expose
    private String helm = "vprj";
    @Expose
    private int w0 = 0;
    @Expose
    private int w1 = 1;
    @Expose
    private int w2 = 2;
    @Expose
    private int w3 = 3;
    @Expose
    private int posX = 160;
    @Expose
    private int posY = 160;
    @Expose
    private int playerSpeed = 1;
    @Expose
    private int windSpeed = 1;
    @Expose
    private int victoryBounce = 0;

    @Expose
    private long starTwoTime = 0;

    @Expose
    private long starThreeTime = 0;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHelm() {
        return helm;
    }

    public void setHelm(String helm) {
        this.helm = helm;
    }

    public int getW0() {
        return w0;
    }

    public void setW0(int w0) {
        this.w0 = w0;
    }

    public int getW1() {
        return w1;
    }

    public void setW1(int w1) {
        this.w1 = w1;
    }

    public int getW2() {
        return w2;
    }

    public void setW2(int w2) {
        this.w2 = w2;
    }

    public int getW3() {
        return w3;
    }

    public void setW3(int w3) {
        this.w3 = w3;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getVictoryBounce() {
        return victoryBounce;
    }

    public void setVictoryBounce(int victoryBounce) {
        this.victoryBounce = victoryBounce;
    }

    public long getStarTwoTime() {
        return starTwoTime;
    }

    public void setStarTwoTime(long starTwoTime) {
        this.starTwoTime = starTwoTime;
    }

    public long getStarThreeTime() {
        return starThreeTime;
    }

    public void setStarThreeTime(long starThreeTime) {
        this.starThreeTime = starThreeTime;
    }
}
