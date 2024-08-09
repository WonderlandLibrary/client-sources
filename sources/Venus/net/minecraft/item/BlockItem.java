/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class BlockItem
extends Item {
    @Deprecated
    private final Block block;

    public BlockItem(Block block, Item.Properties properties) {
        super(properties);
        this.block = block;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        ActionResultType actionResultType = this.tryPlace(new BlockItemUseContext(itemUseContext));
        return !actionResultType.isSuccessOrConsume() && this.isFood() ? this.onItemRightClick(itemUseContext.getWorld(), itemUseContext.getPlayer(), itemUseContext.getHand()).getType() : actionResultType;
    }

    public ActionResultType tryPlace(BlockItemUseContext blockItemUseContext) {
        if (!blockItemUseContext.canPlace()) {
            return ActionResultType.FAIL;
        }
        BlockItemUseContext blockItemUseContext2 = this.getBlockItemUseContext(blockItemUseContext);
        if (blockItemUseContext2 == null) {
            return ActionResultType.FAIL;
        }
        BlockState blockState = this.getStateForPlacement(blockItemUseContext2);
        if (blockState == null) {
            return ActionResultType.FAIL;
        }
        if (!this.placeBlock(blockItemUseContext2, blockState)) {
            return ActionResultType.FAIL;
        }
        BlockPos blockPos = blockItemUseContext2.getPos();
        World world = blockItemUseContext2.getWorld();
        PlayerEntity playerEntity = blockItemUseContext2.getPlayer();
        ItemStack itemStack = blockItemUseContext2.getItem();
        BlockState blockState2 = world.getBlockState(blockPos);
        Block block = blockState2.getBlock();
        if (block == blockState.getBlock()) {
            blockState2 = this.func_219985_a(blockPos, world, itemStack, blockState2);
            this.onBlockPlaced(blockPos, world, playerEntity, itemStack, blockState2);
            block.onBlockPlacedBy(world, blockPos, blockState2, playerEntity, itemStack);
            if (playerEntity instanceof ServerPlayerEntity) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
            }
        }
        SoundType soundType = blockState2.getSoundType();
        world.playSound(playerEntity, blockPos, this.getPlaceSound(blockState2), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0f) / 2.0f, soundType.getPitch() * 0.8f);
        if (playerEntity == null || !playerEntity.abilities.isCreativeMode) {
            itemStack.shrink(1);
        }
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    protected SoundEvent getPlaceSound(BlockState blockState) {
        return blockState.getSoundType().getPlaceSound();
    }

    @Nullable
    public BlockItemUseContext getBlockItemUseContext(BlockItemUseContext blockItemUseContext) {
        return blockItemUseContext;
    }

    protected boolean onBlockPlaced(BlockPos blockPos, World world, @Nullable PlayerEntity playerEntity, ItemStack itemStack, BlockState blockState) {
        return BlockItem.setTileEntityNBT(world, playerEntity, blockPos, itemStack);
    }

    @Nullable
    protected BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = this.getBlock().getStateForPlacement(blockItemUseContext);
        return blockState != null && this.canPlace(blockItemUseContext, blockState) ? blockState : null;
    }

    private BlockState func_219985_a(BlockPos blockPos, World world, ItemStack itemStack, BlockState blockState) {
        BlockState blockState2 = blockState;
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null) {
            CompoundNBT compoundNBT2 = compoundNBT.getCompound("BlockStateTag");
            StateContainer<Block, BlockState> stateContainer = blockState.getBlock().getStateContainer();
            for (String string : compoundNBT2.keySet()) {
                Property<?> property = stateContainer.getProperty(string);
                if (property == null) continue;
                String string2 = compoundNBT2.get(string).getString();
                blockState2 = BlockItem.func_219988_a(blockState2, property, string2);
            }
        }
        if (blockState2 != blockState) {
            world.setBlockState(blockPos, blockState2, 1);
        }
        return blockState2;
    }

    private static <T extends Comparable<T>> BlockState func_219988_a(BlockState blockState, Property<T> property, String string) {
        return property.parseValue(string).map(arg_0 -> BlockItem.lambda$func_219988_a$0(blockState, property, arg_0)).orElse(blockState);
    }

    protected boolean canPlace(BlockItemUseContext blockItemUseContext, BlockState blockState) {
        PlayerEntity playerEntity = blockItemUseContext.getPlayer();
        ISelectionContext iSelectionContext = playerEntity == null ? ISelectionContext.dummy() : ISelectionContext.forEntity(playerEntity);
        return (!this.checkPosition() || blockState.isValidPosition(blockItemUseContext.getWorld(), blockItemUseContext.getPos())) && blockItemUseContext.getWorld().placedBlockCollides(blockState, blockItemUseContext.getPos(), iSelectionContext);
    }

    protected boolean checkPosition() {
        return false;
    }

    protected boolean placeBlock(BlockItemUseContext blockItemUseContext, BlockState blockState) {
        return blockItemUseContext.getWorld().setBlockState(blockItemUseContext.getPos(), blockState, 0);
    }

    public static boolean setTileEntityNBT(World world, @Nullable PlayerEntity playerEntity, BlockPos blockPos, ItemStack itemStack) {
        TileEntity tileEntity;
        MinecraftServer minecraftServer = world.getServer();
        if (minecraftServer == null) {
            return true;
        }
        CompoundNBT compoundNBT = itemStack.getChildTag("BlockEntityTag");
        if (compoundNBT != null && (tileEntity = world.getTileEntity(blockPos)) != null) {
            if (!(world.isRemote || !tileEntity.onlyOpsCanSetNbt() || playerEntity != null && playerEntity.canUseCommandBlock())) {
                return true;
            }
            CompoundNBT compoundNBT2 = tileEntity.write(new CompoundNBT());
            CompoundNBT compoundNBT3 = compoundNBT2.copy();
            compoundNBT2.merge(compoundNBT);
            compoundNBT2.putInt("x", blockPos.getX());
            compoundNBT2.putInt("y", blockPos.getY());
            compoundNBT2.putInt("z", blockPos.getZ());
            if (!compoundNBT2.equals(compoundNBT3)) {
                tileEntity.read(world.getBlockState(blockPos), compoundNBT2);
                tileEntity.markDirty();
                return false;
            }
        }
        return true;
    }

    @Override
    public String getTranslationKey() {
        return this.getBlock().getTranslationKey();
    }

    @Override
    public void fillItemGroup(ItemGroup itemGroup, NonNullList<ItemStack> nonNullList) {
        if (this.isInGroup(itemGroup)) {
            this.getBlock().fillItemGroup(itemGroup, nonNullList);
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        super.addInformation(itemStack, world, list, iTooltipFlag);
        this.getBlock().addInformation(itemStack, world, list, iTooltipFlag);
    }

    public Block getBlock() {
        return this.block;
    }

    public void addToBlockToItemMap(Map<Block, Item> map, Item item) {
        map.put(this.getBlock(), item);
    }

    private static BlockState lambda$func_219988_a$0(BlockState blockState, Property property, Comparable comparable) {
        return (BlockState)blockState.with(property, comparable);
    }
}

