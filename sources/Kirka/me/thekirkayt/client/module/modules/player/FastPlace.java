/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.player;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;

@Module.Mod(displayName="FastPlace")
public class FastPlace
extends Module {
    @Option.Op(name="Half Speed")
    private boolean halfSpeed;

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            ClientUtils.mc().rightClickDelayTimer = Math.min(ClientUtils.mc().rightClickDelayTimer, this.halfSpeed ? 2 : 1);
        }
    }
}

