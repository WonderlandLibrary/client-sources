/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.dispenser;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class BeehiveDispenseBehavior
extends OptionalDispenseBehavior {
    @Override
    protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        ServerWorld serverWorld = iBlockSource.getWorld();
        if (!serverWorld.isRemote()) {
            BlockPos blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING));
            this.setSuccessful(BeehiveDispenseBehavior.shearComb(serverWorld, blockPos) || BeehiveDispenseBehavior.shear(serverWorld, blockPos));
            if (this.isSuccessful() && itemStack.attemptDamageItem(1, serverWorld.getRandom(), null)) {
                itemStack.setCount(0);
            }
        }
        return itemStack;
    }

    private static boolean shearComb(ServerWorld serverWorld, BlockPos blockPos) {
        int n;
        BlockState blockState = serverWorld.getBlockState(blockPos);
        if (blockState.isIn(BlockTags.BEEHIVES) && (n = blockState.get(BeehiveBlock.HONEY_LEVEL).intValue()) >= 5) {
            serverWorld.playSound(null, blockPos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.0f);
            BeehiveBlock.dropHoneyComb(serverWorld, blockPos);
            ((BeehiveBlock)blockState.getBlock()).takeHoney(serverWorld, blockState, blockPos, null, BeehiveTileEntity.State.BEE_RELEASED);
            return false;
        }
        return true;
    }

    private static boolean shear(ServerWorld serverWorld, BlockPos blockPos) {
        for (LivingEntity livingEntity : serverWorld.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockPos), EntityPredicates.NOT_SPECTATING)) {
            IShearable iShearable;
            if (!(livingEntity instanceof IShearable) || !(iShearable = (IShearable)((Object)livingEntity)).isShearable()) continue;
            iShearable.shear(SoundCategory.BLOCKS);
            return false;
        }
        return true;
    }
}

