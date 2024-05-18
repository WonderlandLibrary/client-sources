package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.render.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { BlockFluidRenderer.class }, priority = 9999)
public class MixinBlockFluidRenderer
{
    public MixinBlockFluidRenderer() {
        super();
    }
    
    @Inject(method = { "renderFluid" }, at = { @At("HEAD") }, cancellable = true)
    public void renderFluid(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final BufferBuilder bufferBuilder, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (vlwv.hNxW.qHPF((Class<Xray>)jCBS.class).IuiN() && !Xray.fNgC(blockState.getBlock())) {
            callbackInfoReturnable.setReturnValue(false);
            callbackInfoReturnable.cancel();
        }
    }
}
