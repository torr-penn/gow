package com.gtasoft.godofwind.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gtasoft.godofwind.game.WindManager;

import java.io.FileReader;

public class LevelUtil {
    private static final String LVL_FILENAME = "data/level_info.json";
    private static LevelInfo[] lvl_array;
    boolean loaded = false;

    public LevelUtil() {

        lvl_array = loadLevels();

    }

    public String helmName(int level) {
        String helmName = "helm_rvjp";
        if (lvl_array != null) {


            return lvl_array[level].getHelm();

        }
        if (level == 0) {
            helmName = "helm_vprj";
        }
        if (level == 1) {
            helmName = "helm_vjpr";
        }

        if (level == 4) {
            helmName = "helm_rvjp";
        }

        if (level == 2) {
            helmName = "helm_vjpr";
        }
        if (level == 3) {
            helmName = "helm_prvj";
        }
        if (level == 5) {
            helmName = "helm_rvjp";
        }
        if (level == 6) {
            helmName = "helm_vprj";
        }
        if (level == 7) {
            helmName = "helm_vjpr";
        }
        if (level == 8) {
            helmName = "helm_rvjp";
        }

        return helmName;

    }

    public void setupWind(int level, WindManager wm, float rotDeg) {

        float rotRad = (float) (rotDeg * Math.PI / 180f);
        if (lvl_array != null) {
            wm.setupWind(rotRad, lvl_array[level].getW0(), lvl_array[level].getW1(), lvl_array[level].getW2(), lvl_array[level].getW3());

            return;
        }


        if (level == 0) {
            wm.setupWind(rotRad, WindManager.GREEN, WindManager.PINK, WindManager.RED, WindManager.YELLOW);
        } else if (level == 4) {
            wm.setupWind(rotRad, WindManager.RED, WindManager.GREEN, WindManager.YELLOW, WindManager.PINK);
        } else if (level == 2 || level == 1) {
            wm.setupWind(rotRad, WindManager.GREEN, WindManager.YELLOW, WindManager.PINK, WindManager.RED);
        } else if (level == 3) {
            wm.setupWind(rotRad, WindManager.PINK, WindManager.RED, WindManager.GREEN, WindManager.YELLOW);
        } else if (level == 5) {
            wm.setupWind(rotRad, WindManager.RED, WindManager.GREEN, WindManager.YELLOW, WindManager.PINK);
        } else if (level == 6) {
            wm.setupWind(rotRad, WindManager.GREEN, WindManager.PINK, WindManager.RED, WindManager.YELLOW);
        } else if (level == 7) {
            wm.setupWind(rotRad, WindManager.GREEN, WindManager.YELLOW, WindManager.PINK, WindManager.RED);
        } else if (level == 8) {
            wm.setupWind(rotRad, WindManager.RED, WindManager.GREEN, WindManager.YELLOW, WindManager.PINK);
        }
    }

    public int getPosX(int level) {
        if (lvl_array != null) {


            return lvl_array[level].getPosX();
        }

        if (level == 0) {
            return 256;
        } else if (level == 1 || level == 3) {
            return 96;
        } else if (level == 5) {
            return 64;
        } else if (level == 4) {
            return 96;
        } else if (level == 6) {
            return 896;

        } else {
            return 160;
        }

    }

    public int getPosY(int level) {
        if (lvl_array != null) {


            return lvl_array[level].getPosY();
        }

        if (level == 0) {
            return 110;
        } else if (level == 1 || level == 3) {
            return 96;
        } else if (level == 2) {
            return 64;
        } else if (level == 5) {
            return 64;
        } else if (level == 4) {
            return 224;
        } else if (level == 6) {
            return 896;
        } else {
            return 160;
        }

    }

    public int getPlayerSpeed(int level) {
        if (lvl_array != null) {


            return lvl_array[level].getPlayerSpeed();
        }

        if (level < 5) {
            return level + 1;
        } else {
            return level + 2;
        }
    }

    public long getStarTwoTime(int level) {
        if (lvl_array != null) {


            return lvl_array[level].getStarTwoTime();
        }
        return -1;
    }

    public long getStarThreeTime(int level) {
        if (lvl_array != null) {


            return lvl_array[level].getStarThreeTime();
        }
        return -1;
    }

    public int getWindSpeed(int level) {
        if (lvl_array != null) {


            return lvl_array[level].getWindSpeed();
        }

        if (level < 5) {
            return level + 1;
        } else {
            return level + 4;
        }
    }


    public LevelInfo[] loadLevels() {
        FileReader fr;
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try {
            FileHandle file;
            file = Gdx.files.internal(LevelUtil.LVL_FILENAME);
            if (file != null && file.exists()) {
                String s = file.readString();
                if (!s.isEmpty()) {
                    LevelInfo[] li = gson.fromJson(s, LevelInfo[].class);
                    return li;
                }
            } else {
                System.out.println(" file " + LevelUtil.LVL_FILENAME + "does not exist");
            }
        } catch (
                Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
