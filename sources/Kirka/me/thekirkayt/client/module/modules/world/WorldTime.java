/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.world;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketReceiveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

@Module.Mod(displayName="WorldTime")
public class WorldTime
extends Module {
    @Option.Op(min=0.0, max=24000.0, increment=250.0)
    private double time = 9000.0;

    @EventTarget
    private void onPacketRecieve(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.POST) {
            ClientUtils.world().setWorldTime((long)this.time);
        }
    }
}

