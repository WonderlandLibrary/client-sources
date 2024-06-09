package com.client.glowclient.sponge.mixin;

import net.minecraftforge.registries.*;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin({ Block.class })
public abstract class MixinBlock extends IForgeRegistryEntry.Impl<Block>
{
    public MixinBlock() {
        super();
    }
    
    @Inject(method = { "addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V" }, at = { @At("RETURN") }, cancellable = true)
    public void postAddCollisionBoxToList(final IBlockState blockState, final World world, final BlockPos blockPos, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, @Nullable final Entity entity, final boolean b, final CallbackInfo callbackInfo) {
        HookTranslator.m1(Block.class.cast(this), blockState, world, blockPos, axisAlignedBB, list, entity, b);
    }
    
    @Inject(method = { "shouldSideBeRendered" }, at = { @At("HEAD") }, cancellable = true)
    public void preShouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing, final CallbackInfoReturnable callbackInfoReturnable) {
        if (HookTranslator.v21) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
    
    @Inject(method = { "isFullCube" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsFullCube(final IBlockState blockState, final CallbackInfoReturnable callbackInfoReturnable) {
        if (HookTranslator.v21) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
