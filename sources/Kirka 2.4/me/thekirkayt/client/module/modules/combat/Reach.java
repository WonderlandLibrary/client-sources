/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.TickEvent;

@Module.Mod
public class Reach
extends Module {
    @Option.Op(min=3.0, max=6.0, increment=0.1)
    public static double reach;
    public static boolean isnabled;

    public void onEnable() {
        isnabled = true;
    }

    public void onDisable() {
        isnabled = false;
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateTick();
    }

    private void updateTick() {
        this.setSuffix(String.valueOf(reach));
    }
}

