/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed.SpeedNCP2;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.SpeedNCP2;
import me.thekirkayt.client.module.modules.speed.SpeedNCP2.SpeedMode;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class Speed
extends SpeedMode {
    public Speed(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event) && ClientUtils.player().onGround) {
            ClientUtils.setMoveSpeed(event, ((SpeedNCP2)this.getModule()).speed);
        }
        return true;
    }
}

