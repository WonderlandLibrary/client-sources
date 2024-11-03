package net.silentclient.client.mixin.mixins;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.renderer.BlockModelRenderer$AmbientOcclusionFace")
public class BlockModelRendererAmbientOcclusionFaceMixin {
    @Redirect(
            //#if MC==10809
            method = "updateVertexBrightness(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/Block;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;[FLjava/util/BitSet;)V",
            //#else
            //$$ method = "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;[FLjava/util/BitSet;)V",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target =
                            //#if MC==10809
                            "Lnet/minecraft/block/Block;isTranslucent()Z"
                    //#else
                    //$$ "Lnet/minecraft/block/Block;isTranslucent(Lnet/minecraft/block/state/IBlockState;)Z"
                    //#endif
            )
    )
    private boolean silent$betterSmoothLighting(Block block) {
        //#if MC==10809
        return !block.isVisuallyOpaque() || block.getLightOpacity() == 0;
        //#else
        //$$ IBlockState state = block.getDefaultState();
        //$$ return !block.causesSuffocation(state) || block.getLightOpacity(state) == 0;
        //#endif
    }
}
