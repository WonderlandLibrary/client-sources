/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.JukeboxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class JukeboxBlock
extends ContainerBlock {
    public static final BooleanProperty HAS_RECORD = BlockStateProperties.HAS_RECORD;

    protected JukeboxBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HAS_RECORD, false));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        CompoundNBT compoundNBT;
        super.onBlockPlacedBy(world, blockPos, blockState, livingEntity, itemStack);
        CompoundNBT compoundNBT2 = itemStack.getOrCreateTag();
        if (compoundNBT2.contains("BlockEntityTag") && (compoundNBT = compoundNBT2.getCompound("BlockEntityTag")).contains("RecordItem")) {
            world.setBlockState(blockPos, (BlockState)blockState.with(HAS_RECORD, true), 1);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (blockState.get(HAS_RECORD).booleanValue()) {
            this.dropRecord(world, blockPos);
            blockState = (BlockState)blockState.with(HAS_RECORD, false);
            world.setBlockState(blockPos, blockState, 1);
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.PASS;
    }

    public void insertRecord(IWorld iWorld, BlockPos blockPos, BlockState blockState, ItemStack itemStack) {
        TileEntity tileEntity = iWorld.getTileEntity(blockPos);
        if (tileEntity instanceof JukeboxTileEntity) {
            ((JukeboxTileEntity)tileEntity).setRecord(itemStack.copy());
            iWorld.setBlockState(blockPos, (BlockState)blockState.with(HAS_RECORD, true), 2);
        }
    }

    private void dropRecord(World world, BlockPos blockPos) {
        JukeboxTileEntity jukeboxTileEntity;
        ItemStack itemStack;
        TileEntity tileEntity;
        if (!world.isRemote && (tileEntity = world.getTileEntity(blockPos)) instanceof JukeboxTileEntity && !(itemStack = (jukeboxTileEntity = (JukeboxTileEntity)tileEntity).getRecord()).isEmpty()) {
            world.playEvent(1010, blockPos, 0);
            jukeboxTileEntity.clear();
            float f = 0.7f;
            double d = (double)(world.rand.nextFloat() * 0.7f) + (double)0.15f;
            double d2 = (double)(world.rand.nextFloat() * 0.7f) + 0.06000000238418579 + 0.6;
            double d3 = (double)(world.rand.nextFloat() * 0.7f) + (double)0.15f;
            ItemStack itemStack2 = itemStack.copy();
            ItemEntity itemEntity = new ItemEntity(world, (double)blockPos.getX() + d, (double)blockPos.getY() + d2, (double)blockPos.getZ() + d3, itemStack2);
            itemEntity.setDefaultPickupDelay();
            world.addEntity(itemEntity);
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock())) {
            this.dropRecord(world, blockPos);
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new JukeboxTileEntity();
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        Item item;
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof JukeboxTileEntity && (item = ((JukeboxTileEntity)tileEntity).getRecord().getItem()) instanceof MusicDiscItem) {
            return ((MusicDiscItem)item).getComparatorValue();
        }
        return 1;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HAS_RECORD);
    }
}

