// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.render;

import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;

@Mod(displayName = "Anti-Effects", shown = false)
public class AntiEffects extends Module
{
    private static final int NAUSEA_ID = 9;
    private static final int BLINDNESS_ID = 15;
    @Op
    private boolean nausea;
    @Op
    private boolean blindness;
    
    public AntiEffects() {
        this.nausea = true;
        this.blindness = true;
    }
    
    @EventTarget
    private void onTick(final UpdateEvent event) {
        if (event.getState() == Event.State.POST && ClientUtils.player().isPotionActive(9) && this.nausea) {
            ClientUtils.player().removePotionEffect(NAUSEA_ID);
        }
        if (event.getState() == Event.State.POST && ClientUtils.player().isPotionActive(9) && this.blindness) {
            ClientUtils.player().removePotionEffect(BLINDNESS_ID);
        }
    }
    
    public boolean isBlindness() {
        return this.blindness;
    }
}
