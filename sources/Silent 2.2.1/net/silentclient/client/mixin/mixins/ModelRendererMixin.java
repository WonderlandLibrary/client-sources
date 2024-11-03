package net.silentclient.client.mixin.mixins;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.silentclient.client.mods.settings.FPSBoostMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelRenderer.class)
public class ModelRendererMixin {
    //#if MC==10809
    @Shadow private boolean compiled;

    private boolean silent$compiledState;

    @Inject(method = "render", at = @At("HEAD"))
    private void silent$resetCompiled(float j, CallbackInfo ci) {
        if (silent$compiledState != FPSBoostMod.basicEnabled()) {
            this.compiled = false;
        }
    }

    @Inject(method = "compileDisplayList", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/Tessellator;getWorldRenderer()Lnet/minecraft/client/renderer/WorldRenderer;"))
    private void silent$beginRendering(CallbackInfo ci) {
        this.silent$compiledState = FPSBoostMod.basicEnabled();
        if (FPSBoostMod.basicEnabled()) {
            Tessellator.getInstance().getWorldRenderer().begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
        }
    }

    @Inject(method = "compileDisplayList", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glEndList()V", remap = false))
    private void silent$draw(CallbackInfo ci) {
        if (FPSBoostMod.basicEnabled()) {
            Tessellator.getInstance().draw();
        }
    }
    //#endif
}
