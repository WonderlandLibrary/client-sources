package dev.stephen.nexus.mixin.game;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.impl.network.EventDisconnect;
import dev.stephen.nexus.event.impl.network.EventPacket;
import dev.stephen.nexus.event.types.TransferOrder;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.OffThreadException;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class MixinClientConnection {

    @Shadow
    protected static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener) {
    }


    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacketEvent(Packet<?> packet, final CallbackInfo callbackInfo) {
        final EventPacket event = new EventPacket(packet, TransferOrder.SEND);
        Client.INSTANCE.getEventManager().post(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true, require = 1)
    private static void receivePacketEvent(Packet<?> packet, PacketListener listener, CallbackInfo ci) {
        if (packet instanceof BundleS2CPacket bundleS2CPacket) {
            ci.cancel();
            for (Packet<?> packetInBundle : bundleS2CPacket.getPackets()) {
                try {
                    handlePacket(packetInBundle, listener);
                } catch (OffThreadException ignored) {
                }
            }
            return;
        }

        final EventPacket event = new EventPacket(packet, TransferOrder.RECEIVE);
        Client.INSTANCE.getEventManager().post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "handleDisconnection", at = @At("HEAD"))
    private void handleDisconnectionInject(CallbackInfo ci) {
        Client.INSTANCE.getEventManager().post(new EventDisconnect());
    }
}