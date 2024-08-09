/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class DebugStickItem
extends Item {
    public DebugStickItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        if (!world.isRemote) {
            this.handleClick(playerEntity, blockState, world, blockPos, false, playerEntity.getHeldItem(Hand.MAIN_HAND));
        }
        return true;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        PlayerEntity playerEntity = itemUseContext.getPlayer();
        World world = itemUseContext.getWorld();
        if (!world.isRemote && playerEntity != null) {
            BlockPos blockPos = itemUseContext.getPos();
            this.handleClick(playerEntity, world.getBlockState(blockPos), world, blockPos, true, itemUseContext.getItem());
        }
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    private void handleClick(PlayerEntity playerEntity, BlockState blockState, IWorld iWorld, BlockPos blockPos, boolean bl, ItemStack itemStack) {
        if (playerEntity.canUseCommandBlock()) {
            Block block = blockState.getBlock();
            StateContainer<Block, BlockState> stateContainer = block.getStateContainer();
            Collection<Property<?>> collection = stateContainer.getProperties();
            String string = Registry.BLOCK.getKey(block).toString();
            if (collection.isEmpty()) {
                DebugStickItem.sendMessage(playerEntity, new TranslationTextComponent(this.getTranslationKey() + ".empty", string));
            } else {
                CompoundNBT compoundNBT = itemStack.getOrCreateChildTag("DebugProperty");
                String string2 = compoundNBT.getString(string);
                Property<?> property = stateContainer.getProperty(string2);
                if (bl) {
                    if (property == null) {
                        property = collection.iterator().next();
                    }
                    BlockState blockState2 = DebugStickItem.cycleProperty(blockState, property, playerEntity.isSecondaryUseActive());
                    iWorld.setBlockState(blockPos, blockState2, 18);
                    DebugStickItem.sendMessage(playerEntity, new TranslationTextComponent(this.getTranslationKey() + ".update", property.getName(), DebugStickItem.func_195957_a(blockState2, property)));
                } else {
                    property = DebugStickItem.getAdjacentValue(collection, property, playerEntity.isSecondaryUseActive());
                    String string3 = property.getName();
                    compoundNBT.putString(string, string3);
                    DebugStickItem.sendMessage(playerEntity, new TranslationTextComponent(this.getTranslationKey() + ".select", string3, DebugStickItem.func_195957_a(blockState, property)));
                }
            }
        }
    }

    private static <T extends Comparable<T>> BlockState cycleProperty(BlockState blockState, Property<T> property, boolean bl) {
        return (BlockState)blockState.with(property, (Comparable)DebugStickItem.getAdjacentValue(property.getAllowedValues(), blockState.get(property), bl));
    }

    private static <T> T getAdjacentValue(Iterable<T> iterable, @Nullable T t, boolean bl) {
        return bl ? Util.getElementBefore(iterable, t) : Util.getElementAfter(iterable, t);
    }

    private static void sendMessage(PlayerEntity playerEntity, ITextComponent iTextComponent) {
        ((ServerPlayerEntity)playerEntity).func_241151_a_(iTextComponent, ChatType.GAME_INFO, Util.DUMMY_UUID);
    }

    private static <T extends Comparable<T>> String func_195957_a(BlockState blockState, Property<T> property) {
        return property.getName(blockState.get(property));
    }
}

