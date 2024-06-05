/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module.impl.render;

import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.Setting;
import cc.diablo.setting.impl.NumberSetting;

public class SlowHit
extends Module {
    public static NumberSetting speed = new NumberSetting("Speed", 20.0, 1.0, 30.0, 1.0);

    public SlowHit() {
        super("SlowHit", "Slows down the player's swing animation", 0, Category.Render);
        this.addSettings(new Setting[0]);
    }
}

