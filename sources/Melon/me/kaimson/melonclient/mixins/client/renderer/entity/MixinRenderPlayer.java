package me.kaimson.melonclient.mixins.client.renderer.entity;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.cosmetics.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bln.class })
public abstract class MixinRenderPlayer extends bjl<bet>
{
    public MixinRenderPlayer(final biu renderManagerIn, final bbo modelBaseIn, final float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/client/renderer/entity/RenderManager;Z)V" }, at = { @At("RETURN") })
    private void constructor(final biu renderManager, final boolean useSmallArms, final CallbackInfo ci) {
        this.a((blb)new LayerCape());
    }
}
