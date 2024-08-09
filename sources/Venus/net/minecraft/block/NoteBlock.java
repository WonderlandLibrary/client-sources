/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class NoteBlock
extends Block {
    public static final EnumProperty<NoteBlockInstrument> INSTRUMENT = BlockStateProperties.NOTE_BLOCK_INSTRUMENT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final IntegerProperty NOTE = BlockStateProperties.NOTE_0_24;

    public NoteBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(INSTRUMENT, NoteBlockInstrument.HARP)).with(NOTE, 0)).with(POWERED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(INSTRUMENT, NoteBlockInstrument.byState(blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos().down())));
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction == Direction.DOWN ? (BlockState)blockState.with(INSTRUMENT, NoteBlockInstrument.byState(blockState2)) : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        boolean bl2 = world.isBlockPowered(blockPos);
        if (bl2 != blockState.get(POWERED)) {
            if (bl2) {
                this.triggerNote(world, blockPos);
            }
            world.setBlockState(blockPos, (BlockState)blockState.with(POWERED, bl2), 0);
        }
    }

    private void triggerNote(World world, BlockPos blockPos) {
        if (world.getBlockState(blockPos.up()).isAir()) {
            world.addBlockEvent(blockPos, this, 0, 0);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        blockState = (BlockState)blockState.func_235896_a_(NOTE);
        world.setBlockState(blockPos, blockState, 0);
        this.triggerNote(world, blockPos);
        playerEntity.addStat(Stats.TUNE_NOTEBLOCK);
        return ActionResultType.CONSUME;
    }

    @Override
    public void onBlockClicked(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        if (!world.isRemote) {
            this.triggerNote(world, blockPos);
            playerEntity.addStat(Stats.PLAY_NOTEBLOCK);
        }
    }

    @Override
    public boolean eventReceived(BlockState blockState, World world, BlockPos blockPos, int n, int n2) {
        int n3 = blockState.get(NOTE);
        float f = (float)Math.pow(2.0, (double)(n3 - 12) / 12.0);
        world.playSound(null, blockPos, blockState.get(INSTRUMENT).getSound(), SoundCategory.RECORDS, 3.0f, f);
        world.addParticle(ParticleTypes.NOTE, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.2, (double)blockPos.getZ() + 0.5, (double)n3 / 24.0, 0.0, 0.0);
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(INSTRUMENT, POWERED, NOTE);
    }
}

