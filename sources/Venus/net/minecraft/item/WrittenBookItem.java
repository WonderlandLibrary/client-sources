/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.WritableBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class WrittenBookItem
extends Item {
    public WrittenBookItem(Item.Properties properties) {
        super(properties);
    }

    public static boolean validBookTagContents(@Nullable CompoundNBT compoundNBT) {
        if (!WritableBookItem.isNBTValid(compoundNBT)) {
            return true;
        }
        if (!compoundNBT.contains("title", 1)) {
            return true;
        }
        String string = compoundNBT.getString("title");
        return string.length() > 32 ? false : compoundNBT.contains("author", 1);
    }

    public static int getGeneration(ItemStack itemStack) {
        return itemStack.getTag().getInt("generation");
    }

    public static int getPageCount(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getTag();
        return compoundNBT != null ? compoundNBT.getList("pages", 8).size() : 0;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack itemStack) {
        CompoundNBT compoundNBT;
        String string;
        if (itemStack.hasTag() && !StringUtils.isNullOrEmpty(string = (compoundNBT = itemStack.getTag()).getString("title"))) {
            return new StringTextComponent(string);
        }
        return super.getDisplayName(itemStack);
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        if (itemStack.hasTag()) {
            CompoundNBT compoundNBT = itemStack.getTag();
            String string = compoundNBT.getString("author");
            if (!StringUtils.isNullOrEmpty(string)) {
                list.add(new TranslationTextComponent("book.byAuthor", string).mergeStyle(TextFormatting.GRAY));
            }
            list.add(new TranslationTextComponent("book.generation." + compoundNBT.getInt("generation")).mergeStyle(TextFormatting.GRAY));
        }
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

    public static boolean resolveContents(ItemStack itemStack, @Nullable CommandSource commandSource, @Nullable PlayerEntity playerEntity) {
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null && !compoundNBT.getBoolean("resolved")) {
            compoundNBT.putBoolean("resolved", false);
            if (!WrittenBookItem.validBookTagContents(compoundNBT)) {
                return true;
            }
            ListNBT listNBT = compoundNBT.getList("pages", 8);
            for (int i = 0; i < listNBT.size(); ++i) {
                IFormattableTextComponent iFormattableTextComponent;
                String string = listNBT.getString(i);
                try {
                    iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJsonLenient(string);
                    iFormattableTextComponent = TextComponentUtils.func_240645_a_(commandSource, iFormattableTextComponent, playerEntity, 0);
                } catch (Exception exception) {
                    iFormattableTextComponent = new StringTextComponent(string);
                }
                listNBT.set(i, StringNBT.valueOf(ITextComponent.Serializer.toJson(iFormattableTextComponent)));
            }
            compoundNBT.put("pages", listNBT);
            return false;
        }
        return true;
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return false;
    }
}

