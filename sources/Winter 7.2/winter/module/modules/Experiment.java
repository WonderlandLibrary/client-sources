/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import winter.event.EventListener;
import winter.event.events.TickEvent;
import winter.module.Module;
import winter.utils.other.Timer;

public class Experiment
extends Module {
    public static Timer jumpTimer;
    public static boolean addedThisTick;
    public static int damage;
    public static int jumps;

    static {
        addedThisTick = false;
    }

    public Experiment() {
        super("Experiment", Module.Category.Exploits, -2986855);
        this.setBind(33);
        jumpTimer = new Timer();
    }

    @Override
    public void onEnable() {
        jumps = 0;
        damage = 0;
    }

    @EventListener
    public void onTick(TickEvent event) {
        if (jumps > 5) {
            jumps = 0;
            damage = 0;
        }
        if (this.mc.thePlayer.hurtTime == 9) {
            ++damage;
        }
        if (jumpTimer.hasPassed(250.0f) && damage >= 6) {
            jumpTimer.reset();
            this.mc.thePlayer.motionY = 0.4;
            ++jumps;
        }
    }
}

