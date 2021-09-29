package com.gtasoft.godofwind.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.utils.Json;


import com.gtasoft.godofwind.game.WindManager;


import java.io.*;


public class LevelUtil {
    private static final String LVL_FILENAME = "data/level_info.txt";

    private static LevelInfo[] lvl_array;
    boolean loaded = false;
    String info = "0";

    private String debug = "Y";


    public LevelUtil() {

        lvl_array = loadLevels();


    }

    public String lvlInfo() {
        return info;
    }

    public boolean isok() {
        return loaded;

    }

    public String helmName(int level) {
        String helmName = "rvjp";
        if (lvl_array != null) {


            return lvl_array[level].getHelm();

        }
        if (level == 0) {
            helmName = "vprj";
        }
        if (level == 1) {
            helmName = "vjpr";
        }

        if (level == 4) {
            helmName = "rvjp";
        }

        if (level == 2) {
            helmName = "vjpr";
        }
        if (level == 3) {
            helmName = "prvj";
        }
        if (level == 5) {
            helmName = "rvjp";
        }
        if (level == 6) {
            helmName = "vprj";
        }
        if (level == 7) {
            helmName = "vjpr";
        }
        if (level == 8) {
            helmName = "rvjp";
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
        Json json = new Json();
        debug = debug + "1";
        try {
            FileHandle file;
            file = Gdx.files.internal(LevelUtil.LVL_FILENAME);
            InputStream is = file.read();
            String essay = getStreamContent(is);
            debug = debug + "2";
            if (essay != null && !"".equals(essay)) {
                debug = debug + "3";

                LevelInfo[] li = json.fromJson(LevelInfo[].class, essay);
                loaded = true;
                debug = debug + "4" + " nb of elemnet :" + li.length;
                return li;
            }
        } catch (Exception ex) {
            debug = ex.getMessage() + " /" + debug + "5";
        }
        return null;
    }

//    public void initialize() {
//
//        //    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//        Json json = new Json();
//        try {
//
//            FileHandle file;
//            file = Gdx.files.internal("data/group/.txt");
//            if (file != null && file.exists()) {
//                String s = file.readString();
//                if (!s.isEmpty()) {
//                    Letter[] lgroup = json.fromJson(Letter[].class, s);
//                    //this.allup = json.fromJson(UserPrefs[].class, fpref);
//                    if (lgroup != null) {
//                        fLetter = new ArrayList<Letter>(Arrays.asList(lgroup));
//
//                    }
//                }
//            } else {
//                System.out.println("Error : data/group/" + language.getCode() + ".txt is not a file");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//


    public String getStreamContent(InputStream inputStream) {
        StringBuilder result = new StringBuilder();
        try {

            String newLine = "\n";
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            boolean flag = false;

            String line = reader.readLine();
            while (line != null) {
                result.append(flag ? newLine : "").append(line);
                flag = true;
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            System.out.println(" ioe error " + ioe);
        }
        return result.toString();
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }
}
