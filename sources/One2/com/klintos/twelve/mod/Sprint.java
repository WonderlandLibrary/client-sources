// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.entity.EntityPlayerSP;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.mod.events.EventPreUpdate;

public class Sprint extends Mod
{
    public Sprint() {
        super("Sprint", 47, ModCategory.PLAYER);
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        final boolean forward = Sprint.mc.gameSettings.keyBindForward.pressed;
        final boolean back = Sprint.mc.gameSettings.keyBindBack.pressed;
        final boolean left = Sprint.mc.gameSettings.keyBindLeft.pressed;
        final boolean right = Sprint.mc.gameSettings.keyBindRight.pressed;
        if ((forward | left | right) && Sprint.mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
            if ((left | right) && !forward && !back && Sprint.mc.thePlayer.onGround && !Twelve.getInstance().getModHandler().getMod("Speed").getEnabled()) {
                final EntityPlayerSP thePlayer = Sprint.mc.thePlayer;
                thePlayer.motionX *= 1.25;
                final EntityPlayerSP thePlayer2 = Sprint.mc.thePlayer;
                thePlayer2.motionZ *= 1.25;
            }
            Sprint.mc.thePlayer.setSprinting(true);
        }
        else {
            Sprint.mc.thePlayer.setSprinting(false);
        }
    }
}
