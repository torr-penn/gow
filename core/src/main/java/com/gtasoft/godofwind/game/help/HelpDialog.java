package com.gtasoft.godofwind.game.help;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gtasoft.godofwind.game.utils.BackgroundColor;


public class HelpDialog extends Dialog {


    BackgroundColor backgroundColor;
    private String tittle;


    public HelpDialog(String tittle, Skin skin, String wStyleName) {
        super(tittle, skin, wStyleName);

        this.tittle = tittle;
        create();
    }

    private void initialize() {

        padTop(25);
        padLeft(20);
        padRight(20);
        padBottom(10);

        setModal(true);


    }

    public void create() {


        Group myGroup = new Group();
        Label labProposal = new Label(" 1- Define wind orientation \n (space, Ctrl, mouse, arrows...) ", getSkin());//We respect your will. No shortcut!
        Label labSubProposal = new Label(" 2- Then play using arrows\n And please... Do not break the Egg!", getSkin());//"There is not much to do in the spaceship.\n You have spare time anyway..."
        Label.LabelStyle ls = new Label.LabelStyle();
        ls.fontColor = Color.WHITE;
        ls.font = getSkin().getFont("montserrat");
        labProposal.setStyle(ls);
        labSubProposal.setFontScale(0.7f);
        labProposal.setPosition(0, 70);

        Label.LabelStyle lsmall = new Label.LabelStyle();
        lsmall.fontColor = Color.LIGHT_GRAY;
        lsmall.font = getSkin().getFont("default-font");
        labSubProposal.setStyle(ls);
        labSubProposal.setFontScale(0.6f);
        labSubProposal.setPosition(0, 15);


        myGroup.addActor(labSubProposal);

        myGroup.addActor(labProposal);

        getContentTable().addActor(myGroup);


        TextButton tb = new TextButton("OK", getSkin());

        Label labok = new Label("OK", getSkin());
        labok.setStyle(ls);
        tb.setLabel(labok);
        button(tb);

        initialize();

    }

    @Override
    protected void result(Object o) {

    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return 480f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return 240f;
    }
}

