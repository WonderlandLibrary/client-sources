/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.StemGrownBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class PumpkinBlock
extends StemGrownBlock {
    protected PumpkinBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.getItem() == Items.SHEARS) {
            if (!world.isRemote) {
                Direction direction = blockRayTraceResult.getFace();
                Direction direction2 = direction.getAxis() == Direction.Axis.Y ? playerEntity.getHorizontalFacing().getOpposite() : direction;
                world.playSound(null, blockPos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(blockPos, (BlockState)Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, direction2), 0);
                ItemEntity itemEntity = new ItemEntity(world, (double)blockPos.getX() + 0.5 + (double)direction2.getXOffset() * 0.65, (double)blockPos.getY() + 0.1, (double)blockPos.getZ() + 0.5 + (double)direction2.getZOffset() * 0.65, new ItemStack(Items.PUMPKIN_SEEDS, 4));
                itemEntity.setMotion(0.05 * (double)direction2.getXOffset() + world.rand.nextDouble() * 0.02, 0.05, 0.05 * (double)direction2.getZOffset() + world.rand.nextDouble() * 0.02);
                world.addEntity(itemEntity);
                itemStack.damageItem(1, playerEntity, arg_0 -> PumpkinBlock.lambda$onBlockActivated$0(hand, arg_0));
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return super.onBlockActivated(blockState, world, blockPos, playerEntity, hand, blockRayTraceResult);
    }

    @Override
    public StemBlock getStem() {
        return (StemBlock)Blocks.PUMPKIN_STEM;
    }

    @Override
    public AttachedStemBlock getAttachedStem() {
        return (AttachedStemBlock)Blocks.ATTACHED_PUMPKIN_STEM;
    }

    private static void lambda$onBlockActivated$0(Hand hand, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(hand);
    }
}

