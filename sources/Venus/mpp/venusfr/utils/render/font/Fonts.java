/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render.font;

import mpp.venusfr.utils.render.font.Font;

public class Fonts {
    public static Font montserrat;
    public static Font consolas;
    public static Font icons;
    public static Font damage;
    public static Font sfui;
    public static Font sfbold;
    public static Font sfMedium;
    public static Font mslight;
    public static Font gilroybold;

    public static void register() {
        montserrat = new Font("Montserrat-Regular.ttf.png", "Montserrat-Regular.ttf.json");
        icons = new Font("icons.ttf.png", "icons.ttf.json");
        consolas = new Font("consolas.ttf.png", "consolas.ttf.json");
        damage = new Font("damage.ttf.png", "damage.ttf.json");
        sfui = new Font("sf_semibold.ttf.png", "sf_semibold.ttf.json");
        sfbold = new Font("sf_bold.ttf.png", "sf_bold.ttf.json");
        sfMedium = new Font("sf_medium.ttf.png", "sf_medium.ttf.json");
        mslight = new Font("Montserrat-Light.ttf.png", "Montserrat-Light.ttf.json");
        gilroybold = new Font("gilroy-bold.ttf.png", "gilroy-bold.ttf.json");
    }
}

