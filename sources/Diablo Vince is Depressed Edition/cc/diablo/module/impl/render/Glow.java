/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module.impl.render;

import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;

public class Glow
extends Module {
    public static NumberSetting red = new NumberSetting("Red", 45.0, 1.0, 255.0, 1.0);
    public static NumberSetting green = new NumberSetting("Green", 24.0, 1.0, 255.0, 1.0);
    public static NumberSetting blue = new NumberSetting("Blue", 158.0, 1.0, 255.0, 1.0);
    public static NumberSetting size = new NumberSetting("Size", 1.0, 1.0, 5.0, 1.0);

    public Glow() {
        super("Glow", "faggot esp", 0, Category.Render);
        this.addSettings(red, green, blue, size);
    }
}

