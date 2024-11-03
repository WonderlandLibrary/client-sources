package net.silentclient.client.mixin.mixins;

import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.silentclient.client.mixin.accessors.WorldRendererAccessor;
import net.silentclient.client.mods.settings.FPSBoostMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TexturedQuad.class)
public class TexturedQuadMixin {
    //#if MC==10809
    @Unique
    private boolean silent$drawOnSelf;

    @Redirect(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V"))
    private void silent$beginDraw(WorldRenderer renderer, int glMode, VertexFormat format) {
        this.silent$drawOnSelf = !((WorldRendererAccessor) renderer).isDrawing();
        if (this.silent$drawOnSelf || !FPSBoostMod.basicEnabled()) {
            renderer.begin(glMode, DefaultVertexFormats.POSITION_TEX_NORMAL);
        }
    }

    @Redirect(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V"))
    private void silent$endDraw(Tessellator tessellator) {
        if (this.silent$drawOnSelf || !FPSBoostMod.basicEnabled()) {
            tessellator.draw();
        }
    }
    //#endif
}
