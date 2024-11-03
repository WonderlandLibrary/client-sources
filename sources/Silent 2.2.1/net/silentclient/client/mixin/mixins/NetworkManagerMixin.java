package net.silentclient.client.mixin.mixins;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.event.impl.EventSendPacket;
import net.silentclient.client.event.impl.NewMessageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class NetworkManagerMixin {
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void packetEvent(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_, CallbackInfo ci) {
        EventReceivePacket event = new EventReceivePacket(p_channelRead0_2_);
        event.call();

        if(event.isCancelable()) {
            ci.cancel();
        }

        if(p_channelRead0_2_ instanceof S02PacketChat && ((S02PacketChat) p_channelRead0_2_).getType() == 0) {
            NewMessageEvent event2 = new NewMessageEvent(((S02PacketChat) p_channelRead0_2_).getChatComponent());
            event2.call();

            if(event2.isCancelable()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"))
    public void sendPacket(Packet packetIn, CallbackInfo ci) {
        EventSendPacket event = new EventSendPacket(packetIn);
        event.call();

        if(event.isCancelable()) {
            ci.cancel();
        }
    }
}
