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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class StructureBlock
extends ContainerBlock {
    public static final EnumProperty<StructureMode> MODE = BlockStateProperties.STRUCTURE_BLOCK_MODE;

    protected StructureBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new StructureBlockTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof StructureBlockTileEntity) {
            return ((StructureBlockTileEntity)tileEntity).usedBy(playerEntity) ? ActionResultType.func_233537_a_(world.isRemote) : ActionResultType.PASS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity tileEntity;
        if (!world.isRemote && livingEntity != null && (tileEntity = world.getTileEntity(blockPos)) instanceof StructureBlockTileEntity) {
            ((StructureBlockTileEntity)tileEntity).createdBy(livingEntity);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(MODE, StructureMode.DATA);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MODE);
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        TileEntity tileEntity;
        if (world instanceof ServerWorld && (tileEntity = world.getTileEntity(blockPos)) instanceof StructureBlockTileEntity) {
            StructureBlockTileEntity structureBlockTileEntity = (StructureBlockTileEntity)tileEntity;
            boolean bl2 = world.isBlockPowered(blockPos);
            boolean bl3 = structureBlockTileEntity.isPowered();
            if (bl2 && !bl3) {
                structureBlockTileEntity.setPowered(false);
                this.execute((ServerWorld)world, structureBlockTileEntity);
            } else if (!bl2 && bl3) {
                structureBlockTileEntity.setPowered(true);
            }
        }
    }

    private void execute(ServerWorld serverWorld, StructureBlockTileEntity structureBlockTileEntity) {
        switch (1.$SwitchMap$net$minecraft$state$properties$StructureMode[structureBlockTileEntity.getMode().ordinal()]) {
            case 1: {
                structureBlockTileEntity.save(true);
                break;
            }
            case 2: {
                structureBlockTileEntity.func_242688_a(serverWorld, true);
                break;
            }
            case 3: {
                structureBlockTileEntity.unloadStructure();
            }
        }
    }
}

