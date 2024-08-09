/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.DyeColor;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FireworkStarItem
extends Item {
    public FireworkStarItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        CompoundNBT compoundNBT = itemStack.getChildTag("Explosion");
        if (compoundNBT != null) {
            FireworkStarItem.func_195967_a(compoundNBT, list);
        }
    }

    public static void func_195967_a(CompoundNBT compoundNBT, List<ITextComponent> list) {
        int[] nArray;
        FireworkRocketItem.Shape shape = FireworkRocketItem.Shape.get(compoundNBT.getByte("Type"));
        list.add(new TranslationTextComponent("item.minecraft.firework_star.shape." + shape.getShapeName()).mergeStyle(TextFormatting.GRAY));
        int[] nArray2 = compoundNBT.getIntArray("Colors");
        if (nArray2.length > 0) {
            list.add(FireworkStarItem.func_200298_a_(new StringTextComponent("").mergeStyle(TextFormatting.GRAY), nArray2));
        }
        if ((nArray = compoundNBT.getIntArray("FadeColors")).length > 0) {
            list.add(FireworkStarItem.func_200298_a_(new TranslationTextComponent("item.minecraft.firework_star.fade_to").appendString(" ").mergeStyle(TextFormatting.GRAY), nArray));
        }
        if (compoundNBT.getBoolean("Trail")) {
            list.add(new TranslationTextComponent("item.minecraft.firework_star.trail").mergeStyle(TextFormatting.GRAY));
        }
        if (compoundNBT.getBoolean("Flicker")) {
            list.add(new TranslationTextComponent("item.minecraft.firework_star.flicker").mergeStyle(TextFormatting.GRAY));
        }
    }

    private static ITextComponent func_200298_a_(IFormattableTextComponent iFormattableTextComponent, int[] nArray) {
        for (int i = 0; i < nArray.length; ++i) {
            if (i > 0) {
                iFormattableTextComponent.appendString(", ");
            }
            iFormattableTextComponent.append(FireworkStarItem.func_200297_a(nArray[i]));
        }
        return iFormattableTextComponent;
    }

    private static ITextComponent func_200297_a(int n) {
        DyeColor dyeColor = DyeColor.byFireworkColor(n);
        return dyeColor == null ? new TranslationTextComponent("item.minecraft.firework_star.custom_color") : new TranslationTextComponent("item.minecraft.firework_star." + dyeColor.getTranslationKey());
    }
}

