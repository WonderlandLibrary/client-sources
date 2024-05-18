// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.render;

import me.chrest.event.EventTarget;
import net.minecraft.potion.PotionEffect;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.UpdateEvent;
import me.chrest.client.module.Module;

@Mod(shown = false, displayName = "Full-Bright")
public class Brightness extends Module
{
    @EventTarget
    private void onTick(final UpdateEvent event) {
        ClientUtils.player().addPotionEffect(new PotionEffect(16, 1337, 1));
    }
    
    public void onDisable() {
        ClientUtils.player().removePotionEffect(16);
    }
}
