/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module.impl.render;

import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;

public class ItemOffset
extends Module {
    public static NumberSetting scale = new NumberSetting("Scale", 0.75, 0.1, 2.0, 0.05);
    public static NumberSetting xOffset = new NumberSetting("X", 3.0, -2.0, 2.0, 0.05);
    public static NumberSetting yOffset = new NumberSetting("Y", 0.45, -2.0, 2.0, 0.05);
    public static NumberSetting zOffset = new NumberSetting("Z", -0.3, -2.0, 2.0, 0.05);

    public ItemOffset() {
        super("Item Offset", "Offsets your item", 0, Category.Render);
        this.addSettings(scale, xOffset, yOffset, zOffset);
    }
}

