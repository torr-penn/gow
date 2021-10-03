package com.gtasoft.godofwind.game.entity;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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
    private TextureRegion[] boomAnim;
    private Body body;

    private float animState;

    private Animation wLeft[], wUp[], wDown[], wIn[], aboom;

    private boolean applyWind = false;
    private int collisionNb = 0;
    private WindManager windManager = null;
    private boolean victory = false;
    private int nbpush = 0;
    private float boompX = 0f;
    private float boompY = 0f;
    private int playerDirection = 0;
    private boolean windIsNew = false;
    private boolean noWindIsNew = false;
    private int masterDirectionColor = 0;

    public Player(World world, GodOfWind gow, int level) {

        this.gow = gow;


        this.setBody(createTwoCircle(world, gow.getLu().getPosX(level), gow.getLu().getPosY(level), 16, 160, 200, 16));
        SPEED = gow.getLu().getPlayerSpeed(level);
        this.getBody().setLinearDamping(0.1f);
        this.getBody().setAngularDamping(0.1f);
        victory = false;


        hp = 100;
        mp = 100;
        wDown = new Animation[5];
        wIn = new Animation[5];
        wUp = new Animation[5];
        wLeft = new Animation[5];


        initAnimations();
    }

    // 0 - nocol
    // 1 - red
    // 2 - green
    //3 - pink
    // 4 yellow
    private void initAnimations() {
        animState = 0;

        atlas = new TextureAtlas(Gdx.files.internal("img/atlas/egg_runner.atlas"));

        Array<TextureAtlas.AtlasRegion> ta;

        ta = atlas.findRegions("wfront");
        wDown[0] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wDown[0].setPlayMode(Animation.PlayMode.LOOP);


        ta = atlas.findRegions("wleft");

        wLeft[0] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wLeft[0].setPlayMode(Animation.PlayMode.LOOP);

        ta = atlas.findRegions("wback");
        wUp[0] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wUp[0].setPlayMode(Animation.PlayMode.LOOP);


        ta = atlas.findRegions("wfrontred");
        wDown[1] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wDown[1].setPlayMode(Animation.PlayMode.LOOP);


        ta = atlas.findRegions("wleftred");

        wLeft[1] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wLeft[1].setPlayMode(Animation.PlayMode.LOOP);

        ta = atlas.findRegions("wbackred");
        wUp[1] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wUp[1].setPlayMode(Animation.PlayMode.LOOP);

        ta = atlas.findRegions("wfrontgreen");
        wDown[2] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wDown[2].setPlayMode(Animation.PlayMode.LOOP);


        ta = atlas.findRegions("wleftgreen");

        wLeft[2] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wLeft[2].setPlayMode(Animation.PlayMode.LOOP);

        ta = atlas.findRegions("wbackgreen");
        wUp[2] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wUp[2].setPlayMode(Animation.PlayMode.LOOP);

        ta = atlas.findRegions("wfrontp");
        wDown[3] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wDown[3].setPlayMode(Animation.PlayMode.LOOP);


        ta = atlas.findRegions("wleftp");

        wLeft[3] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wLeft[3].setPlayMode(Animation.PlayMode.LOOP);

        ta = atlas.findRegions("wbackp");
        wUp[3] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wUp[3].setPlayMode(Animation.PlayMode.LOOP);
        ta = atlas.findRegions("wfronty");
        wDown[4] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wDown[4].setPlayMode(Animation.PlayMode.LOOP);


        ta = atlas.findRegions("wlefty");

        wLeft[4] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wLeft[4].setPlayMode(Animation.PlayMode.LOOP);

        ta = atlas.findRegions("wbacky");
        wUp[4] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wUp[4].setPlayMode(Animation.PlayMode.LOOP);


        ta = atlas.findRegions("sploutch");
        wIn[0] = new Animation<TextureAtlas.AtlasRegion>(.15f, ta);
        wIn[0].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


        current = (TextureRegion) wDown[0].getKeyFrame(animState);


        Texture boom1;
        TextureRegion boomAnim1Aux[][];

        boom1 = new Texture(Gdx.files.internal("img/board/boom/boom.png"));
        boomAnim1Aux = TextureRegion.split(boom1, boom1.getWidth() / 4, boom1.getHeight() / 4); // #10
        boomAnim = new TextureRegion[16];
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boomAnim[k++] = boomAnim1Aux[i][j];
            }

        }
        aboom = new Animation<TextureRegion>(1f / 32f, boomAnim);
        aboom.setPlayMode(Animation.PlayMode.NORMAL);


    }

    public void controller(float delta, int pushX, int pushY) {

        float x = 0, y = 0;
        if (victory) {
            animState += delta;
            current = (TextureRegion) wIn[0].getKeyFrame(animState, true);

            return;
        }

        boolean refresh = false;
//        if (applyWind && masterDirectionColor == 0) {
//            masterDirectionColor = windManager.getMasterDirectionColor();
//            refresh = true;
//        }
        if (applyWind && masterDirectionColor != windManager.getMasterDirectionColor()) {
            masterDirectionColor = windManager.getMasterDirectionColor();
            refresh = true;
        }
        if (!applyWind && masterDirectionColor != 0) {
            masterDirectionColor = 0;
            refresh = true;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || pushX < 0) {
            x -= 1;
            playerDirection = 0;
            current = (TextureRegion) wLeft[masterDirectionColor].getKeyFrame(animState, true);
            if (current.isFlipX())
                current.flip(true, false);
        }
        if (Gdx.input.isKeyPressed((Input.Keys.RIGHT)) || pushX > 0) {
            x += 1;
            playerDirection = 1;
            current = (TextureRegion) wLeft[masterDirectionColor].getKeyFrame(animState, true);
            if (!current.isFlipX())
                current.flip(true, false);
        }
        if (Gdx.input.isKeyPressed((Input.Keys.UP)) || pushY > 0) {
            y += 1;
            playerDirection = 2;
            current = (TextureRegion) wUp[masterDirectionColor].getKeyFrame(animState, true);

        }
        if (Gdx.input.isKeyPressed((Input.Keys.DOWN)) || pushY < 0) {
            y -= 1;
            playerDirection = 3;
            current = (TextureRegion) wDown[masterDirectionColor].getKeyFrame(animState, true);

        }
        if (refresh) {
            if (playerDirection == 0) {
                current = (TextureRegion) wLeft[masterDirectionColor].getKeyFrame(animState, true);
            }
            if (playerDirection == 1) {
                current = (TextureRegion) wLeft[masterDirectionColor].getKeyFrame(animState, true);
                if (!current.isFlipX())
                    current.flip(true, false);
            }
            if (playerDirection == 2) {
                current = (TextureRegion) wUp[masterDirectionColor].getKeyFrame(animState, true);
            }
            if (playerDirection == 3) {
                current = (TextureRegion) wDown[masterDirectionColor].getKeyFrame(animState, true);
            }

        }


        x = x + pushX;
        if (x == 2) x = 1;
        if (x == -2) x = -1;
        if (x != 0) {
            getBody().setLinearVelocity(getBody().getLinearVelocity().x + x * SPEED * delta, getBody().getLinearVelocity().y);
        }
        y = y + pushY;
        if (y == 2) y = 1;
        if (y == -2) y = -1;

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


        TextureRegion explosion = (TextureRegion) aboom.getKeyFrame(animState, true);
        float aw = explosion.getRegionWidth();
        float ah = explosion.getRegionHeight();

        batch.begin();
        batch.draw(current, getBody().getPosition().x * Constants.PPM - w, getBody().getPosition().y * Constants.PPM - h, 0, 0, w, h, 2, 2, 0);

        if (System.currentTimeMillis() - lastbong < 500) {
            batch.draw(explosion, boompX - aw / 4, boompY - ah / 4,
                    0, 0, aw / 2, ah / 2, 1, 1, 0);
        }

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
            if (gow.isAudible()) {
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
        long time = System.currentTimeMillis();


        if (time - lastbong > 20) {
            if (!isVictorious()) {
                this.collisionNb = getCollisionNb() + 1;
            }

            if (gow.isAudible()) {
                if (time - lastbong > 200) {

                    int i = (int) (Math.random() * 6);
                    gow.snd_bong[i].play();
                }
            }
            lastbong = time;
        }

    }

    public void collisionOccured(float x, float y) {
        //System.out.println(" collision occured : " + x + " y :" + y);
        boompX = x * Constants.PPM;
        boompY = y * Constants.PPM;
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
