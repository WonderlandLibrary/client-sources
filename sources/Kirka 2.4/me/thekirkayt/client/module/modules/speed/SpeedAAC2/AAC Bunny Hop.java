/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed.SpeedAAC2;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.SpeedAAC2.SpeedMode;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.MoveUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class AAC Bunny Hop
extends SpeedMode {
    public AAC Bunny Hop(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onTick(TickEvent event) {
        if (super.onTick(event) && MoveUtils.isMoving() && ClientUtils.player() != null) {
            ClientUtils.mc().gameSettings.keyBindJump.pressed = false;
            if (ClientUtils.mc().thePlayer.onGround) {
                ClientUtils.mc().thePlayer.jump();
            } else {
                double direction = MoveUtils.getDirection();
                double speed = 1.007;
                double Motion = Math.sqrt(ClientUtils.mc().thePlayer.motionX * ClientUtils.mc().thePlayer.motionX + ClientUtils.mc().thePlayer.motionZ * ClientUtils.mc().thePlayer.motionZ);
                ClientUtils.mc().thePlayer.motionX = -Math.sin(direction) * 1.007 * Motion;
                ClientUtils.mc().thePlayer.motionZ = Math.cos(direction) * 1.007 * Motion;
            }
        }
        return true;
    }
}

