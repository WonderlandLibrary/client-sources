/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class TippedArrowItem
extends ArrowItem {
    public TippedArrowItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        return PotionUtils.addPotionToItemStack(super.getDefaultInstance(), Potions.POISON);
    }

    @Override
    public void fillItemGroup(ItemGroup itemGroup, NonNullList<ItemStack> nonNullList) {
        if (this.isInGroup(itemGroup)) {
            for (Potion potion : Registry.POTION) {
                if (potion.getEffects().isEmpty()) continue;
                nonNullList.add(PotionUtils.addPotionToItemStack(new ItemStack(this), potion));
            }
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        PotionUtils.addPotionTooltip(itemStack, list, 0.125f);
    }

    @Override
    public String getTranslationKey(ItemStack itemStack) {
        return PotionUtils.getPotionFromItem(itemStack).getNamePrefixed(this.getTranslationKey() + ".effect.");
    }
}

