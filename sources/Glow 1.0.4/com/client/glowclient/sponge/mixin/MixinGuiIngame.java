package com.client.glowclient.sponge.mixin;

import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiIngame.class })
public abstract class MixinGuiIngame extends Gui
{
    @Shadow
    private Minecraft field_73839_d;
    
    public MixinGuiIngame() {
        super();
    }
    
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    public void prerenderPotionEffects(final ScaledResolution scaledResolution, final CallbackInfo callbackInfo) {
        if (HookTranslator.v9) {
            callbackInfo.cancel();
        }
    }
}
