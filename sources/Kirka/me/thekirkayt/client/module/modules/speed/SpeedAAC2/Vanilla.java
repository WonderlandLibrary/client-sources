/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed.SpeedAAC2;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.SpeedAAC2;
import me.thekirkayt.client.module.modules.speed.SpeedAAC2.SpeedMode;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class Vanilla
extends SpeedMode {
    public Vanilla(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event) && ClientUtils.player().onGround) {
            ClientUtils.setMoveSpeed(event, ((SpeedAAC2)this.getModule()).speed);
        }
        return true;
    }
}

