package com.gtasoft.godofwind.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.TimeUtils;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;
import com.gtasoft.godofwind.GodOfWind;

import com.google.gwt.core.client.GWT;

/**
 * Launches the GWT application.
 */
public class GwtLauncher extends GwtApplication {
    GwtApplicationConfiguration config = null;
    long loadStart = TimeUtils.nanoTime();

    @Override
    public GwtApplicationConfiguration getConfig() {
        // Resizable application, uses available space in browser
        if (config == null) {
            config = new GwtApplicationConfiguration(true);
        }
        return config;
        // Fixed size application:
        //return new GwtApplicationConfiguration(480, 320);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        HtmlPlatform hp = new HtmlPlatform();
        return new GodOfWind(hp);
    }


    public Preloader.PreloaderCallback getPreloaderCallbackx() {
        return createPreloaderPanel(GWT.getHostPageBaseURL() + "preloadlogo.png");
    }

    @Override
    protected void adjustMeterPanel(Panel meterPanel, Style meterStyle) {
        meterPanel.setStyleName("gdx-meter");
        meterPanel.addStyleName("green");
        meterStyle.setProperty("backgroundColor", "#BFFFBF");
        meterStyle.setProperty("backgroundImage", GWT.getHostPageBaseURL() + "preloadbg.png");
    }

    @Override
    public Preloader.PreloaderCallback getPreloaderCallback() {
        final Panel preloaderPanel = new VerticalPanel();
        preloaderPanel.setStyleName("gdx-preloader");
        preloaderPanel.getElement().getStyle().setBackgroundColor("#BFFFBF");
        final Image logo = new Image(GWT.getHostPageBaseURL() + "preloadlogo.png");
        logo.setStyleName("logo");
        preloaderPanel.add(logo);
        final Panel meterPanel = new SimplePanel();

        meterPanel.setStyleName("gdx-meter");
        meterPanel.addStyleName("green");

        final InlineHTML meter = new InlineHTML();
        final Style meterStyle = meter.getElement().getStyle();
        meterStyle.setBackgroundColor("#BBFFBB");
        meterStyle.setWidth(0, Style.Unit.PCT);
        meterPanel.add(meter);
        preloaderPanel.add(meterPanel);
        getRootPanel().add(preloaderPanel);
        return new Preloader.PreloaderCallback() {

            @Override
            public void error(String file) {
                System.out.println("error: " + file);
            }

            @Override
            public void update(Preloader.PreloaderState state) {
                meterStyle.setWidth(100f * state.getProgress(), Style.Unit.PCT);
            }
        };
    }

}
