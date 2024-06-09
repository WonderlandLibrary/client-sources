/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat.AntiKnockBack;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.combat.AntiKnockBack.AntiKnockBack Mode;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class AAC
extends AntiKnockBack Mode {
    public AAC(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (super.onUpdate(event) && !ClientUtils.player().onGround && ClientUtils.player().hurtTime != 0) {
            double yaw = ClientUtils.mc().thePlayer.rotationYawHead;
            yaw = Math.toRadians(yaw);
            double motionX = -Math.sin(yaw) * 0.08;
            double motionZ = Math.cos(yaw) * 0.08;
            ClientUtils.mc().thePlayer.motionX = motionX;
            ClientUtils.mc().thePlayer.motionZ = motionZ;
        }
        return true;
    }
}

