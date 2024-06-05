/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.combat;

import cc.diablo.event.impl.SlowdownEvent;
import cc.diablo.helpers.MathHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.ModeSetting;
import com.google.common.eventbus.Subscribe;

public class KeepSprint
extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Normal", "Normal", "Invaded");

    public KeepSprint() {
        super("KeepSprint", "Anti Slowdown when hitting.", 0, Category.Combat);
        this.addSettings(this.mode);
    }

    @Subscribe
    public void onSlowdown(SlowdownEvent e) {
        e.setCancelled(true);
        switch (this.mode.getMode()) {
            case "Normal": {
                e.setCancelled(true);
                break;
            }
            case "Invaded": {
                e.setCancelled(true);
                KeepSprint.mc.thePlayer.motionX *= (double)MathHelper.getRandomInRange(0.9375f, 0.95f);
                KeepSprint.mc.thePlayer.motionZ *= (double)MathHelper.getRandomInRange(0.9375f, 0.95f);
            }
        }
    }
}

