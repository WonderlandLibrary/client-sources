/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump.LongJumpBypass2;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.longjump.LongJumpBypass2.LongJumpBypassMode;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class AAC
extends LongJumpBypassMode {
    public Timer time = new Timer();
    public boolean fuckYou = false;

    public AAC(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event)) {
            if (!AAC.mc.thePlayer.onGround && (double)AAC.mc.thePlayer.fallDistance > 0.67) {
                ClientUtils.setMoveSpeed(event, 2.9);
                this.fuckYou = true;
            }
            if (this.time.delay(1.0E8f)) {
                this.time.reset();
            }
        }
        return true;
    }

    @Override
    public boolean disable() {
        if (super.disable()) {
            AAC.mc.thePlayer.speedInAir = 0.02f;
        }
        return true;
    }
}

