package com.client.glowclient.sponge.mixin;

import io.netty.channel.*;
import io.netty.util.concurrent.*;
import java.util.concurrent.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ NetworkManager.class })
public abstract class MixinNetworkManager extends SimpleChannelInboundHandler<Packet<?>>
{
    private boolean sendPackets;
    
    public MixinNetworkManager() {
        super();
        this.sendPackets = true;
    }
    
    @Shadow
    protected abstract void dispatchPacket(final Packet<?> p0, @Nullable final GenericFutureListener<? extends Future<? super Void>>[] p1);
    
    @Redirect(method = { "channelRead0" }, at = @At(value = "INVOKE", target = "net/minecraft/network/Packet.processPacket(Lnet/minecraft/network/INetHandler;)V"))
    private void channelRead0$processPacket(final Packet<?> packet, final INetHandler netHandler) {
        HookTranslator.m41(packet, netHandler);
    }
    
    @Redirect(method = { "sendPacket" }, at = @At(value = "INVOKE", target = "net/minecraft/network/NetworkManager.dispatchPacket(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V"))
    private void sendPacket$dispatchPacket(final NetworkManager networkManager, final Packet<?> packet, @Nullable final GenericFutureListener<? extends io.netty.util.concurrent.Future<? super Void>>[] array) {
        HookTranslator.m42(NetworkManager.class.cast(this), packet, array);
    }
    
    @Redirect(method = { "sendPacket" }, at = @At(value = "INVOKE", target = "net/minecraft/network/NetworkManager.isChannelOpen()Z"))
    private boolean sendPacket$isChannelOpen(final NetworkManager networkManager) {
        return this.sendPackets && networkManager.isChannelOpen();
    }
    
    @Inject(method = { "flushOutboundQueue" }, at = { @At("HEAD") }, cancellable = true)
    private void flushOutboundQueue(final CallbackInfo callbackInfo) {
        if (!this.sendPackets) {
            callbackInfo.cancel();
        }
    }
}
