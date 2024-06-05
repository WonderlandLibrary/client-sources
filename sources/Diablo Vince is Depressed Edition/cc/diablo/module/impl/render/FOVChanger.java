/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.render;

import cc.diablo.event.impl.TickEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;

public class FOVChanger
extends Module {
    public static NumberSetting fov = new NumberSetting("FOV", 135.0, 10.0, 200.0, 10.0);
    public static float oldFOV;

    public FOVChanger() {
        super("FOVChanger", "Changes FOV", 0, Category.Render);
        this.addSettings(fov);
    }

    @Override
    public void onEnable() {
        oldFOV = FOVChanger.mc.gameSettings.fovSetting;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        FOVChanger.mc.gameSettings.fovSetting = oldFOV;
        super.onDisable();
    }

    @Subscribe
    public void onTick(TickEvent e) {
        FOVChanger.mc.gameSettings.fovSetting = (float)fov.getVal();
    }
}

