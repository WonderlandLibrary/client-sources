/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module.impl.render;

import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.NumberSetting;

public class ChestChams
extends Module {
    public static BooleanSetting colored = new BooleanSetting("Colored", true);
    public static BooleanSetting material = new BooleanSetting("Material", true);
    public static NumberSetting red = new NumberSetting("Red", 53.0, 1.0, 255.0, 1.0);
    public static NumberSetting green = new NumberSetting("Green", 157.0, 1.0, 255.0, 1.0);
    public static NumberSetting blue = new NumberSetting("Blue", 255.0, 1.0, 255.0, 1.0);
    public static NumberSetting red_hidden = new NumberSetting("Red Hidden", 255.0, 1.0, 255.0, 1.0);
    public static NumberSetting green_hidden = new NumberSetting("Green Hidden", 54.0, 1.0, 255.0, 1.0);
    public static NumberSetting blue_hidden = new NumberSetting("Blue Hidden", 54.0, 1.0, 255.0, 1.0);
    public static NumberSetting transparency = new NumberSetting("Transparency", 230.0, 1.0, 255.0, 1.0);

    public ChestChams() {
        super("Chest Chams", "Chinnese government for jews", 0, Category.Render);
        this.addSettings(colored, material, red, green, blue, red_hidden, green_hidden, blue_hidden, transparency);
    }
}

