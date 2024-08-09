/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tileentity.EnchantingTableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.INameable;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class EnchantingTableBlock
extends ContainerBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

    protected EnchantingTableBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isTransparent(BlockState blockState) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        super.animateTick(blockState, world, blockPos, random2);
        for (int i = -2; i <= 2; ++i) {
            block1: for (int j = -2; j <= 2; ++j) {
                if (i > -2 && i < 2 && j == -1) {
                    j = 2;
                }
                if (random2.nextInt(16) != 0) continue;
                for (int k = 0; k <= 1; ++k) {
                    BlockPos blockPos2 = blockPos.add(i, k, j);
                    if (!world.getBlockState(blockPos2).isIn(Blocks.BOOKSHELF)) continue;
                    if (!world.isAirBlock(blockPos.add(i / 2, 0, j / 2))) continue block1;
                    world.addParticle(ParticleTypes.ENCHANT, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 2.0, (double)blockPos.getZ() + 0.5, (double)((float)i + random2.nextFloat()) - 0.5, (float)k - random2.nextFloat() - 1.0f, (double)((float)j + random2.nextFloat()) - 0.5);
                }
            }
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new EnchantingTableTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        playerEntity.openContainer(blockState.getContainer(world, blockPos));
        return ActionResultType.CONSUME;
    }

    @Override
    @Nullable
    public INamedContainerProvider getContainer(BlockState blockState, World world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof EnchantingTableTileEntity) {
            ITextComponent iTextComponent = ((INameable)((Object)tileEntity)).getDisplayName();
            return new SimpleNamedContainerProvider((arg_0, arg_1, arg_2) -> EnchantingTableBlock.lambda$getContainer$0(world, blockPos, arg_0, arg_1, arg_2), iTextComponent);
        }
        return null;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity tileEntity;
        if (itemStack.hasDisplayName() && (tileEntity = world.getTileEntity(blockPos)) instanceof EnchantingTableTileEntity) {
            ((EnchantingTableTileEntity)tileEntity).setCustomName(itemStack.getDisplayName());
        }
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    private static Container lambda$getContainer$0(World world, BlockPos blockPos, int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new EnchantmentContainer(n, playerInventory, IWorldPosCallable.of(world, blockPos));
    }
}

