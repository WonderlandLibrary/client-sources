/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.auto;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.MovementInput;

@Module.Mod(displayName="AutoSneak")
public class AutoSneak
extends Module {
    @EventTarget(value=3)
    public void onEvent(UpdateEvent event) {
        block7 : {
            block4 : {
                block6 : {
                    block5 : {
                        Event.State state = event.getState();
                        event.getState();
                        if (state != Event.State.PRE) break block4;
                        if (ClientUtils.player().isSneaking()) break block5;
                        if (MovementInput.moveForward != 0.0f) break block6;
                        if (MovementInput.moveStrafe != 0.0f) break block6;
                    }
                    return;
                }
                ClientUtils.player().sendQueue.addToSendQueue(new C0BPacketEntityAction(ClientUtils.player(), C0BPacketEntityAction.Action.STOP_SNEAKING));
                break block7;
            }
            Event.State state2 = event.getState();
            event.getState();
            if (state2 == Event.State.POST) {
                ClientUtils.player().sendQueue.addToSendQueue(new C0BPacketEntityAction(ClientUtils.player(), C0BPacketEntityAction.Action.START_SNEAKING));
            }
        }
    }

    public void onDisable() {
        ClientUtils.player().sendQueue.addToSendQueue(new C0BPacketEntityAction(ClientUtils.player(), C0BPacketEntityAction.Action.STOP_SNEAKING));
    }
}

