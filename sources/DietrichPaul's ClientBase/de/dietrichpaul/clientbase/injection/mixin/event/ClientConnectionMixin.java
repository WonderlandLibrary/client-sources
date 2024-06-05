package de.dietrichpaul.clientbase.injection.mixin.event;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.network.ReceivePacketListener;
import de.dietrichpaul.clientbase.event.network.SendPacketListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {
    @Shadow
    public static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener) {
    }

    @Inject(
            method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/ClientConnection;isOpen()Z",
                    shift = At.Shift.AFTER
            ), cancellable = true
    )
    public void injectNetworkEvent_write(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
        SendPacketListener.SendPacketEvent event = new SendPacketListener.SendPacketEvent(packet, callbacks);
        ClientBase.INSTANCE.getEventDispatcher().post(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Redirect(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;)V"))
    public void onHandlePacket(Packet<?> packet, PacketListener listener) {
        ReceivePacketListener.ReceivePacketEvent event = new ReceivePacketListener.ReceivePacketEvent(packet, listener);
        ClientBase.INSTANCE.getEventDispatcher().post(event);
        if (event.isCancelled())
            return;
        handlePacket(event.getPacket(), event.getListener());
    }
}
