package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.BreakIndicators;
import xyz.mathax.mathaxclient.systems.modules.render.NoRender;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderManager.class)
public class BlockRenderManagerMixin {
    @Inject(method = "renderDamage", at = @At("HEAD"), cancellable = true)
    private void renderDamage(BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrixStack, VertexConsumer vertexConsumer, CallbackInfo info) {
        if (Modules.get().isEnabled(BreakIndicators.class) || Modules.get().get(NoRender.class).noBlockBreakOverlay()) {
            info.cancel();
        }
    }
}
