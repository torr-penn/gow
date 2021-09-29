package com.gtasoft.godofwind.game.entity;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gtasoft.godofwind.GodOfWind;
import com.gtasoft.godofwind.game.Level;
import com.gtasoft.godofwind.game.WindManager;
import com.gtasoft.godofwind.game.utils.LevelUtil;
import com.gtasoft.godofwind.ressource.Constants;

public class Player {

    int prevval = 0;
    GodOfWind gow;
    long lastbong = 0;
    private TextureAtlas atlas;
    private float hp, mp;
    private float SPEED = 15f;
    private TextureRegion current;
    private Body body;

    private float animState;

    private Animation wLeft, wUp, wDown, wIn;

    private boolean applyWind = false;
    private int collisionNb = 0;
    private WindManager windManager = null;
    private boolean victory = false;
    private int nbpush = 0;

    public Player(World world, GodOfWind gow, int level) {

        this.gow = gow;


        this.setBody(createTwoCircle(world, gow.getLu().getPosX(level), gow.getLu().getPosY(level), 16, 160, 200, 16));
        SPEED = gow.getLu().getPlayerSpeed(level);
        this.getBody().setLinearDamping(0.1f);
        this.getBody().setAngularDamping(0.1f);
        victory = false;


        hp = 100;
        mp = 100;
        initAnimations();
    }

    private void initAnimations() {
        animState = 0;

        TextureAtlas atlastmp = new TextureAtlas(Gdx.files.internal("img/atlas/egg_runner.txt"));

        Array<TextureAtlas.AtlasRegion> ta = atlastmp.findRegions("wfront");

        wDown = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wDown.setPlayMode(Animation.PlayMode.LOOP);


        ta = atlastmp.findRegions("wleft");

        wLeft = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wLeft.setPlayMode(Animation.PlayMode.LOOP);

        ta = atlastmp.findRegions("wback");
        wUp = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wUp.setPlayMode(Animation.PlayMode.LOOP);

        ta = atlastmp.findRegions("sploutch");
        wIn = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wIn.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        atlas = new TextureAtlas(Gdx.files.internal("img/link.txt"));

        current = (TextureRegion) wDown.getKeyFrame(animState);
    }

    public void controller(float delta) {

        float x = 0, y = 0;
        if (victory) {
            animState += delta;
            current = (TextureRegion) wIn.getKeyFrame(animState, true);

            return;
        }

        boolean heldAttack = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= 1;
            current = (TextureRegion) wLeft.getKeyFrame(animState, true);

            if (!heldAttack) {

                if (current.isFlipX())
                    current.flip(true, false);
            }
        }
        if (Gdx.input.isKeyPressed((Input.Keys.RIGHT))) {
            x += 1;
            current = (TextureRegion) wLeft.getKeyFrame(animState, true);

            if (!heldAttack) {

                if (!current.isFlipX())
                    current.flip(true, false);
            }
        }
        if (Gdx.input.isKeyPressed((Input.Keys.UP))) {
            y += 1;
            current = (TextureRegion) wUp.getKeyFrame(animState, true);

        }
        if (Gdx.input.isKeyPressed((Input.Keys.DOWN))) {
            y -= 1;
            current = (TextureRegion) wDown.getKeyFrame(animState, true);

        }

        if (x != 0) {
            getBody().setLinearVelocity(getBody().getLinearVelocity().x + x * SPEED * delta, getBody().getLinearVelocity().y);
        }
        if (y != 0) {

            getBody().setLinearVelocity(getBody().getLinearVelocity().x, y * SPEED * delta + getBody().getLinearVelocity().y);
        }
        animState += delta;


    }

    public void moveit() {
        if (isApplyWind()) {

            if (prevval != (int) (Math.cos(windManager.getMainWindDirection()) * windManager.getWindPower())) {
                prevval = (int) (Math.cos(windManager.getMainWindDirection()) * windManager.getWindPower());
            }

            applyForce(new Vector2((float) Math.cos(windManager.getMainWindDirection()) * windManager.getWindPower(), (float) Math.sin(windManager.getMainWindDirection()) * windManager.getWindPower()));
        }
    }

    public void applyForce(Vector2 force) {
        Vector2 vcenter = body.getWorldCenter();
        body.applyForce(force, vcenter, true);

    }

    public void render(Batch batch) {
        float w = current.getRegionWidth();
        float h = current.getRegionHeight();
        batch.begin();
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


    private Body createTwoCircle(World world, float xA, float yA, float radiusA, float xB, float yB, float radiusB) {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(xA / Constants.PPM, yA / Constants.PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radiusA / Constants.PPM);
        shape.setPosition(new Vector2(0 / Constants.PPM, -14 / Constants.PPM));

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.restitution = 0.95f;
        fd.friction = 0.1f;
        fd.filter.categoryBits = Constants.BIT_PLAYER;
        fd.filter.maskBits = Constants.BIT_WALL | Constants.BIT_COLORZONE | Constants.BIT_SENSOR;
        fd.filter.groupIndex = 0;
        CircleShape shape2 = new CircleShape();
        shape2.setRadius(radiusB / Constants.PPM);
        shape2.setPosition(new Vector2(0 / Constants.PPM, 14 / Constants.PPM));

        FixtureDef fd2 = new FixtureDef();
        fd2.shape = shape2;
        fd2.density = 1.0f;
        fd2.restitution = 0.9f;
        fd2.friction = 0.1f;
        fd2.filter.categoryBits = Constants.BIT_PLAYER;
        fd2.filter.maskBits = Constants.BIT_WALL | Constants.BIT_COLORZONE | Constants.BIT_SENSOR;
        fd2.filter.groupIndex = 0;

        pBody.createFixture(fd).setUserData(this);
        pBody.createFixture(fd2).setUserData(this);
        shape.dispose();
        shape2.dispose();
        return pBody;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public int getCollisionNb() {
        return collisionNb;
    }

    public void setCollisionNb(int collisionNb) {
        this.collisionNb = collisionNb;
    }

    public void addCollision() {
        if (isVictorious() && gow.getScore().getBounce() == 0) {
            if (gow.op.isAudible()) {
                long time = System.currentTimeMillis();
                if (time - lastbong > 200) {
                    int i = (int) (Math.random() * 4);
                    if (i == 0) {
                        gow.snd_top1.play();
                    } else if (i == 1) {
                        gow.snd_top2.play();
                    } else if (i == 2) {
                        gow.snd_top3.play();
                    } else if (i == 3) {
                        gow.snd_top5.play();
                    }
                    lastbong = time;
                }

            }

            return;
        }
        if (!isVictorious()) {
            this.collisionNb = getCollisionNb() + 1;
        }
        if (gow.op.isAudible()) {
            long time = System.currentTimeMillis();
            if (time - lastbong > 200) {
                int i = (int) (Math.random() * 6);
                gow.snd_bong[i].play();
                lastbong = time;
            }
        }
    }

    public WindManager getWindManager() {
        return windManager;
    }

    public void setWindManager(WindManager windManager) {
        this.windManager = windManager;
    }

    public void setVictory(boolean b) {
        victory = b;

    }

    public boolean isVictorious() {
        return victory;
    }

    public void addNbpush() {
        if (nbpush == 0)
            setApplyWind(true);
        nbpush = nbpush + 1;

    }

    public void substractNbpush() {
        if (nbpush == 0) {
            System.out.println(" already no push app wind : " + isApplyWind());
            setApplyWind(false);
            return;
        }
        if (nbpush == 1) {
            setApplyWind(false);
        }
        nbpush = nbpush - 1;


    }

    public boolean isApplyWind() {
        return applyWind;
    }

    public void setApplyWind(boolean applyWind) {
        this.applyWind = applyWind;
    }
}
