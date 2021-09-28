package com.gtasoft.godofwind.game.help;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gtasoft.godofwind.game.utils.BackgroundColor;


public class PreferenceDialog extends Dialog {


    BackgroundColor backgroundColor;
    private String tittle;


    public PreferenceDialog(String tittle, Skin skin, String wStyleName) {
        super(tittle, skin, wStyleName);

        this.tittle = tittle;
        create();
    }

    private void initialize() {

        padTop(25); // set padding on top of the dialog title
        padLeft(20);
        padRight(20);
        padBottom(10);

        setModal(true);


    }

    public void create() {


        Group myGroup = new Group();
        Label labProposal = new Label(" - Sound preference is ON ", getSkin());//We respect your will. No shortcut!
        Label labSubProposal = new Label("To play without sound, set computer volume to 0.\n" +
                "you may find how to disable music somehow.", getSkin());//"There is not much to do in the spaceship.\n You have spare time anyway..."
        Label.LabelStyle ls = new Label.LabelStyle();
        ls.fontColor = Color.WHITE;
        ls.font = getSkin().getFont("montserrat");
        labProposal.setStyle(ls);
        labProposal.setPosition(0, 100);

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

        return 480f;
    }

    @Override
    public float getPrefHeight() {

        return 240f;
    }
}

