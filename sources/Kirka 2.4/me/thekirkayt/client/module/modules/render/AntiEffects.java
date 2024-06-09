/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;

@Module.Mod(displayName="AntiEffects", shown=false)
public class AntiEffects
extends Module {
    private static final int NAUSEA_ID = 9;
    @Option.Op
    private boolean nausea = true;
    @Option.Op
    private boolean blindness = true;

    @EventTarget
    private void onTick(UpdateEvent event) {
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

