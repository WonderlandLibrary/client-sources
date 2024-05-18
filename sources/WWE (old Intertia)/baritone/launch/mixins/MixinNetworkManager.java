package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import io.netty.util.concurrent.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import baritone.api.*;
import baritone.api.event.events.type.*;
import baritone.api.event.events.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;

@Mixin({ gw.class })
public class MixinNetworkManager
{
    @Shadow
    private Channel k;
    @Shadow
    @Final
    private hu h;
    
    public MixinNetworkManager() {
        super();
    }
    
    @Inject(method = { "dispatchPacket" }, at = { @At("HEAD") })
    private void preDispatchPacket(final ht<?> ht, final GenericFutureListener<? extends Future<? super Void>>[] array, final CallbackInfo callbackInfo) {
        if (this.h != hu.b) {
            return;
        }
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player() != null && baritone.getPlayerContext().player().d.a() == (gw)this) {
                baritone.getGameEventHandler().onSendPacket(new PacketEvent((gw)this, EventState.PRE, ht));
            }
        }
    }
    
    @Inject(method = { "dispatchPacket" }, at = { @At("RETURN") })
    private void postDispatchPacket(final ht<?> ht, final GenericFutureListener<? extends Future<? super Void>>[] array, final CallbackInfo callbackInfo) {
        if (this.h != hu.b) {
            return;
        }
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player() != null && baritone.getPlayerContext().player().d.a() == (gw)this) {
                baritone.getGameEventHandler().onSendPacket(new PacketEvent((gw)this, EventState.POST, ht));
            }
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At(value = "INVOKE", target = "net/minecraft/network/Packet.processPacket(Lnet/minecraft/network/INetHandler;)V") })
    private void preProcessPacket(final ChannelHandlerContext channelHandlerContext, final ht<?> ht, final CallbackInfo callbackInfo) {
        if (this.h != hu.b) {
            return;
        }
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player() != null && baritone.getPlayerContext().player().d.a() == (gw)this) {
                baritone.getGameEventHandler().onReceivePacket(new PacketEvent((gw)this, EventState.PRE, ht));
            }
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("RETURN") })
    private void postProcessPacket(final ChannelHandlerContext channelHandlerContext, final ht<?> ht, final CallbackInfo callbackInfo) {
        if (!this.k.isOpen() || this.h != hu.b) {
            return;
        }
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player() != null && baritone.getPlayerContext().player().d.a() == (gw)this) {
                baritone.getGameEventHandler().onReceivePacket(new PacketEvent((gw)this, EventState.POST, ht));
            }
        }
    }
}
