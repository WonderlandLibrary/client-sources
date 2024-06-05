package me.mees.remix.font;

import java.awt.*;

public abstract class FontLoaders
{
    public static CFontRenderer confortaa22;
    public static CFontRenderer confortaa20;
    public static CFontRenderer confortaa18;
    public static CFontRenderer confortaa17;
    public static CFontRenderer confortaa16;
    public static CFontRenderer consolas18;
    
    static {
        FontLoaders.confortaa22 = new CFontRenderer(new Font("Comfortaa", 0, 22), true, true);
        FontLoaders.confortaa20 = new CFontRenderer(new Font("Comfortaa", 0, 20), true, true);
        FontLoaders.confortaa18 = new CFontRenderer(new Font("Comfortaa", 0, 20), true, true);
        FontLoaders.confortaa17 = new CFontRenderer(new Font("Comfortaa", 0, 17), true, true);
        FontLoaders.confortaa16 = new CFontRenderer(new Font("Comfortaa", 0, 16), true, true);
        FontLoaders.consolas18 = new CFontRenderer(new Font("Consolas", 0, 18), true, true);
    }
}
