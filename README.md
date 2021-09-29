# God of Wind

# Original creation

This program was developped for libGDX JAM 18 in september 2021
https://itch.io/jam/libgdx-jam-18

# Assets

Original assets were created for the occasion. e.g. : https://opengameart.org/content/transparent-clouds-and-shadows

# Sound

created by Torr-Penn

# Music :

"Music by Devynn LaShure, Echo Blue Music

https://www.echobluemusic.com"

# references : Box2D

first tentative of usage of Box2D library : great success This was possible thanks to
https://github.com/libgdx/libgdx/wiki/Box2d

and very nice youtube effort here from conner Anderson:
https://www.youtube.com/c/ConnerAnderson1992/videos
(adapting some part of code from those video)

and some energetic videos of the nature of code from the coding train:
https://www.youtube.com/watch?v=6vX8wT1G798&list=PLRqwX-V7Uu6aFlwukCmDf0-1-uSR7mklK

# porting to GWT

it's the final task post jam List of issue founds and solution during the process. most of them are explained here :
https://github.com/libgdx/libgdx/wiki/HTML5-Backend-and-GWT-Specifics

1- html compilation issue -> usage of Gson for file storage or parsing I am used to Gson. Unfortunately this is not compatible with libgdx+Gwt.

solution :
https://github.com/libgdx/libgdx/wiki/Reading-and-writing-JSON
--> sadly the @expose annotation do not work so modify Entity when needed --> change all Gson for Json - syntax is close sometimes args are inverted

2- compilation output start with many different issues :
A)
After Gson, now it's java.util.Timer that is not compatible ->
switch to libgdx Timer version is required. as explained here :
https://stackoverflow.com/questions/15528500/java-timer-in-gwt

import com.badlogic.gdx.utils.Timer; instead of import java.util.Timer;

B) my util class for multilanguage support was not compatible... Solution : As I had no intention to add multilanguage, I removed all the integration part of the utility class.

C) I had to create an interface IScoreUtil so that the Android and Desktop version use file while the HTML version only use an in-Memory Top score. -> 3 different class ScoreUtil in project android, html and Desktop

D)  I initially didn't use the correct way to read file in that context.

Good one : import com.badlogic.gdx.files.FileHandle; Bad one :import java.io.FileReader;

solution : use the good one

E) impossible to use String.format()

so :   this : String display = String.format("%02d:%02d:%02d.%03d", secs / 3600, (secs % 3600) / 60, (secs % 60), milisec);

became String display = format("%s:%s:%s.%s", pad2Number(secs / 3600), pad2Number((secs % 3600) / 60), pad2Number(secs % 60), pad3Number(milisec));

with :
public String pad2Number(float f) { int i = (int) Math.floor(f); if (i < 10) { return "0" + i; } if (i > 99) { Gdx.app.log("[pad2Number]", " warning number too big"); return "" + i; } return "" + i; }

    public String pad3Number(float f) {
        int i = (int) Math.floor(f);
        if (i < 10) {
            return "00" + i;
        }
        if (i < 100) {
            return "0" + i;
        }
        if (i < 1000) {
            return "" + i;
        }
        Gdx.app.log("[pad3Number]", "warning number too big");

        return "" + i;
    }

# Running GWT :

so how to see the result :
1- in html :
gradle superdev

real link that work is: http://localhost:8080/index.html

Then fight in json file containing Level informations for it to be parseable in GWT

Again same mistake, every class using reflection has to be declared for GWT. so in GodOfWind.gwt.xml in the core lib add :
<extend-configuration-property name="gdx.reflect.include" value="com.gtasoft.godofwind.game.utils.LevelInfo"/>
and in html :
GdxDefinition.gwt.xml

	<extend-configuration-property name="gdx.reflect.include" value="com.gtasoft.godofwind.game.utils.LevelInfos"/>

Also for some reason, file with extension .json is not managed in the same way as .txt in order to json parsing to work I had to rename file into .txt... :-( another mystery for me. then finally solution was a simple switch gradle 7.1.1 -> 7.2 // and jdk 1.8 -> 15 to make it work after no clue in
parsing code change.




