/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.ShulkerAABBHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ShulkerBoxBlock
extends ContainerBlock {
    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");
    @Nullable
    private final DyeColor color;

    public ShulkerBoxBlock(@Nullable DyeColor dyeColor, AbstractBlock.Properties properties) {
        super(properties);
        this.color = dyeColor;
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.UP));
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new ShulkerBoxTileEntity(this.color);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        if (playerEntity.isSpectator()) {
            return ActionResultType.CONSUME;
        }
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof ShulkerBoxTileEntity) {
            boolean bl;
            ShulkerBoxTileEntity shulkerBoxTileEntity = (ShulkerBoxTileEntity)tileEntity;
            if (shulkerBoxTileEntity.getAnimationStatus() == ShulkerBoxTileEntity.AnimationStatus.CLOSED) {
                Direction direction = blockState.get(FACING);
                bl = world.hasNoCollisions(ShulkerAABBHelper.getOpenedCollisionBox(blockPos, direction));
            } else {
                bl = true;
            }
            if (bl) {
                playerEntity.openContainer(shulkerBoxTileEntity);
                playerEntity.addStat(Stats.OPEN_SHULKER_BOX);
                PiglinTasks.func_234478_a_(playerEntity, true);
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.PASS;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getFace());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof ShulkerBoxTileEntity) {
            ShulkerBoxTileEntity shulkerBoxTileEntity = (ShulkerBoxTileEntity)tileEntity;
            if (!world.isRemote && playerEntity.isCreative() && !shulkerBoxTileEntity.isEmpty()) {
                ItemStack itemStack = ShulkerBoxBlock.getColoredItemStack(this.getColor());
                CompoundNBT compoundNBT = shulkerBoxTileEntity.saveToNbt(new CompoundNBT());
                if (!compoundNBT.isEmpty()) {
                    itemStack.setTagInfo("BlockEntityTag", compoundNBT);
                }
                if (shulkerBoxTileEntity.hasCustomName()) {
                    itemStack.setDisplayName(shulkerBoxTileEntity.getCustomName());
                }
                ItemEntity itemEntity = new ItemEntity(world, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, itemStack);
                itemEntity.setDefaultPickupDelay();
                world.addEntity(itemEntity);
            } else {
                shulkerBoxTileEntity.fillWithLoot(playerEntity);
            }
        }
        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        TileEntity tileEntity = builder.get(LootParameters.BLOCK_ENTITY);
        if (tileEntity instanceof ShulkerBoxTileEntity) {
            ShulkerBoxTileEntity shulkerBoxTileEntity = (ShulkerBoxTileEntity)tileEntity;
            builder = builder.withDynamicDrop(CONTENTS, (arg_0, arg_1) -> ShulkerBoxBlock.lambda$getDrops$0(shulkerBoxTileEntity, arg_0, arg_1));
        }
        return super.getDrops(blockState, builder);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity tileEntity;
        if (itemStack.hasDisplayName() && (tileEntity = world.getTileEntity(blockPos)) instanceof ShulkerBoxTileEntity) {
            ((ShulkerBoxTileEntity)tileEntity).setCustomName(itemStack.getDisplayName());
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock())) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof ShulkerBoxTileEntity) {
                world.updateComparatorOutputLevel(blockPos, blockState.getBlock());
            }
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable IBlockReader iBlockReader, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        super.addInformation(itemStack, iBlockReader, list, iTooltipFlag);
        CompoundNBT compoundNBT = itemStack.getChildTag("BlockEntityTag");
        if (compoundNBT != null) {
            if (compoundNBT.contains("LootTable", 1)) {
                list.add(new StringTextComponent("???????"));
            }
            if (compoundNBT.contains("Items", 0)) {
                NonNullList<ItemStack> nonNullList = NonNullList.withSize(27, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(compoundNBT, nonNullList);
                int n = 0;
                int n2 = 0;
                for (ItemStack itemStack2 : nonNullList) {
                    if (itemStack2.isEmpty()) continue;
                    ++n2;
                    if (n > 4) continue;
                    ++n;
                    IFormattableTextComponent iFormattableTextComponent = itemStack2.getDisplayName().deepCopy();
                    iFormattableTextComponent.appendString(" x").appendString(String.valueOf(itemStack2.getCount()));
                    list.add(iFormattableTextComponent);
                }
                if (n2 - n > 0) {
                    list.add(new TranslationTextComponent("container.shulkerBox.more", n2 - n).mergeStyle(TextFormatting.ITALIC));
                }
            }
        }
    }

    @Override
    public PushReaction getPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        TileEntity tileEntity = iBlockReader.getTileEntity(blockPos);
        return tileEntity instanceof ShulkerBoxTileEntity ? VoxelShapes.create(((ShulkerBoxTileEntity)tileEntity).getBoundingBox(blockState)) : VoxelShapes.fullCube();
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return Container.calcRedstoneFromInventory((IInventory)((Object)world.getTileEntity(blockPos)));
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        ItemStack itemStack = super.getItem(iBlockReader, blockPos, blockState);
        ShulkerBoxTileEntity shulkerBoxTileEntity = (ShulkerBoxTileEntity)iBlockReader.getTileEntity(blockPos);
        CompoundNBT compoundNBT = shulkerBoxTileEntity.saveToNbt(new CompoundNBT());
        if (!compoundNBT.isEmpty()) {
            itemStack.setTagInfo("BlockEntityTag", compoundNBT);
        }
        return itemStack;
    }

    @Nullable
    public static DyeColor getColorFromItem(Item item) {
        return ShulkerBoxBlock.getColorFromBlock(Block.getBlockFromItem(item));
    }

    @Nullable
    public static DyeColor getColorFromBlock(Block block) {
        return block instanceof ShulkerBoxBlock ? ((ShulkerBoxBlock)block).getColor() : null;
    }

    public static Block getBlockByColor(@Nullable DyeColor dyeColor) {
        if (dyeColor == null) {
            return Blocks.SHULKER_BOX;
        }
        switch (1.$SwitchMap$net$minecraft$item$DyeColor[dyeColor.ordinal()]) {
            case 1: {
                return Blocks.WHITE_SHULKER_BOX;
            }
            case 2: {
                return Blocks.ORANGE_SHULKER_BOX;
            }
            case 3: {
                return Blocks.MAGENTA_SHULKER_BOX;
            }
            case 4: {
                return Blocks.LIGHT_BLUE_SHULKER_BOX;
            }
            case 5: {
                return Blocks.YELLOW_SHULKER_BOX;
            }
            case 6: {
                return Blocks.LIME_SHULKER_BOX;
            }
            case 7: {
                return Blocks.PINK_SHULKER_BOX;
            }
            case 8: {
                return Blocks.GRAY_SHULKER_BOX;
            }
            case 9: {
                return Blocks.LIGHT_GRAY_SHULKER_BOX;
            }
            case 10: {
                return Blocks.CYAN_SHULKER_BOX;
            }
            default: {
                return Blocks.PURPLE_SHULKER_BOX;
            }
            case 12: {
                return Blocks.BLUE_SHULKER_BOX;
            }
            case 13: {
                return Blocks.BROWN_SHULKER_BOX;
            }
            case 14: {
                return Blocks.GREEN_SHULKER_BOX;
            }
            case 15: {
                return Blocks.RED_SHULKER_BOX;
            }
            case 16: 
        }
        return Blocks.BLACK_SHULKER_BOX;
    }

    @Nullable
    public DyeColor getColor() {
        return this.color;
    }

    public static ItemStack getColoredItemStack(@Nullable DyeColor dyeColor) {
        return new ItemStack(ShulkerBoxBlock.getBlockByColor(dyeColor));
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.toRotation(blockState.get(FACING)));
    }

    private static void lambda$getDrops$0(ShulkerBoxTileEntity shulkerBoxTileEntity, LootContext lootContext, Consumer consumer) {
        for (int i = 0; i < shulkerBoxTileEntity.getSizeInventory(); ++i) {
            consumer.accept(shulkerBoxTileEntity.getStackInSlot(i));
        }
    }
}

