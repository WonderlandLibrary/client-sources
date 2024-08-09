package xyz.mathax.mathaxclient.mixin.indigo;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Xray;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TerrainRenderContext.class, remap = false)
public class TerrainRenderContextMixin {
    @Inject(method = "tessellateBlock", at = @At("HEAD"), cancellable = true)
    private void onTessellateBlock(BlockState blockState, BlockPos blockPos, BakedModel model, MatrixStack matrixStack, CallbackInfoReturnable<Boolean> infoReturnable) {
        Xray xray = Modules.get().get(Xray.class);
        if (xray.isEnabled() && xray.isBlocked(blockState.getBlock(), blockPos)) {
            infoReturnable.cancel();
        }
    }
}
