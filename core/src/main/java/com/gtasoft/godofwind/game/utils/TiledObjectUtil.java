package com.gtasoft.godofwind.game.utils;

import com.badlogic.gdx.maps.MapObject;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.gtasoft.godofwind.game.entity.ColorZoneBody;
import com.gtasoft.godofwind.game.entity.WallBody;
import com.gtasoft.godofwind.game.entity.WinnerZoneBody;
import com.gtasoft.godofwind.ressource.Constants;

public class TiledObjectUtil {
    public static void parseTiledObjectLayer(World world, MapObjects objects) {
        int i = 0;
        for (MapObject object : objects) {

            Shape shape;

            if (object instanceof PolygonMapObject) {
                shape = createPolygon((PolygonMapObject) object);

            } else if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else {
                continue;
            }


            WallBody wb = new WallBody(world, "" + i, shape);
            i = i + 1;
        }
    }


    public static void parseTiledColorLayer(World world, MapObjects objects, int colmode) {
        int i = 0;
        for (MapObject object : objects) {

            Shape shape;

            if (object instanceof PolygonMapObject) {
                shape = createPolygon((PolygonMapObject) object);

            } else if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else {
                continue;
            }


            ColorZoneBody czb = new ColorZoneBody(world, "" + i, shape, colmode);
            i = i + 1;
        }
    }


    public static WinnerZoneBody parseTiledWinnerLayer(World world, MapObjects objects) {
        int i = 0;
        for (MapObject object : objects) {

            Shape shape;

            if (object instanceof PolygonMapObject) {
                shape = createPolygon((PolygonMapObject) object);

            } else if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else {
                continue;
            }

            WinnerZoneBody wzb = new WinnerZoneBody(world, "" + i, shape);
            i = i + 1;
            return wzb;
        }
        return null;
    }

    private static ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / Constants.PPM, vertices[i * 2 + 1] / Constants.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }

    private static PolygonShape createPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {

            worldVertices[i] = vertices[i] / Constants.PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }
}
