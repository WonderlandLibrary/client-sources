package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.render.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { BlockModelRenderer.class }, priority = 9999)
public class MixinBlockModelRenderer
{
    public MixinBlockModelRenderer() {
        super();
    }
    
    @Inject(method = { "renderModel" }, at = { @At("HEAD") }, cancellable = true)
    private void renderModel(final IBlockAccess blockAccess, final IBakedModel bakedModel, final IBlockState blockState, final BlockPos blockPos, final BufferBuilder bufferBuilder, final boolean b, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        try {
            if (vlwv.hNxW.qHPF((Class<Xray>)jCBS.class).IuiN() && !Xray.fNgC(blockState.getBlock())) {
                callbackInfoReturnable.setReturnValue(false);
                callbackInfoReturnable.cancel();
            }
        }
        catch (Exception ex) {}
    }
    
    @ModifyArg(method = { "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModelFlat(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z"))
    private boolean renderModelFlat(final boolean b) {
        try {
            if (vlwv.hNxW.qHPF((Class<Xray>)jCBS.class).IuiN()) {
                return false;
            }
        }
        catch (Exception ex) {}
        return b;
    }
    
    @ModifyArg(method = { "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModelSmooth(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z"))
    private boolean renderModelSmooth(final boolean b) {
        try {
            if (vlwv.hNxW.qHPF((Class<Xray>)jCBS.class).IuiN()) {
                return false;
            }
        }
        catch (Exception ex) {}
        return b;
    }
}
