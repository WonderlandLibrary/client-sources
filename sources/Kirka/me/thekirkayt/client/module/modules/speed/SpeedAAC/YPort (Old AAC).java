/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed.SpeedAAC;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.SpeedAAC.SpeedMode;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.MoveUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class YPort (Old AAC)
extends SpeedMode {
    public YPort (Old AAC)(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onTick(TickEvent event) {
        if (super.onTick(event) && MoveUtils.isMoving() && ClientUtils.player() != null) {
            if (ClientUtils.player().onGround) {
                ClientUtils.player().jump();
            } else {
                ClientUtils.player().motionY = -0.20000000298023224;
            }
        }
        return true;
    }
}

