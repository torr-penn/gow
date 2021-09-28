package com.gtasoft.godofwind.options;

import com.google.gson.annotations.Expose;

public class User {

    @Expose
    private String name = "anonymous";
    @Expose
    private int langid = 0;
    @Expose
    private int prefLevel = 2;
    @Expose
    private int maxLevel = 1;

    @Expose
    private boolean playSound = true;

    @Expose
    private long lastLogin = 0;


    @Expose
    private boolean consentGDPR = false;


    @Expose
    private String pwd = null;


    @Expose
    private String gid = "0";

    @Expose
    private boolean validProfile = false;

    public User(String name) {
        this.name = name;
    }

    public boolean isValidProfile() {
        return validProfile;
    }

    public void setValidProfile(boolean validProfile) {
        this.validProfile = validProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User up = (User) o;
            if (up.getName() != null) {
                if (((User) o).getName().equals(getName())) {
                    return true;
                }
            }
        }
        return false;
    }


    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isConsentGDPR() {
        return consentGDPR;
    }

    public void setConsentGDPR(boolean consentGDPR) {
        this.consentGDPR = consentGDPR;
    }

    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getLangid() {
        return langid;
    }

    public void setLangid(int langid) {
        this.langid = langid;
    }


    public int getPrefLevel() {
        return prefLevel;
    }

    public void setPrefLevel(int prefLevel) {
        this.prefLevel = prefLevel;
    }
}
