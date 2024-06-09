/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.bypass;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.util.Timer;

@Module.Mod(displayName="SuperFastLadderSpartan")
public class SuperFastLadderSpartan
extends Module {
    private static final double MAX_LADDER_SPEED = 0.999999999999999;

    @EventTarget
    private void onMove(MoveEvent event) {
        Timer.timerSpeed = 1.0f;
        if (event.getY() > 0.0 && ClientUtils.player().isOnLadder()) {
            event.setY(0.999999999999999);
        }
    }
}

