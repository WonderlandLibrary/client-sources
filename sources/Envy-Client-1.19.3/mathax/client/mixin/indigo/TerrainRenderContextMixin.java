package mathax.client.mixin.indigo;

import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TerrainRenderContext.class, remap = false)
public class TerrainRenderContextMixin {
    /*@Inject(method = "tesselateBlock", at = @At("HEAD"), cancellable = true)
    private void onTesselateBlock(BlockState blockState, BlockPos blockPos, BakedModel model, MatrixStack matrixStack, CallbackInfoReturnable<Boolean> info) {
        Xray xray = Modules.get().get(Xray.class);

        if (xray.isActive() && xray.isBlocked(blockState.getBlock(), blockPos)) info.cancel();
    }*/
}
