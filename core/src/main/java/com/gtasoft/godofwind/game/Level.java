package com.gtasoft.godofwind.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Level {

    private Array<Vector2> focalPoints;
    private String tiledmapname = "tile/level1.tmx";

    public Level(String tname, Vector2 center) {
        setFocalPoints(new Array<Vector2>());
        getFocalPoints().add(center);
        this.setTiledmapname(tname);
    }

    public Array<Vector2> getFocalPoints() {
        return focalPoints;
    }

    public void setFocalPoints(Array<Vector2> focalPoints) {
        this.focalPoints = focalPoints;
    }

    public String getTiledmapname() {
        return tiledmapname;
    }

    public void setTiledmapname(String tiledmapname) {
        this.tiledmapname = tiledmapname;
    }
}
