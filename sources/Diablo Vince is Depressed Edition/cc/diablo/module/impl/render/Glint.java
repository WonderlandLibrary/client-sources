/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module.impl.render;

import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;

public class Glint
extends Module {
    public static NumberSetting red = new NumberSetting("Red", 53.0, 1.0, 255.0, 1.0);
    public static NumberSetting green = new NumberSetting("Green", 157.0, 1.0, 255.0, 1.0);
    public static NumberSetting blue = new NumberSetting("Blue", 255.0, 1.0, 255.0, 1.0);

    public Glint() {
        super("Glint", "Changes the glint color", 0, Category.Render);
        this.addSettings(red, green, blue);
    }
}

