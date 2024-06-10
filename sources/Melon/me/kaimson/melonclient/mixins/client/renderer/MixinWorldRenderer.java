package me.kaimson.melonclient.mixins.client.renderer;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.features.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bfd.class })
public class MixinWorldRenderer
{
    @Shadow
    private bmv f;
    @Shadow
    private bmu m;
    @Shadow
    private int g;
    
    @Inject(method = { "nextVertexFormatIndex" }, at = { @At("HEAD") }, cancellable = true)
    private void nextVertexFormatIndex(final CallbackInfo ci) {
        if (SettingsManager.INSTANCE.generalPerformance.getBoolean()) {
            ci.cancel();
            final List<bmv> elements = (List<bmv>)this.m.h();
            do {
                if (++this.g >= elements.size()) {
                    this.g -= elements.size();
                }
                this.f = elements.get(this.g);
            } while (this.f.b() == bmv.b.g);
        }
    }
}
