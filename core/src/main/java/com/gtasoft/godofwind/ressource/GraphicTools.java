package com.gtasoft.godofwind.ressource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GraphicTools {
    Label labelLoad;

    Label.LabelStyle lblStyle;
    String msgLoading;
    long tx = 0;
    private GraphicTools _instance = null;
    private Skin skin;
    private Texture imgLoad = null;
    private Texture imgExit;
    private Texture imgBackground;
    private Texture imgSea;
    private Texture imgPlay;
    private Texture imgStar;
    private Texture imgNoStar;
    private Texture imgScoreBox;
    private Texture imgVictory;


    private Texture imgHiddenCell64;
    private Texture imgHiddenCell96;


    private Texture imgNext;
    private Texture imgBack;
    private Texture imgRestart;

    public GraphicTools() {
        setSkin(new Skin(Gdx.files.internal("ui/gowskin.json")));
        imgLoad = new Texture(Gdx.files.internal("img/menu/loading-icon-small.png"));
        lblStyle = new Label.LabelStyle();
        lblStyle.font = this.getSkin().getFont("jaapokki");
        lblStyle.fontColor = skin.getColor("darkgreen");


        imgHiddenCell64 = new Texture(Gdx.files.internal("img/board/grid/question_cell_64.png"));
        imgHiddenCell96 = new Texture(Gdx.files.internal("img/board/grid/question_cell_96.png"));
        imgNext = new Texture(Gdx.files.internal("img/menu/next-icon.png"));
        skin.add("imgNext", imgNext);

        setImgScoreBox(new Texture(Gdx.files.internal("img/board/score_box.png")));
        skin.add("imgScoreBox", getImgScoreBox());


        imgExit = new Texture(Gdx.files.internal("img/menu/cross-icon-small.png"));
        skin.add("imgExit", imgExit);

        imgPlay = new Texture(Gdx.files.internal("img/menu/game-start-icon.png"));
        skin.add("imgPlay", imgPlay);

        this.imgBack = new Texture(Gdx.files.internal("img/menu/back-icon-medium.png"));
        skin.add("imgBack", imgBack);

        this.setImgStar(new Texture(Gdx.files.internal("img/menu/ranking/star.png")));
        skin.add("imgStar", getImgStar());

        this.setImgNoStar(new Texture(Gdx.files.internal("img/menu/ranking/nostar.png")));
        skin.add("imgNoStar", getImgNoStar());

        this.setImgVictory(new Texture(Gdx.files.internal("img/board/victory.png")));
        skin.add("imgVictory", getImgVictory());


        imgRestart = new Texture(Gdx.files.internal("img/menu/game-retry-icon.png"));
        skin.add("imgRestart", imgRestart);

        msgLoading = "Loading..";
        labelLoad = new Label(msgLoading, getSkin());
        labelLoad.setStyle(lblStyle);
        labelLoad.setName("loading");
        labelLoad.setSize(120, 20);
        imgBackground = new Texture(Gdx.files.internal("img/menu/bg_space_1.png"));
        imgSea = new Texture(Gdx.files.internal("img/board/bg_sea.png"));
        getImgSea().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        getImgBackground().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }


    public void isLoadingImg(SpriteBatch sb, int posx, int posy) {
        if (sb != null)
            sb.draw(this.imgLoad, posx, posy);
    }

    public void isLoadingText(boolean visible, Stage stage, int posx, int posy) {
        if (visible) {
            for (Actor ac : stage.getActors()) {
                if ("loading".equals(ac.getName())) {
                    Label lb1 = (Label) ac;
                    lb1.setText(msgLoading);
                    break;
                }
            }
        }
        isLoadingTextWithAnimation(visible, stage, posx, posy, false);
    }

    public String randomSpace(String str) {

        if (str != null) {
            int l = str.length();
            if (l > 1) {
                int x = (int) Math.round((l - 1) * Math.random()) + 1;
                if (x < l - 1) {
                    return str.substring(0, x) + " " + str.substring(x + 1, l);
                } else {
                    return str.substring(0, x) + " ";
                }

            }

        }
        return ".";
    }

    public void isLoadingTextWithAnimation(boolean visible, Stage stage, int posx, int posy, boolean animated) {

        if (animated) {
            long t1 = System.currentTimeMillis();
            if (t1 > tx) {
                tx = t1 + 400;
                if (visible) {
                    for (Actor ac : stage.getActors()) {
                        if ("loading".equals(ac.getName())) {
                            Label lb1 = (Label) ac;
                            lb1.setText(randomSpace(msgLoading));
                            break;
                        }
                    }

                }
            }
        }
        boolean found = false;

        labelLoad.setPosition(posx, posy);

        if (visible) {

            for (Actor ac : stage.getActors()) {
                if ("loading".equals(ac.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                stage.addActor(labelLoad);
            }


        } else {
            for (Actor ac : stage.getActors()) {
                if ("loading".equals(ac.getName())) {
                    ac.remove();
                    break;
                }
            }
        }

    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public void dispose() {
        imgExit.dispose();
        imgBackground.dispose();
    }

    public Texture getImgBackground() {
        return imgBackground;
    }


    public Texture getImgExit() {
        return imgExit;
    }


    public Texture getImgPlay() {
        return imgPlay;
    }

    public Texture getImgBack() {
        return imgBack;
    }

    public Texture getImgNext() {
        return imgNext;
    }

    public Texture getImgHiddenCell64() {
        return imgHiddenCell64;
    }

    public Texture getImgHiddenCell96() {
        return imgHiddenCell96;
    }

    public Texture getImgSea() {
        return imgSea;
    }

    public void setImgSea(Texture imgSea) {
        this.imgSea = imgSea;
    }

    public Texture getImgScoreBox() {
        return imgScoreBox;
    }

    public void setImgScoreBox(Texture imgScoreBox) {
        this.imgScoreBox = imgScoreBox;
    }

    public Texture getImgVictory() {
        return imgVictory;
    }

    public void setImgVictory(Texture imgVictory) {
        this.imgVictory = imgVictory;
    }

    public Texture getImgStar() {
        return imgStar;
    }

    public void setImgStar(Texture imgStar) {
        this.imgStar = imgStar;
    }

    public Texture getImgNoStar() {
        return imgNoStar;
    }

    public void setImgNoStar(Texture imgNoStar) {
        this.imgNoStar = imgNoStar;
    }
}
