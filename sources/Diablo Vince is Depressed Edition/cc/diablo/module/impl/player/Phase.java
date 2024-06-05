/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.player;

import cc.diablo.event.impl.CollideEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.util.Timer;

public class Phase
extends Module {
    public ModeSetting mode = new ModeSetting("Phase Mode", "Watchdog", "Watchdog", "Invaded");
    public NumberSetting distance = new NumberSetting("Distance", 1.1, 0.1, 8.0, 0.1);

    public Phase() {
        super("Phase", "Phase through blocks", 0, Category.Player);
        this.addSettings(this.mode, this.distance);
    }

    @Override
    public void onEnable() {
        switch (this.mode.getMode()) {
            case "Watchdog": {
                Phase.mc.thePlayer.setPosition(Phase.mc.thePlayer.posX, Phase.mc.thePlayer.posY - this.distance.getVal(), Phase.mc.thePlayer.posZ);
                this.setToggled(false);
            }
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        this.setDisplayName("Phase\u00a77 " + this.mode.getMode());
    }

    @Subscribe
    public void onCollide(CollideEvent event) {
    }
}

