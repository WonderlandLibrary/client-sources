/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BlastFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlastFurnaceBlock
extends AbstractFurnaceBlock {
    protected BlastFurnaceBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new BlastFurnaceTileEntity();
    }

    @Override
    protected void interactWith(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof BlastFurnaceTileEntity) {
            playerEntity.openContainer((INamedContainerProvider)((Object)tileEntity));
            playerEntity.addStat(Stats.INTERACT_WITH_BLAST_FURNACE);
        }
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        if (blockState.get(LIT).booleanValue()) {
            double d = (double)blockPos.getX() + 0.5;
            double d2 = blockPos.getY();
            double d3 = (double)blockPos.getZ() + 0.5;
            if (random2.nextDouble() < 0.1) {
                world.playSound(d, d2, d3, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
            }
            Direction direction = blockState.get(FACING);
            Direction.Axis axis = direction.getAxis();
            double d4 = 0.52;
            double d5 = random2.nextDouble() * 0.6 - 0.3;
            double d6 = axis == Direction.Axis.X ? (double)direction.getXOffset() * 0.52 : d5;
            double d7 = random2.nextDouble() * 9.0 / 16.0;
            double d8 = axis == Direction.Axis.Z ? (double)direction.getZOffset() * 0.52 : d5;
            world.addParticle(ParticleTypes.SMOKE, d + d6, d2 + d7, d3 + d8, 0.0, 0.0, 0.0);
        }
    }
}

