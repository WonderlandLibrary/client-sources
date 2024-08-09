/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableList;
import java.util.Optional;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.World;

public class RespawnAnchorBlock
extends Block {
    public static final IntegerProperty CHARGES = BlockStateProperties.CHARGES;
    private static final ImmutableList<Vector3i> field_242676_b = ImmutableList.of(new Vector3i(0, 0, -1), new Vector3i(-1, 0, 0), new Vector3i(0, 0, 1), new Vector3i(1, 0, 0), new Vector3i(-1, 0, -1), new Vector3i(1, 0, -1), new Vector3i(-1, 0, 1), new Vector3i(1, 0, 1));
    private static final ImmutableList<Vector3i> field_242677_c = ((ImmutableList.Builder)((ImmutableList.Builder)((ImmutableList.Builder)((ImmutableList.Builder)new ImmutableList.Builder().addAll(field_242676_b)).addAll(field_242676_b.stream().map(Vector3i::down).iterator())).addAll(field_242676_b.stream().map(Vector3i::up).iterator())).add(new Vector3i(0, 1, 0))).build();

    public RespawnAnchorBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(CHARGES, 0));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        ServerPlayerEntity serverPlayerEntity;
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (hand == Hand.MAIN_HAND && !RespawnAnchorBlock.isValidFuel(itemStack) && RespawnAnchorBlock.isValidFuel(playerEntity.getHeldItem(Hand.OFF_HAND))) {
            return ActionResultType.PASS;
        }
        if (RespawnAnchorBlock.isValidFuel(itemStack) && RespawnAnchorBlock.notFullyCharged(blockState)) {
            RespawnAnchorBlock.chargeAnchor(world, blockPos, blockState);
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        if (blockState.get(CHARGES) == 0) {
            return ActionResultType.PASS;
        }
        if (!RespawnAnchorBlock.doesRespawnAnchorWork(world)) {
            if (!world.isRemote) {
                this.triggerExplosion(blockState, world, blockPos);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        if (!(world.isRemote || (serverPlayerEntity = (ServerPlayerEntity)playerEntity).func_241141_L_() == world.getDimensionKey() && serverPlayerEntity.func_241140_K_().equals(blockPos))) {
            serverPlayerEntity.func_242111_a(world.getDimensionKey(), blockPos, 0.0f, false, false);
            world.playSound(null, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.CONSUME;
    }

    private static boolean isValidFuel(ItemStack itemStack) {
        return itemStack.getItem() == Items.GLOWSTONE;
    }

    private static boolean notFullyCharged(BlockState blockState) {
        return blockState.get(CHARGES) < 4;
    }

    private static boolean isNearWater(BlockPos blockPos, World world) {
        FluidState fluidState = world.getFluidState(blockPos);
        if (!fluidState.isTagged(FluidTags.WATER)) {
            return true;
        }
        if (fluidState.isSource()) {
            return false;
        }
        float f = fluidState.getLevel();
        if (f < 2.0f) {
            return true;
        }
        FluidState fluidState2 = world.getFluidState(blockPos.down());
        return !fluidState2.isTagged(FluidTags.WATER);
    }

    private void triggerExplosion(BlockState blockState, World world, BlockPos blockPos) {
        world.removeBlock(blockPos, true);
        boolean bl = Direction.Plane.HORIZONTAL.getDirectionValues().map(blockPos::offset).anyMatch(arg_0 -> RespawnAnchorBlock.lambda$triggerExplosion$0(world, arg_0));
        boolean bl2 = bl || world.getFluidState(blockPos.up()).isTagged(FluidTags.WATER);
        ExplosionContext explosionContext = new ExplosionContext(this, bl2){
            final boolean val$flag1;
            final RespawnAnchorBlock this$0;
            {
                this.this$0 = respawnAnchorBlock;
                this.val$flag1 = bl;
            }

            @Override
            public Optional<Float> getExplosionResistance(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
                return blockPos.equals(blockPos) && this.val$flag1 ? Optional.of(Float.valueOf(Blocks.WATER.getExplosionResistance())) : super.getExplosionResistance(explosion, iBlockReader, blockPos, blockState, fluidState);
            }
        };
        world.createExplosion(null, DamageSource.func_233546_a_(), explosionContext, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, 5.0f, true, Explosion.Mode.DESTROY);
    }

    public static boolean doesRespawnAnchorWork(World world) {
        return world.getDimensionType().doesRespawnAnchorWorks();
    }

    public static void chargeAnchor(World world, BlockPos blockPos, BlockState blockState) {
        world.setBlockState(blockPos, (BlockState)blockState.with(CHARGES, blockState.get(CHARGES) + 1), 0);
        world.playSound(null, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        if (blockState.get(CHARGES) != 0) {
            if (random2.nextInt(100) == 0) {
                world.playSound(null, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            double d = (double)blockPos.getX() + 0.5 + (0.5 - random2.nextDouble());
            double d2 = (double)blockPos.getY() + 1.0;
            double d3 = (double)blockPos.getZ() + 0.5 + (0.5 - random2.nextDouble());
            double d4 = (double)random2.nextFloat() * 0.04;
            world.addParticle(ParticleTypes.REVERSE_PORTAL, d, d2, d3, 0.0, d4, 0.0);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CHARGES);
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    public static int getChargeScale(BlockState blockState, int n) {
        return MathHelper.floor((float)(blockState.get(CHARGES) - 0) / 4.0f * (float)n);
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return RespawnAnchorBlock.getChargeScale(blockState, 15);
    }

    public static Optional<Vector3d> findRespawnPoint(EntityType<?> entityType, ICollisionReader iCollisionReader, BlockPos blockPos) {
        Optional<Vector3d> optional = RespawnAnchorBlock.func_242678_a(entityType, iCollisionReader, blockPos, true);
        return optional.isPresent() ? optional : RespawnAnchorBlock.func_242678_a(entityType, iCollisionReader, blockPos, false);
    }

    private static Optional<Vector3d> func_242678_a(EntityType<?> entityType, ICollisionReader iCollisionReader, BlockPos blockPos, boolean bl) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Vector3i vector3i : field_242677_c) {
            mutable.setPos(blockPos).func_243531_h(vector3i);
            Vector3d vector3d = TransportationHelper.func_242379_a(entityType, iCollisionReader, mutable, bl);
            if (vector3d == null) continue;
            return Optional.of(vector3d);
        }
        return Optional.empty();
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    private static boolean lambda$triggerExplosion$0(World world, BlockPos blockPos) {
        return RespawnAnchorBlock.isNearWater(blockPos, world);
    }
}

