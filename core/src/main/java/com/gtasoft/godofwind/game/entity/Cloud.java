package com.gtasoft.godofwind.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gtasoft.godofwind.GodOfWind;
import com.gtasoft.godofwind.game.WindManager;
import com.gtasoft.godofwind.ressource.Constants;
import com.gtasoft.godofwind.ressource.Point;

public class Cloud {


    GodOfWind gow;
    private TextureAtlas atlas;

    private float SPEED = 15f;
    private TextureRegion current;
    private TextureRegion shadow;
    private Point popPosition;

    private Body body;


    private boolean applyWind = false;
    private WindManager windManager = null;
    private float radAngle = 0;
    private int shadowDist = 50;
    private int level = 0;

    public Cloud(int level) {
        this.level = level;
        setPopPosition(popit());

    }

    public void create(World world, GodOfWind gow, float lightAngle, int shadowDistance) {
        this.gow = gow;
        this.shadowDist = shadowDistance;
        this.radAngle = lightAngle;
        this.setBody(createCloud(world, gow.getLu().getPosX(level), gow.getLu().getPosY(level), 16));
        SPEED = gow.getLu().getWindSpeed(level);
        this.getBody().setLinearDamping(0.1f);
        this.getBody().setAngularDamping(0.1f);


        initTextures();

    }


    private void initTextures() {


        atlas = new TextureAtlas(Gdx.files.internal("img/board/clouds/clouds.atlas"));

        int cloud = (int) (Math.random() * 6f);
        Array<TextureAtlas.AtlasRegion> ta = atlas.findRegions("original_cloud");
        current = ta.items[cloud];

        Array<TextureAtlas.AtlasRegion> tas = atlas.findRegions("original_shadow");
        shadow = tas.items[cloud];


    }


    public void moveit() {
        if (gow.gameScreen.getPlayer().isApplyWind()) {
            float maxwpower = 2f;
            float wpower = windManager.getWindPower();
            if (wpower > maxwpower) {
                wpower = maxwpower;
            }

            applyForce(new Vector2((float) Math.cos(windManager.getMainWindDirection()) * wpower, (float) Math.sin(windManager.getMainWindDirection()) * wpower));
        } else {
            if (body.getLinearVelocity().y != 0 || body.getLinearVelocity().x != 0) {
                body.setLinearVelocity(0, 0);
            }
        }
    }

    public void applyForce(Vector2 force) {
        Vector2 vcenter = body.getWorldCenter();
        body.applyForce(force, vcenter, true);

    }

    public void render(Batch batch) {
        float w = current.getRegionWidth();
        float h = current.getRegionHeight();
        int distx = (int) (shadowDist * Math.cos(radAngle));
        int disty = (int) (shadowDist * Math.sin(radAngle));

        batch.begin();
        batch.draw(shadow, getBody().getPosition().x * Constants.PPM - w + distx, getBody().getPosition().y * Constants.PPM - h + disty,
                0, 0, w, h, 2, 2, 0);
        batch.draw(current, getBody().getPosition().x * Constants.PPM - w, getBody().getPosition().y * Constants.PPM - h,
                0, 0, w, h, 2, 2, 0);

        batch.end();
    }


    public Vector2 getPosition() {
        return getBody().getPosition();
    }

    public void dispose() {
        atlas.dispose();
    }

    private Point popit() {
        float maxCloudDist = 800f;
        if (level == 2) {
            maxCloudDist = 1600f;
        }
        if (level == 5) {
            maxCloudDist = 1600f;
        }
        if (level == 6) {
            maxCloudDist = 1200f;
        }
        if (level == 8) {
            maxCloudDist = 1000f;
        }

        int popAngle = (int) (Math.random() * 380);

        float popAngleRad = (float) (popAngle * Math.PI) / 180f;
        int dist = (int) (150 + Math.random() * maxCloudDist);
        int distx = (int) (dist * Math.cos(popAngleRad));
        int disty = (int) (dist * Math.sin(popAngleRad));

        return new Point(distx, disty);
    }

    private Body createCloud(World world, float x, float y, float radius) {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;


        def.position.set((x + getPopPosition().x) / Constants.PPM, (y + getPopPosition().y) / Constants.PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Constants.PPM);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 3.f;
        fd.restitution = 0.0f;
        fd.friction = 0.5f;
        fd.filter.categoryBits = Constants.BIT_CLOUD;
        fd.filter.maskBits = Constants.BIT_NOLIGHT;
        fd.filter.groupIndex = 2;
        pBody.createFixture(fd).setUserData(this);
        shape.dispose();
        return pBody;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public WindManager getWindManager() {
        return windManager;
    }

    public void setWindManager(WindManager windManager) {
        this.windManager = windManager;
    }


    public boolean isApplyWind() {
        return applyWind;
    }

    public void setApplyWind(boolean applyWind) {
        this.applyWind = applyWind;
    }

    public Point getPopPosition() {
        return popPosition;
    }

    public void setPopPosition(Point popPosition) {
        this.popPosition = popPosition;
    }
}
