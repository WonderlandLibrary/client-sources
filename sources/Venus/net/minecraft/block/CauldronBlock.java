/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class CauldronBlock
extends Block {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;
    private static final VoxelShape INSIDE = CauldronBlock.makeCuboidShape(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(CauldronBlock.makeCuboidShape(0.0, 0.0, 4.0, 16.0, 3.0, 12.0), CauldronBlock.makeCuboidShape(4.0, 0.0, 0.0, 12.0, 3.0, 16.0), CauldronBlock.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), INSIDE), IBooleanFunction.ONLY_FIRST);

    public CauldronBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(LEVEL, 0));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return INSIDE;
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        int n = blockState.get(LEVEL);
        float f = (float)blockPos.getY() + (6.0f + (float)(3 * n)) / 16.0f;
        if (!world.isRemote && entity2.isBurning() && n > 0 && entity2.getPosY() <= (double)f) {
            entity2.extinguish();
            this.setWaterLevel(world, blockPos, blockState, n - 1);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        Object object;
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.isEmpty()) {
            return ActionResultType.PASS;
        }
        int n = blockState.get(LEVEL);
        Item item = itemStack.getItem();
        if (item == Items.WATER_BUCKET) {
            if (n < 3 && !world.isRemote) {
                if (!playerEntity.abilities.isCreativeMode) {
                    playerEntity.setHeldItem(hand, new ItemStack(Items.BUCKET));
                }
                playerEntity.addStat(Stats.FILL_CAULDRON);
                this.setWaterLevel(world, blockPos, blockState, 3);
                world.playSound(null, blockPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        if (item == Items.BUCKET) {
            if (n == 3 && !world.isRemote) {
                if (!playerEntity.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                    if (itemStack.isEmpty()) {
                        playerEntity.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
                    } else if (!playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
                        playerEntity.dropItem(new ItemStack(Items.WATER_BUCKET), true);
                    }
                }
                playerEntity.addStat(Stats.USE_CAULDRON);
                this.setWaterLevel(world, blockPos, blockState, 0);
                world.playSound(null, blockPos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        if (item == Items.GLASS_BOTTLE) {
            if (n > 0 && !world.isRemote) {
                if (!playerEntity.abilities.isCreativeMode) {
                    ItemStack itemStack2 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
                    playerEntity.addStat(Stats.USE_CAULDRON);
                    itemStack.shrink(1);
                    if (itemStack.isEmpty()) {
                        playerEntity.setHeldItem(hand, itemStack2);
                    } else if (!playerEntity.inventory.addItemStackToInventory(itemStack2)) {
                        playerEntity.dropItem(itemStack2, true);
                    } else if (playerEntity instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity)playerEntity).sendContainerToPlayer(playerEntity.container);
                    }
                }
                world.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                this.setWaterLevel(world, blockPos, blockState, n - 1);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        if (item == Items.POTION && PotionUtils.getPotionFromItem(itemStack) == Potions.WATER) {
            if (n < 3 && !world.isRemote) {
                if (!playerEntity.abilities.isCreativeMode) {
                    ItemStack itemStack3 = new ItemStack(Items.GLASS_BOTTLE);
                    playerEntity.addStat(Stats.USE_CAULDRON);
                    playerEntity.setHeldItem(hand, itemStack3);
                    if (playerEntity instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity)playerEntity).sendContainerToPlayer(playerEntity.container);
                    }
                }
                world.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                this.setWaterLevel(world, blockPos, blockState, n + 1);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        if (n > 0 && item instanceof IDyeableArmorItem && (object = (IDyeableArmorItem)((Object)item)).hasColor(itemStack) && !world.isRemote) {
            object.removeColor(itemStack);
            this.setWaterLevel(world, blockPos, blockState, n - 1);
            playerEntity.addStat(Stats.CLEAN_ARMOR);
            return ActionResultType.SUCCESS;
        }
        if (n > 0 && item instanceof BannerItem) {
            if (BannerTileEntity.getPatterns(itemStack) > 0 && !world.isRemote) {
                object = itemStack.copy();
                ((ItemStack)object).setCount(1);
                BannerTileEntity.removeBannerData((ItemStack)object);
                playerEntity.addStat(Stats.CLEAN_BANNER);
                if (!playerEntity.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                    this.setWaterLevel(world, blockPos, blockState, n - 1);
                }
                if (itemStack.isEmpty()) {
                    playerEntity.setHeldItem(hand, (ItemStack)object);
                } else if (!playerEntity.inventory.addItemStackToInventory((ItemStack)object)) {
                    playerEntity.dropItem((ItemStack)object, true);
                } else if (playerEntity instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity)playerEntity).sendContainerToPlayer(playerEntity.container);
                }
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        if (n > 0 && item instanceof BlockItem) {
            object = ((BlockItem)item).getBlock();
            if (object instanceof ShulkerBoxBlock && !world.isRemote()) {
                ItemStack itemStack4 = new ItemStack(Blocks.SHULKER_BOX, 1);
                if (itemStack.hasTag()) {
                    itemStack4.setTag(itemStack.getTag().copy());
                }
                playerEntity.setHeldItem(hand, itemStack4);
                this.setWaterLevel(world, blockPos, blockState, n - 1);
                playerEntity.addStat(Stats.CLEAN_SHULKER_BOX);
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.PASS;
    }

    public void setWaterLevel(World world, BlockPos blockPos, BlockState blockState, int n) {
        world.setBlockState(blockPos, (BlockState)blockState.with(LEVEL, MathHelper.clamp(n, 0, 3)), 1);
        world.updateComparatorOutputLevel(blockPos, this);
    }

    @Override
    public void fillWithRain(World world, BlockPos blockPos) {
        BlockState blockState;
        float f;
        if (world.rand.nextInt(20) == 1 && !((f = world.getBiome(blockPos).getTemperature(blockPos)) < 0.15f) && (blockState = world.getBlockState(blockPos)).get(LEVEL) < 3) {
            world.setBlockState(blockPos, (BlockState)blockState.func_235896_a_(LEVEL), 1);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return blockState.get(LEVEL);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

