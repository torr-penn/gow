package com.gtasoft.godofwind.ressource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileTools {
    public FileTools() {

    }

    public static String readFile(String fileName) {
        //print(" read file " + fileName);
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String s = file.readString();
            if (!s.isEmpty()) {
                return s;
            }
        }
        return null;
    }


}



