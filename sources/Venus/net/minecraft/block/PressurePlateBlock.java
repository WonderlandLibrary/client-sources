/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.List;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class PressurePlateBlock
extends AbstractPressurePlateBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private final Sensitivity sensitivity;

    protected PressurePlateBlock(Sensitivity sensitivity, AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(POWERED, false));
        this.sensitivity = sensitivity;
    }

    @Override
    protected int getRedstoneStrength(BlockState blockState) {
        return blockState.get(POWERED) != false ? 15 : 0;
    }

    @Override
    protected BlockState setRedstoneStrength(BlockState blockState, int n) {
        return (BlockState)blockState.with(POWERED, n > 0);
    }

    @Override
    protected void playClickOnSound(IWorld iWorld, BlockPos blockPos) {
        if (this.material != Material.WOOD && this.material != Material.NETHER_WOOD) {
            iWorld.playSound(null, blockPos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3f, 0.6f);
        } else {
            iWorld.playSound(null, blockPos, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3f, 0.8f);
        }
    }

    @Override
    protected void playClickOffSound(IWorld iWorld, BlockPos blockPos) {
        if (this.material != Material.WOOD && this.material != Material.NETHER_WOOD) {
            iWorld.playSound(null, blockPos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3f, 0.5f);
        } else {
            iWorld.playSound(null, blockPos, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3f, 0.7f);
        }
    }

    @Override
    protected int computeRedstoneStrength(World world, BlockPos blockPos) {
        List<Entity> list;
        AxisAlignedBB axisAlignedBB = PRESSURE_AABB.offset(blockPos);
        switch (1.$SwitchMap$net$minecraft$block$PressurePlateBlock$Sensitivity[this.sensitivity.ordinal()]) {
            case 1: {
                list = world.getEntitiesWithinAABBExcludingEntity(null, axisAlignedBB);
                break;
            }
            case 2: {
                list = world.getEntitiesWithinAABB(LivingEntity.class, axisAlignedBB);
                break;
            }
            default: {
                return 1;
            }
        }
        if (!list.isEmpty()) {
            for (Entity entity2 : list) {
                if (entity2.doesEntityNotTriggerPressurePlate()) continue;
                return 0;
            }
        }
        return 1;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    public static enum Sensitivity {
        EVERYTHING,
        MOBS;

    }
}

