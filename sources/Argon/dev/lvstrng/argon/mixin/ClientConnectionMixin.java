// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.event.EventBus;
import dev.lvstrng.argon.event.events.PacketReceiveEvent;
import dev.lvstrng.argon.event.events.PacketSendEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientConnection.class})
public class ClientConnectionMixin {
    @Inject(method = {"handlePacket"}, at = {@At("HEAD")}, cancellable = true)
    private static void onPacketReceive(final Packet packet, final PacketListener listener, final CallbackInfo ci) {
        final PacketReceiveEvent event = new PacketReceiveEvent(packet);
        EventBus.postEvent(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"send(Lnet/minecraft/network/packet/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void onPacketSend(final Packet packet, final CallbackInfo ci) {
        final PacketSendEvent event = new PacketSendEvent(packet);
        EventBus.postEvent(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
