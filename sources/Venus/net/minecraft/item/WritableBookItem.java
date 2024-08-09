/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WritableBookItem
extends Item {
    public WritableBookItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        BlockPos blockPos;
        World world = itemUseContext.getWorld();
        BlockState blockState = world.getBlockState(blockPos = itemUseContext.getPos());
        if (blockState.isIn(Blocks.LECTERN)) {
            return LecternBlock.tryPlaceBook(world, blockPos, blockState, itemUseContext.getItem()) ? ActionResultType.func_233537_a_(world.isRemote) : ActionResultType.PASS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        playerEntity.openBook(itemStack, hand);
        playerEntity.addStat(Stats.ITEM_USED.get(this));
        return ActionResult.func_233538_a_(itemStack, world.isRemote());
    }

    public static boolean isNBTValid(@Nullable CompoundNBT compoundNBT) {
        if (compoundNBT == null) {
            return true;
        }
        if (!compoundNBT.contains("pages", 0)) {
            return true;
        }
        ListNBT listNBT = compoundNBT.getList("pages", 8);
        for (int i = 0; i < listNBT.size(); ++i) {
            String string = listNBT.getString(i);
            if (string.length() <= Short.MAX_VALUE) continue;
            return true;
        }
        return false;
    }
}

