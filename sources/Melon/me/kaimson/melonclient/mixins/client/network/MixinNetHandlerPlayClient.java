package me.kaimson.melonclient.mixins.client.network;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.features.modules.*;
import me.kaimson.melonclient.features.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bcy.class })
public abstract class MixinNetHandlerPlayClient implements fj
{
    @Shadow
    private ave f;
    
    @Inject(method = { "handleTimeUpdate" }, at = { @At("HEAD") }, cancellable = true)
    private void handleTimeUpdate(final hu packetIn, final CallbackInfo ci) {
        hu packet = packetIn;
        if (ModuleConfig.INSTANCE.isEnabled(TimeChangerModule.INSTANCE)) {
            switch (TimeChangerModule.INSTANCE.time.getInt()) {
                case 1: {
                    packet = new hu(packet.b(), -6000L, true);
                    break;
                }
                case 2: {
                    packet = new hu(packet.b(), -22880L, true);
                    break;
                }
                case 3: {
                    packet = new hu(packet.b(), -18000L, true);
                    break;
                }
            }
            fh.a((ff)packet, (ep)this, (od)this.f);
            this.f.f.a(packetIn.a());
            this.f.f.b(packetIn.b());
            ci.cancel();
        }
    }
}
