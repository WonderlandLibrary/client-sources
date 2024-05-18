// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.render;

import me.chrest.event.EventTarget;
import me.chrest.utils.ClientUtils;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Anti-Effects", shown = false)
public class AntiEffects extends Module
{
    private static final int NAUSEA_ID = 9;
    private static final int BLINDNESS_ID = 15;
    @Option.Op
    private boolean nausea;
    @Option.Op
    private boolean blindness;
    
    public AntiEffects() {
        this.nausea = true;
        this.blindness = true;
    }
    
    @EventTarget
    private void onTick(final UpdateEvent event) {
        if (event.getState() == Event.State.POST && ClientUtils.player().isPotionActive(9) && this.nausea) {
            ClientUtils.player().removePotionEffect(9);
        }
        if (event.getState() == Event.State.POST && ClientUtils.player().isPotionActive(9) && this.blindness) {
            ClientUtils.player().removePotionEffect(15);
        }
    }
    
    public boolean isBlindness() {
        return this.blindness;
    }
}
