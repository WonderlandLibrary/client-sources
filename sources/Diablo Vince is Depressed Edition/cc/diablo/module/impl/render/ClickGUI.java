/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module.impl.render;

import cc.diablo.Main;
import cc.diablo.module.Category;
import cc.diablo.module.Module;

public class ClickGUI
extends Module {
    public ClickGUI() {
        super("ClickGUI", "ClickGUI", 54, Category.Render);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Main.getInstance().getClickGUI());
        this.toggle();
        super.onEnable();
    }
}

