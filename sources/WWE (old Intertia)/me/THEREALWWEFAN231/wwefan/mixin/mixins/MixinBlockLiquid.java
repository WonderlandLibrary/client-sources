package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.block.properties.*;
import wwe.modules.world.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { BlockLiquid.class }, priority = 9999)
public class MixinBlockLiquid
{
    public MixinBlockLiquid() {
        super();
    }
    
    @Inject(method = { "canCollideCheck" }, at = { @At("HEAD") }, cancellable = true)
    public void canCollideCheck(final IBlockState blockState, final boolean b, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue((b && (int)blockState.getValue((IProperty)BlockLiquid.LEVEL) == 0) || vlwv.hNxW.qHPF((Class<LiquidInteract>)sBzS.class).IuiN());
    }
}
