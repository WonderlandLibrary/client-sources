package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import io.netty.channel.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import wwe.*;

@Mixin(value = { NetworkManager.class }, priority = 9999)
public class MixinNetworkManager
{
    public MixinNetworkManager() {
        super();
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    public void channelRead0(final ChannelHandlerContext channelHandlerContext, final Packet<?> packet, final CallbackInfo callbackInfo) {
        PJxY.sAGw(packet);
        final LqJY lqJY = new LqJY(packet);
        hSNE.Seqo(lqJY);
        if (lqJY.CEMd()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    public void IsendPacket(final Packet<?> packet, final CallbackInfo callbackInfo) {
        final uqzS uqzS = new uqzS(packet);
        hSNE.Seqo(uqzS);
        uqzS.MYlK();
        if (uqzS.CEMd()) {
            callbackInfo.cancel();
        }
    }
}
