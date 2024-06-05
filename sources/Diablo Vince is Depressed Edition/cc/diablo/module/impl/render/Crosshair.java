/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module.impl.render;

import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;

public class Crosshair
extends Module {
    public static NumberSetting red = new NumberSetting("Red", 70.0, 1.0, 255.0, 1.0);
    public static NumberSetting green = new NumberSetting("Green", 243.0, 1.0, 255.0, 1.0);
    public static NumberSetting blue = new NumberSetting("Blue", 99.0, 1.0, 255.0, 1.0);
    public static NumberSetting thicknes = new NumberSetting("Thickness", 0.5, 1.0, 4.0, 0.5);
    public static NumberSetting length = new NumberSetting("Length", 7.0, 1.0, 20.0, 1.0);

    public Crosshair() {
        super("Crosshair", "Customize your ingame crosshair", 0, Category.Render);
        this.addSettings(red, green, blue, thicknes, length);
    }
}

