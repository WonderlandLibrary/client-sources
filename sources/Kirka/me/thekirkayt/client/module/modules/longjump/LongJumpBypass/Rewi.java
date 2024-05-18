/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump.LongJumpBypass;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.longjump.LongJumpBypass.LongJumpBypassMode;
import me.thekirkayt.event.events.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Rewi
extends LongJumpBypassMode {
    public Rewi(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onTick(TickEvent e) {
        if (super.onTick(e) && Rewi.mc.thePlayer.isAirBorne) {
            Rewi.mc.thePlayer.motionY = -0.004;
            Rewi.mc.thePlayer.speedInAir = 0.021f;
        }
        return true;
    }

    @Override
    public boolean disable() {
        if (super.disable()) {
            Rewi.mc.thePlayer.speedInAir = 0.02f;
        }
        return true;
    }
}

