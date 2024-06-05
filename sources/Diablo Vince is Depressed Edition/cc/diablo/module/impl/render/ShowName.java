/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module.impl.render;

import cc.diablo.module.Category;
import cc.diablo.module.Module;

public class ShowName
extends Module {
    public ShowName() {
        super("ShowName", "Renders Your Own Nametag", 0, Category.Render);
    }

    @Override
    public void onEnable() {
        ShowName.mc.thePlayer.setAlwaysRenderNameTag(true);
    }

    @Override
    public void onDisable() {
        ShowName.mc.thePlayer.setAlwaysRenderNameTag(false);
    }
}

