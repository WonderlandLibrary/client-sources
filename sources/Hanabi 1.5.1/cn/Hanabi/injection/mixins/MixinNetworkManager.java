package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import io.netty.channel.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.darkmagician6.eventapi.types.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.util.concurrent.*;

@Mixin({ NetworkManager.class })
public abstract class MixinNetworkManager implements INetworkManager
{
    @Shadow
    private Channel channel;
    @Shadow
    private Queue outboundPacketsQueue;
    
    @Inject(method = { "channelRead0" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private void packetReceived(final ChannelHandlerContext p_channelRead0_1_, final Packet packet, final CallbackInfo ci) {
        final EventPacket event = new EventPacket(EventType.RECIEVE, packet);
        EventManager.call(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void sendPacket(final Packet packetIn, final CallbackInfo ci) {
        final EventPacket event = new EventPacket(EventType.SEND, packetIn);
        EventManager.call(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Override
    public void sendPacketNoEvent(final Packet a) {
        if (this.channel != null && this.channel.isOpen()) {
            final GenericFutureListener[] a2 = null;
            this.flushOutboundQueue();
            this.dispatchPacket(a, a2);
            return;
        }
        this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(a, (GenericFutureListener<? extends Future<? super Void>>[])null));
    }
    
    @Shadow
    protected abstract void dispatchPacket(final Packet p0, final GenericFutureListener[] p1);
    
    @Shadow
    protected abstract void flushOutboundQueue();
    
    static class InboundHandlerTuplePacketListener
    {
        private final Packet packet;
        private final GenericFutureListener<? extends Future<? super Void>>[] futureListeners;
        
        public InboundHandlerTuplePacketListener(final Packet inPacket, final GenericFutureListener<? extends Future<? super Void>>... inFutureListeners) {
            this.packet = inPacket;
            this.futureListeners = inFutureListeners;
        }
    }
}
