package com.gtasoft.godofwind.game.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gtasoft.godofwind.GodOfWind;
import com.gtasoft.godofwind.game.WindManager;
import com.gtasoft.godofwind.game.entity.Cloud;
import com.gtasoft.godofwind.ressource.Point;

import java.util.ArrayList;

public class CloudManager {

    Array<Cloud> meteo = new Array<Cloud>();
    int nbmax = 20;

    float angleRad = 0;


    public CloudManager(World world, GodOfWind gow, int level) {
        if (level == 5) {
            nbmax = 50;
        }
        if (level == 2) {
            nbmax = 50;
        }
        if (level == 9) {
            nbmax = 200;
        }
        if (level == 6) {
            nbmax = 30;
        }

        if (level == 8) {
            nbmax = 25;
        }
        int nbfail = 0;
        int lightAngle = (int) (Math.random() * 380);
        angleRad = (float) (lightAngle * Math.PI) / 180f;
        int shadowDistance = 20 + (int) (Math.random() * 40);

        for (int i = 0; i <= nbmax; i++) {
            Cloud cloud;
            boolean addit;
            int nbtry = 0;
            do {
                nbtry++;
                cloud = new Cloud(level);
                addit = farEnough(cloud);
                if (!addit) {
                    nbfail = nbfail + 1;
                }
            } while (!addit && nbtry < 30);
            if (addit) {
                cloud.create(world, gow, angleRad, shadowDistance);
                meteo.add(cloud);
            }
        }


    }

    public boolean farEnough(Cloud c) {
        Point p = c.getPopPosition();
        int mindist = 300;

        for (Cloud cx : meteo) {
            Point px = cx.getPopPosition();

            int dsquare = (px.x - p.x) * (px.x - p.x) + (px.y - p.y) * (px.y - p.y);
            double dist = Math.sqrt(dsquare);

            if (dist < mindist) {
                return false;
            }

        }

        return true;
    }

    public void setWindManager(WindManager wm) {
        if (meteo != null) {
            for (int i = 0; i < meteo.size; i++) {
                Cloud c = meteo.get(i);
                c.setWindManager(wm);
            }
        }

    }

    public void moveit() {
        if (meteo != null) {
            for (int i = 0; i < meteo.size; i++) {
                Cloud c = meteo.get(i);
                c.moveit();
            }
        }

    }

    public void render(SpriteBatch batch) {
        if (meteo != null) {
            for (int i = 0; i < meteo.size; i++) {
                Cloud c = meteo.get(i);
                c.render(batch);
            }
        }

    }

}
