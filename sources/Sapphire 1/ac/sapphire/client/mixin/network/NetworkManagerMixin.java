package ac.sapphire.client.mixin.network;

import ac.sapphire.client.Sapphire;
import ac.sapphire.client.event.events.network.PacketReceiveEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class NetworkManagerMixin {
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V", shift = At.Shift.BEFORE), cancellable = true)
    void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_, CallbackInfo ci) {
        PacketReceiveEvent event = Sapphire.INSTANCE.getEventBus().post(new PacketReceiveEvent(p_channelRead0_2_));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
