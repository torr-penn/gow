package com.gtasoft.godofwind.game.entity;

import com.badlogic.gdx.physics.box2d.*;
import com.gtasoft.godofwind.ressource.Constants;

public class WallBody {
    public Body body;
    public String id;

    public WallBody(World world, String id, Shape shape) {
        this.id = id;
        createWallBodyFromShape(world, shape);
        body.setLinearDamping(2f);
    }


    private void createWallBodyFromShape(World world, Shape shape) {

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.05f;
        fixtureDef.friction = 0.5f;


        this.body = world.createBody(bdef);

        Fixture fixt = body.createFixture(fixtureDef);


        fixt.setUserData(this);


    }


}
