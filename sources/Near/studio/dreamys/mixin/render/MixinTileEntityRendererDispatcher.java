package studio.dreamys.mixin.render;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.dreamys.entityculling.access.Cullable;
import studio.dreamys.near;

@Mixin(TileEntityRendererDispatcher.class)
public class MixinTileEntityRendererDispatcher {
    @Inject(method = "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntity;DDDFI)V", at = @At("HEAD"), cancellable = true)
    public void renderTileEntityAt(TileEntity blockEntity, double p_renderTileEntityAt_2_, double d1, double d2, float f1, int p_renderTileEntityAt_9_, CallbackInfo info) {
        if (((Cullable) blockEntity).isForcedVisible() && ((Cullable) blockEntity).isCulled()) {
            near.skippedBlockEntities++;
            info.cancel();
            return;
        }
        near.renderedBlockEntities++;
    }
}
