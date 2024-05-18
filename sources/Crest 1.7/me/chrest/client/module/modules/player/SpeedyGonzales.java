// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import me.chrest.event.EventTarget;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Mod(displayName = "SpeedyGonzales")
public class SpeedyGonzales extends Module
{
    Minecraft mc;
    
    public SpeedyGonzales() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @EventTarget
    public void onUpdate(final UpdateEvent event) {
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        final boolean item = ClientUtils.mc().thePlayer.getCurrentEquippedItem() == null;
        Minecraft.getMinecraft();
        ClientUtils.mc().thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 20, (int)(item ? 1 : 0)));
    }
    
    @Override
    public void disable() {
        super.disable();
        ClientUtils.player().removePotionEffect(Potion.digSpeed.getId());
    }
}
