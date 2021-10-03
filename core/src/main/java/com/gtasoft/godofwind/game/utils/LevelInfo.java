package com.gtasoft.godofwind.game.utils;


public class LevelInfo {


    private int id = 0;
    private String helm = "vprj";
    private int w0 = 0;
    private int w1 = 1;
    private int w2 = 2;
    private int w3 = 3;
    private int posX = 160;
    private int posY = 160;
    private int playerSpeed = 1;
    private int windSpeed = 1;
    private int victoryBounce = 0;

    private int starTwoTime = 0;

    private int starThreeTime = 0;
    private String label = "level";

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

    public int getStarTwoTime() {
        return starTwoTime;
    }

    public void setStarTwoTime(int starTwoTime) {
        this.starTwoTime = starTwoTime;
    }

    public int getStarThreeTime() {
        return starThreeTime;
    }

    public void setStarThreeTime(int starThreeTime) {
        this.starThreeTime = starThreeTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
