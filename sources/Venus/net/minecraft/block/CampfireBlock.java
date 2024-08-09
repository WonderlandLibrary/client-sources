/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class CampfireBlock
extends ContainerBlock
implements IWaterLoggable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty SIGNAL_FIRE = BlockStateProperties.SIGNAL_FIRE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SMOKING_SHAPE = Block.makeCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    private final boolean smokey;
    private final int fireDamage;

    public CampfireBlock(boolean bl, int n, AbstractBlock.Properties properties) {
        super(properties);
        this.smokey = bl;
        this.fireDamage = n;
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(LIT, true)).with(SIGNAL_FIRE, false)).with(WATERLOGGED, false)).with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        ItemStack itemStack;
        CampfireTileEntity campfireTileEntity;
        Optional<CampfireCookingRecipe> optional;
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof CampfireTileEntity && (optional = (campfireTileEntity = (CampfireTileEntity)tileEntity).findMatchingRecipe(itemStack = playerEntity.getHeldItem(hand))).isPresent()) {
            if (!world.isRemote && campfireTileEntity.addItem(playerEntity.abilities.isCreativeMode ? itemStack.copy() : itemStack, optional.get().getCookTime())) {
                playerEntity.addStat(Stats.INTERACT_WITH_CAMPFIRE);
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        if (!entity2.isImmuneToFire() && blockState.get(LIT).booleanValue() && entity2 instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity2)) {
            entity2.attackEntityFrom(DamageSource.IN_FIRE, this.fireDamage);
        }
        super.onEntityCollision(blockState, world, blockPos, entity2);
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock())) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof CampfireTileEntity) {
                InventoryHelper.dropItems(world, blockPos, ((CampfireTileEntity)tileEntity).getInventory());
            }
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockPos blockPos;
        World world = blockItemUseContext.getWorld();
        boolean bl = world.getFluidState(blockPos = blockItemUseContext.getPos()).getFluid() == Fluids.WATER;
        return (BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(WATERLOGGED, bl)).with(SIGNAL_FIRE, this.isHayBlock(world.getBlockState(blockPos.down())))).with(LIT, !bl)).with(FACING, blockItemUseContext.getPlacementHorizontalFacing());
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        return direction == Direction.DOWN ? (BlockState)blockState.with(SIGNAL_FIRE, this.isHayBlock(blockState2)) : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    private boolean isHayBlock(BlockState blockState) {
        return blockState.isIn(Blocks.HAY_BLOCK);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        if (blockState.get(LIT).booleanValue()) {
            if (random2.nextInt(10) == 0) {
                world.playSound((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5f + random2.nextFloat(), random2.nextFloat() * 0.7f + 0.6f, true);
            }
            if (this.smokey && random2.nextInt(5) == 0) {
                for (int i = 0; i < random2.nextInt(1) + 1; ++i) {
                    world.addParticle(ParticleTypes.LAVA, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, random2.nextFloat() / 2.0f, 5.0E-5, random2.nextFloat() / 2.0f);
                }
            }
        }
    }

    public static void extinguish(IWorld iWorld, BlockPos blockPos, BlockState blockState) {
        TileEntity tileEntity;
        if (iWorld.isRemote()) {
            for (int i = 0; i < 20; ++i) {
                CampfireBlock.spawnSmokeParticles((World)iWorld, blockPos, blockState.get(SIGNAL_FIRE), true);
            }
        }
        if ((tileEntity = iWorld.getTileEntity(blockPos)) instanceof CampfireTileEntity) {
            ((CampfireTileEntity)tileEntity).dropAllItems();
        }
    }

    @Override
    public boolean receiveFluid(IWorld iWorld, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        if (!blockState.get(BlockStateProperties.WATERLOGGED).booleanValue() && fluidState.getFluid() == Fluids.WATER) {
            boolean bl = blockState.get(LIT);
            if (bl) {
                if (!iWorld.isRemote()) {
                    iWorld.playSound(null, blockPos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                }
                CampfireBlock.extinguish(iWorld, blockPos, blockState);
            }
            iWorld.setBlockState(blockPos, (BlockState)((BlockState)blockState.with(WATERLOGGED, true)).with(LIT, false), 3);
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, fluidState.getFluid(), fluidState.getFluid().getTickRate(iWorld));
            return false;
        }
        return true;
    }

    @Override
    public void onProjectileCollision(World world, BlockState blockState, BlockRayTraceResult blockRayTraceResult, ProjectileEntity projectileEntity) {
        if (!world.isRemote && projectileEntity.isBurning()) {
            boolean bl;
            Entity entity2 = projectileEntity.func_234616_v_();
            boolean bl2 = bl = entity2 == null || entity2 instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.MOB_GRIEFING);
            if (bl && !blockState.get(LIT).booleanValue() && !blockState.get(WATERLOGGED).booleanValue()) {
                BlockPos blockPos = blockRayTraceResult.getPos();
                world.setBlockState(blockPos, (BlockState)blockState.with(BlockStateProperties.LIT, true), 0);
            }
        }
    }

    public static void spawnSmokeParticles(World world, BlockPos blockPos, boolean bl, boolean bl2) {
        Random random2 = world.getRandom();
        BasicParticleType basicParticleType = bl ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        world.addOptionalParticle(basicParticleType, true, (double)blockPos.getX() + 0.5 + random2.nextDouble() / 3.0 * (double)(random2.nextBoolean() ? 1 : -1), (double)blockPos.getY() + random2.nextDouble() + random2.nextDouble(), (double)blockPos.getZ() + 0.5 + random2.nextDouble() / 3.0 * (double)(random2.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
        if (bl2) {
            world.addParticle(ParticleTypes.SMOKE, (double)blockPos.getX() + 0.25 + random2.nextDouble() / 2.0 * (double)(random2.nextBoolean() ? 1 : -1), (double)blockPos.getY() + 0.4, (double)blockPos.getZ() + 0.25 + random2.nextDouble() / 2.0 * (double)(random2.nextBoolean() ? 1 : -1), 0.0, 0.005, 0.0);
        }
    }

    public static boolean isSmokingBlockAt(World world, BlockPos blockPos) {
        for (int i = 1; i <= 5; ++i) {
            BlockPos blockPos2 = blockPos.down(i);
            BlockState blockState = world.getBlockState(blockPos2);
            if (CampfireBlock.isLit(blockState)) {
                return false;
            }
            boolean bl = VoxelShapes.compare(SMOKING_SHAPE, blockState.getCollisionShape(world, blockPos, ISelectionContext.dummy()), IBooleanFunction.AND);
            if (!bl) continue;
            BlockState blockState2 = world.getBlockState(blockPos2.down());
            return CampfireBlock.isLit(blockState2);
        }
        return true;
    }

    public static boolean isLit(BlockState blockState) {
        return blockState.hasProperty(LIT) && blockState.isIn(BlockTags.CAMPFIRES) && blockState.get(LIT) != false;
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.toRotation(blockState.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT, SIGNAL_FIRE, WATERLOGGED, FACING);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new CampfireTileEntity();
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    public static boolean canBeLit(BlockState blockState) {
        return blockState.isInAndMatches(BlockTags.CAMPFIRES, CampfireBlock::lambda$canBeLit$0) && blockState.get(BlockStateProperties.WATERLOGGED) == false && blockState.get(BlockStateProperties.LIT) == false;
    }

    private static boolean lambda$canBeLit$0(AbstractBlock.AbstractBlockState abstractBlockState) {
        return abstractBlockState.hasProperty(BlockStateProperties.WATERLOGGED) && abstractBlockState.hasProperty(BlockStateProperties.LIT);
    }
}

