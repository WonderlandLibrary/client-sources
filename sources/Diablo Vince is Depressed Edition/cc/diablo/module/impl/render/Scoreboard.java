/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module.impl.render;

import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.BooleanSetting;

public class Scoreboard
extends Module {
    public static BooleanSetting position = new BooleanSetting("Bottom Right", true);
    public static BooleanSetting clear = new BooleanSetting("Clear", false);

    public Scoreboard() {
        super("Scoreboard", "Edit ingame scoreboard", 0, Category.Render);
        this.addSettings(position, clear);
    }
}

