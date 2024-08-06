package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.util.PacketUtil;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(
        at = @At("HEAD"),
        method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V",
        cancellable = true
    )
    public void onChannelRead_PRE(
        ChannelHandlerContext channelHandlerContext,
        Packet<?> packet,
        CallbackInfo ci
    ) {
        if (Bus.post(new PacketEvent.Receive.Pre(packet))) ci.cancel();
    }

    @Inject(
        at = @At("TAIL"),
        method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V"
    )
    public void onChannelRead_POST(
        ChannelHandlerContext channelHandlerContext,
        Packet<?> packet,
        CallbackInfo ci
    ) {
        Bus.post(new PacketEvent.Receive.Post(packet));
    }

    @Inject(at = @At("HEAD"), method = "sendImmediately", cancellable = true)
    public void onSendImmediately_PRE(
        Packet<?> packet,
        @Nullable PacketCallbacks callbacks,
        boolean flush,
        CallbackInfo ci
    ) {
        boolean flag = PacketUtil.proccedPackets.contains(
            System.identityHashCode(packet)
        );
        if (Bus.post(new PacketEvent.Send.Pre(packet, flag))) ci.cancel();
    }

    @Inject(at = @At("TAIL"), method = "sendImmediately")
    public void onSendImmediately_POST(
        Packet<?> packet,
        @Nullable PacketCallbacks callbacks,
        boolean flush,
        CallbackInfo ci
    ) {
        boolean flag = PacketUtil.proccedPackets.contains(
            System.identityHashCode(packet)
        );
        Bus.post(new PacketEvent.Send.Post(packet, flag));
    }
}
