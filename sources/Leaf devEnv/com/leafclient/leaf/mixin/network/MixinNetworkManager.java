package com.leafclient.leaf.mixin.network;

import com.leafclient.leaf.event.game.network.PacketEvent;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {

    @Shadow
    protected abstract void dispatchPacket(Packet<?> inPacket, @Nullable GenericFutureListener<? extends Future<? super Void>>[] futureListeners);

    @Shadow @Final private EnumPacketDirection direction;

    @SuppressWarnings("unchecked")
    @Redirect(method = "channelRead0",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/network/Packet.processPacket(Lnet/minecraft/network/INetHandler;)V"
            )
    )
    private void channelRead0$processPacket(Packet<?> packetIn, INetHandler handler) {
        if(this.direction == EnumPacketDirection.CLIENTBOUND && Minecraft.getMinecraft().player != null) {
            PacketEvent.Receive event = EventManager.INSTANCE.publish(new PacketEvent.Receive(packetIn));
            if (event.isCancelled())
                return;

            packetIn = event.getPacket();
        }
        ((Packet<INetHandler>) packetIn).processPacket(handler);
    }

    @SuppressWarnings("AmbiguousMixinReference")
    @Redirect(
            method = "sendPacket",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/network/NetworkManager.dispatchPacket(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V"
            )
    )
    private void sendPacket$dispatchPacket(NetworkManager networkManager, Packet<?> packetIn, @Nullable final GenericFutureListener<? extends Future<?super Void>>[] futureListeners) {
        if(this.direction == EnumPacketDirection.CLIENTBOUND && Minecraft.getMinecraft().player != null) {
            PacketEvent.Send event = EventManager.INSTANCE.publish(new PacketEvent.Send(packetIn));
            if (event.isCancelled())
                return;

            packetIn = event.getPacket();
        }
        this.dispatchPacket(packetIn, futureListeners);
    }

}
