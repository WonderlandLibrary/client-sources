// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement;

import me.chrest.event.EventTarget;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.MoveEvent;
import me.chrest.client.module.Module;

@Mod(displayName = "Fast Ladder")
public class FastLadder extends Module
{
    private static final double MAX_LADDER_SPEED = 0.287299999999994;
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        if (event.getY() > 0.0 && ClientUtils.player().isOnLadder()) {
            event.setY(0.287299999999994);
        }
    }
}
