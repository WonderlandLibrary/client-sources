// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import me.chrest.event.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import me.chrest.utils.ClientUtils;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.client.module.Module;

@Mod
public class Sneak extends Module
{
    @EventTarget(3)
    public void onEvent(final UpdateEvent event) {
        final Event.State state = event.getState();
        event.getState();
        if (state == Event.State.PRE) {
            if (ClientUtils.player().isSneaking() || (ClientUtils.player().movementInput.moveForward == 0.0f && ClientUtils.player().movementInput.moveStrafe == 0.0f)) {
                return;
            }
            ClientUtils.player().sendQueue.addToSendQueue(new C0BPacketEntityAction(ClientUtils.player(), C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        else {
            final Event.State state2 = event.getState();
            event.getState();
            if (state2 == Event.State.POST) {
                ClientUtils.player().sendQueue.addToSendQueue(new C0BPacketEntityAction(ClientUtils.player(), C0BPacketEntityAction.Action.START_SNEAKING));
            }
        }
    }
    
    public void onDisable() {
        ClientUtils.player().sendQueue.addToSendQueue(new C0BPacketEntityAction(ClientUtils.player(), C0BPacketEntityAction.Action.STOP_SNEAKING));
        super.disable();
    }
}
