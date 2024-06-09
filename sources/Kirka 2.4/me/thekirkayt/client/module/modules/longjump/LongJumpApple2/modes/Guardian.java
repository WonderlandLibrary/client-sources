/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump.LongJumpApple2.modes;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.longjump.LongJumpApple2.LongJumpAppleMode;
import me.thekirkayt.client.module.modules.speed.SpeedNCP.Bunny Hop (Old NCP);
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Guardian
extends LongJumpAppleMode {
    public Guardian(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (super.onUpdate(event)) {
            if (ClientUtils.mc().thePlayer.moveForward != 0.0f || ClientUtils.mc().thePlayer.moveStrafing != 0.0f) {
                if (ClientUtils.mc().thePlayer.onGround) {
                    Bunny Hop (Old NCP).setSpeed((double)7.5);
                    ClientUtils.mc().thePlayer.motionY = 0.4255;
                } else {
                    Bunny Hop (Old NCP).setSpeed((double)((float)Math.sqrt(ClientUtils.mc().thePlayer.motionX * ClientUtils.mc().thePlayer.motionX + ClientUtils.mc().thePlayer.motionZ * ClientUtils.mc().thePlayer.motionZ)));
                }
            } else {
                ClientUtils.mc().thePlayer.motionX = 0.0;
                ClientUtils.mc().thePlayer.motionZ = 0.0;
            }
        }
        return true;
    }
}

