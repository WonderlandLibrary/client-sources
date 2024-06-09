/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Timer;
import winter.Client;
import winter.event.EventListener;
import winter.event.events.TickEvent;
import winter.module.Module;

public class Step
extends Module {
    private boolean didSend;
    private int sendTicks;

    public Step() {
        super("Step", Module.Category.Movement, -11044199);
        this.setBind(44);
    }

    @EventListener
    public void onTick(TickEvent event) {
        this.mode(" Timer");
        if (!Client.isEnabled("Speed")) {
            if ((this.mc.thePlayer.motionX != 0.0 || this.mc.thePlayer.motionZ != 0.0) && this.mc.thePlayer.isCollidedHorizontally && !this.mc.thePlayer.isOnLadder()) {
                if (this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.motionY = 0.3998;
                    this.mc.timer.timerSpeed = 2.5f;
                } else if (this.mc.thePlayer.motionY <= -0.162) {
                    this.mc.thePlayer.motionY = 0.26;
                    this.mc.timer.timerSpeed = 1.337f;
                }
            } else {
                this.mc.timer.timerSpeed = 1.0f;
            }
        }
    }
}

