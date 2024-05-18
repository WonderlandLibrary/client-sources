/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.movement;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.utils.ClientUtils;

@Module.Mod(displayName="FastSwim")
public class FastSwim
extends Module {
    public int ticks;

    @EventTarget
    public void moveEntity(MoveEvent event) {
        if (ClientUtils.player().isInWater()) {
            ++this.ticks;
            if (this.ticks == 4) {
                ClientUtils.setMoveSpeed(event, 0.4000000059604645);
            }
            if (this.ticks >= 5) {
                ClientUtils.setMoveSpeed(event, 0.30000001192092896);
                this.ticks = 0;
            }
        }
    }

    public void onDisabled() {
        super.disable();
        this.ticks = 0;
    }
}

