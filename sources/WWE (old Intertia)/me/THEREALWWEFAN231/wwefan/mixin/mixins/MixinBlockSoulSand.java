package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.movement.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { BlockSoulSand.class }, priority = 9999)
public class MixinBlockSoulSand
{
    public MixinBlockSoulSand() {
        super();
    }
    
    @Inject(method = { "onEntityCollision" }, at = { @At("HEAD") }, cancellable = true)
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity, final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<NoSlowDown>)lOiL.class).IuiN()) {
            callbackInfo.cancel();
        }
    }
}
