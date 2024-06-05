/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.TickEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;

public class Timer
extends Module {
    public NumberSetting speed = new NumberSetting("Speed", 2.0, 0.1, 10.0, 0.1);

    public Timer() {
        super("Timer", "Changes game time", 0, Category.Misc);
        this.addSettings(this.speed);
    }

    @Override
    public void onEnable() {
        net.minecraft.util.Timer.timerSpeed = 1.0f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        net.minecraft.util.Timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @Subscribe
    public void onPacket(TickEvent e) {
        if (Timer.mc.thePlayer != null) {
            this.setDisplayName("Timer\u00a77 " + this.speed.getVal());
            net.minecraft.util.Timer.timerSpeed = (float)this.speed.getVal();
        }
    }
}

