// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import me.chrest.event.EventTarget;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Mod(displayName = "PotionSaver")
public class PotionSaver extends Module
{
    Minecraft mc;
    
    public PotionSaver() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE && this.mc.thePlayer.motionX == 0.0 && this.mc.thePlayer.motionZ == 0.0 && this.mc.thePlayer.isCollidedVertically) {
            event.setCancelled(true);
        }
        if (this.mc.thePlayer.motionX == 0.0 && this.mc.thePlayer.motionZ == 0.0 && this.mc.thePlayer.isCollidedVertically) {
            event.setCancelled(true);
        }
    }
}
