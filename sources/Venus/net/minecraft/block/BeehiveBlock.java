/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BeehiveBlock
extends ContainerBlock {
    private static final Direction[] GENERATE_DIRECTIONS = new Direction[]{Direction.WEST, Direction.EAST, Direction.SOUTH};
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final IntegerProperty HONEY_LEVEL = BlockStateProperties.HONEY_LEVEL;

    public BeehiveBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HONEY_LEVEL, 0)).with(FACING, Direction.NORTH));
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return blockState.get(HONEY_LEVEL);
    }

    @Override
    public void harvestBlock(World world, PlayerEntity playerEntity, BlockPos blockPos, BlockState blockState, @Nullable TileEntity tileEntity, ItemStack itemStack) {
        super.harvestBlock(world, playerEntity, blockPos, blockState, tileEntity, itemStack);
        if (!world.isRemote && tileEntity instanceof BeehiveTileEntity) {
            BeehiveTileEntity beehiveTileEntity = (BeehiveTileEntity)tileEntity;
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) == 0) {
                beehiveTileEntity.angerBees(playerEntity, blockState, BeehiveTileEntity.State.EMERGENCY);
                world.updateComparatorOutputLevel(blockPos, this);
                this.angerNearbyBees(world, blockPos);
            }
            CriteriaTriggers.BEE_NEST_DESTROYED.test((ServerPlayerEntity)playerEntity, blockState.getBlock(), itemStack, beehiveTileEntity.getBeeCount());
        }
    }

    private void angerNearbyBees(World world, BlockPos blockPos) {
        List<BeeEntity> list = world.getEntitiesWithinAABB(BeeEntity.class, new AxisAlignedBB(blockPos).grow(8.0, 6.0, 8.0));
        if (!list.isEmpty()) {
            List<PlayerEntity> list2 = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(blockPos).grow(8.0, 6.0, 8.0));
            int n = list2.size();
            for (BeeEntity beeEntity : list) {
                if (beeEntity.getAttackTarget() != null) continue;
                beeEntity.setAttackTarget(list2.get(world.rand.nextInt(n)));
            }
        }
    }

    public static void dropHoneyComb(World world, BlockPos blockPos) {
        BeehiveBlock.spawnAsEntity(world, blockPos, new ItemStack(Items.HONEYCOMB, 3));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        int n = blockState.get(HONEY_LEVEL);
        boolean bl = false;
        if (n >= 5) {
            if (itemStack.getItem() == Items.SHEARS) {
                world.playSound(playerEntity, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                BeehiveBlock.dropHoneyComb(world, blockPos);
                itemStack.damageItem(1, playerEntity, arg_0 -> BeehiveBlock.lambda$onBlockActivated$0(hand, arg_0));
                bl = true;
            } else if (itemStack.getItem() == Items.GLASS_BOTTLE) {
                itemStack.shrink(1);
                world.playSound(playerEntity, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if (itemStack.isEmpty()) {
                    playerEntity.setHeldItem(hand, new ItemStack(Items.HONEY_BOTTLE));
                } else if (!playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.HONEY_BOTTLE))) {
                    playerEntity.dropItem(new ItemStack(Items.HONEY_BOTTLE), true);
                }
                bl = true;
            }
        }
        if (bl) {
            if (!CampfireBlock.isSmokingBlockAt(world, blockPos)) {
                if (this.hasBees(world, blockPos)) {
                    this.angerNearbyBees(world, blockPos);
                }
                this.takeHoney(world, blockState, blockPos, playerEntity, BeehiveTileEntity.State.EMERGENCY);
            } else {
                this.takeHoney(world, blockState, blockPos);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return super.onBlockActivated(blockState, world, blockPos, playerEntity, hand, blockRayTraceResult);
    }

    private boolean hasBees(World world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof BeehiveTileEntity) {
            BeehiveTileEntity beehiveTileEntity = (BeehiveTileEntity)tileEntity;
            return !beehiveTileEntity.hasNoBees();
        }
        return true;
    }

    public void takeHoney(World world, BlockState blockState, BlockPos blockPos, @Nullable PlayerEntity playerEntity, BeehiveTileEntity.State state) {
        this.takeHoney(world, blockState, blockPos);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof BeehiveTileEntity) {
            BeehiveTileEntity beehiveTileEntity = (BeehiveTileEntity)tileEntity;
            beehiveTileEntity.angerBees(playerEntity, blockState, state);
        }
    }

    public void takeHoney(World world, BlockState blockState, BlockPos blockPos) {
        world.setBlockState(blockPos, (BlockState)blockState.with(HONEY_LEVEL, 0), 0);
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        if (blockState.get(HONEY_LEVEL) >= 5) {
            for (int i = 0; i < random2.nextInt(1) + 1; ++i) {
                this.addHoneyParticle(world, blockPos, blockState);
            }
        }
    }

    private void addHoneyParticle(World world, BlockPos blockPos, BlockState blockState) {
        VoxelShape voxelShape;
        double d;
        if (blockState.getFluidState().isEmpty() && !(world.rand.nextFloat() < 0.3f) && (d = (voxelShape = blockState.getCollisionShape(world, blockPos)).getEnd(Direction.Axis.Y)) >= 1.0 && !blockState.isIn(BlockTags.IMPERMEABLE)) {
            double d2 = voxelShape.getStart(Direction.Axis.Y);
            if (d2 > 0.0) {
                this.addHoneyParticle(world, blockPos, voxelShape, (double)blockPos.getY() + d2 - 0.05);
            } else {
                BlockPos blockPos2 = blockPos.down();
                BlockState blockState2 = world.getBlockState(blockPos2);
                VoxelShape voxelShape2 = blockState2.getCollisionShape(world, blockPos2);
                double d3 = voxelShape2.getEnd(Direction.Axis.Y);
                if ((d3 < 1.0 || !blockState2.hasOpaqueCollisionShape(world, blockPos2)) && blockState2.getFluidState().isEmpty()) {
                    this.addHoneyParticle(world, blockPos, voxelShape, (double)blockPos.getY() - 0.05);
                }
            }
        }
    }

    private void addHoneyParticle(World world, BlockPos blockPos, VoxelShape voxelShape, double d) {
        this.addHoneyParticle(world, (double)blockPos.getX() + voxelShape.getStart(Direction.Axis.X), (double)blockPos.getX() + voxelShape.getEnd(Direction.Axis.X), (double)blockPos.getZ() + voxelShape.getStart(Direction.Axis.Z), (double)blockPos.getZ() + voxelShape.getEnd(Direction.Axis.Z), d);
    }

    private void addHoneyParticle(World world, double d, double d2, double d3, double d4, double d5) {
        world.addParticle(ParticleTypes.DRIPPING_HONEY, MathHelper.lerp(world.rand.nextDouble(), d, d2), d5, MathHelper.lerp(world.rand.nextDouble(), d3, d4), 0.0, 0.0, 0.0);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HONEY_LEVEL, FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new BeehiveTileEntity();
    }

    @Override
    public void onBlockHarvested(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        TileEntity tileEntity;
        if (!world.isRemote && playerEntity.isCreative() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && (tileEntity = world.getTileEntity(blockPos)) instanceof BeehiveTileEntity) {
            CompoundNBT compoundNBT;
            boolean bl;
            BeehiveTileEntity beehiveTileEntity = (BeehiveTileEntity)tileEntity;
            ItemStack itemStack = new ItemStack(this);
            int n = blockState.get(HONEY_LEVEL);
            boolean bl2 = bl = !beehiveTileEntity.hasNoBees();
            if (!bl && n == 0) {
                return;
            }
            if (bl) {
                compoundNBT = new CompoundNBT();
                compoundNBT.put("Bees", beehiveTileEntity.getBees());
                itemStack.setTagInfo("BlockEntityTag", compoundNBT);
            }
            compoundNBT = new CompoundNBT();
            compoundNBT.putInt("honey_level", n);
            itemStack.setTagInfo("BlockStateTag", compoundNBT);
            ItemEntity itemEntity = new ItemEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);
            itemEntity.setDefaultPickupDelay();
            world.addEntity(itemEntity);
        }
        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        TileEntity tileEntity;
        Entity entity2 = builder.get(LootParameters.THIS_ENTITY);
        if ((entity2 instanceof TNTEntity || entity2 instanceof CreeperEntity || entity2 instanceof WitherSkullEntity || entity2 instanceof WitherEntity || entity2 instanceof TNTMinecartEntity) && (tileEntity = builder.get(LootParameters.BLOCK_ENTITY)) instanceof BeehiveTileEntity) {
            BeehiveTileEntity beehiveTileEntity = (BeehiveTileEntity)tileEntity;
            beehiveTileEntity.angerBees(null, blockState, BeehiveTileEntity.State.EMERGENCY);
        }
        return super.getDrops(blockState, builder);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        TileEntity tileEntity;
        if (iWorld.getBlockState(blockPos2).getBlock() instanceof FireBlock && (tileEntity = iWorld.getTileEntity(blockPos)) instanceof BeehiveTileEntity) {
            BeehiveTileEntity beehiveTileEntity = (BeehiveTileEntity)tileEntity;
            beehiveTileEntity.angerBees(null, blockState, BeehiveTileEntity.State.EMERGENCY);
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    public static Direction getGenerationDirection(Random random2) {
        return Util.getRandomObject(GENERATE_DIRECTIONS, random2);
    }

    private static void lambda$onBlockActivated$0(Hand hand, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(hand);
    }
}

