// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.mixin.mixins;

import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import me.perry.mcdonalds.event.events.PacketEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacketPre(final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.Send event = new PacketEvent.Send(0, packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelReadPre(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.Receive event = new PacketEvent.Receive(0, packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
}
