/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.auto;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;

@Module.Mod
public class AutoRespawn
extends Module {
    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.POST && !ClientUtils.player().isEntityAlive()) {
            ClientUtils.player().respawnPlayer();
        }
    }
}

