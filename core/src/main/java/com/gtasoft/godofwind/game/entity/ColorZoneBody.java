package com.gtasoft.godofwind.game.entity;

import com.badlogic.gdx.physics.box2d.*;
import com.gtasoft.godofwind.ressource.Constants;

public class ColorZoneBody {
    public Body body;
    public String id;

    public int color;

    public ColorZoneBody(World world, String id, Shape shape, int colorCode) {
        this.id = id;
        this.color = colorCode;
        createZoneBodyFromShape(world, shape);
    }


    private void createZoneBodyFromShape(World world, Shape shape) {

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.isSensor = true;

        fixtureDef.filter.categoryBits = Constants.BIT_COLORZONE;
        fixtureDef.filter.maskBits = Constants.BIT_PLAYER;
        fixtureDef.filter.groupIndex = 0;
        this.body = world.createBody(bdef);

        Fixture fixt = body.createFixture(fixtureDef);


        fixt.setUserData(this);


    }


}
