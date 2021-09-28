package com.gtasoft.godofwind.game.contact;

import com.badlogic.gdx.physics.box2d.*;
import com.gtasoft.godofwind.game.WindManager;
import com.gtasoft.godofwind.game.entity.ColorZoneBody;
import com.gtasoft.godofwind.game.entity.Player;
import com.gtasoft.godofwind.game.entity.WallBody;
import com.gtasoft.godofwind.game.entity.WinnerZoneBody;

public class MyContactListener implements ContactListener {
    int previouscolor = WindManager.GREEN;

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        if (isColorContact(fa, fb)) {
            Player pl;
            ColorZoneBody czb = null;
            if (fa.getUserData() instanceof Player) {
                pl = (Player) fa.getUserData();
                czb = (ColorZoneBody) fb.getUserData();
            } else {
                pl = (Player) fb.getUserData();
                czb = (ColorZoneBody) fa.getUserData();
            }

            pl.getWindManager().setMasterDirection(czb.color);
            previouscolor = czb.color;

            pl.addNbpush();
        }


        if (isWallContact(fa, fb)) {

            Player pl = null;
            WallBody wb = null;
            if (fa.getUserData() instanceof Player) {
                pl = (Player) fa.getUserData();
                wb = (WallBody) fb.getUserData();
            } else {
                pl = (Player) fb.getUserData();
                wb = (WallBody) fa.getUserData();
            }
            pl.addCollision();


        }
        if (isWinnerContact(fa, fb)) {
            Player pl;
            if (fa.getUserData() instanceof Player) {
                pl = (Player) fa.getUserData();
            } else {
                pl = (Player) fb.getUserData();
            }
            pl.setVictory(true);
        }


    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;
        if (isColorContact(fa, fb)) {
            Player pl;
            ColorZoneBody czb = null;
            if (fa.getUserData() instanceof Player) {
                pl = (Player) fa.getUserData();
                czb = (ColorZoneBody) fb.getUserData();
            } else {
                pl = (Player) fb.getUserData();
                czb = (ColorZoneBody) fa.getUserData();
            }

            if (czb.color == pl.getWindManager().getMasterDirection()) {
                pl.getWindManager().setMasterDirection(previouscolor);
            }
            pl.substractNbpush();
        }


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private boolean isWallContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof WallBody || b.getUserData() instanceof WallBody) {
            if (a.getUserData() instanceof Player || b.getUserData() instanceof Player) {
                return true;
            }
        }
        return false;
    }

    private boolean isWinnerContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof WinnerZoneBody || b.getUserData() instanceof WinnerZoneBody) {
            if (a.getUserData() instanceof Player || b.getUserData() instanceof Player) {
                return true;
            }
        }
        return false;
    }


    private boolean isColorContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof ColorZoneBody || b.getUserData() instanceof ColorZoneBody) {
            if (a.getUserData() instanceof Player || b.getUserData() instanceof Player) {
                return true;
            }
        }
        return false;
    }

}
