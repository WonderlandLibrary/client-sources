/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import winter.event.EventListener;
import winter.event.events.TickEvent;
import winter.module.Module;

public class Speedmine
extends Module {
    public Speedmine() {
        super("Speedmine", Module.Category.World, -3239783);
        this.setBind(25);
    }

    @EventListener
    public void onTick(TickEvent event) {
        if (this.mc.playerController.curBlockDamageMP > 0.8f) {
            this.mc.playerController.curBlockDamageMP = 1.0f;
        }
        this.mc.playerController.blockHitDelay = 0;
    }
}

