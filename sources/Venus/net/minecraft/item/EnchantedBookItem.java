/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class EnchantedBookItem
extends Item {
    public EnchantedBookItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return true;
    }

    public static ListNBT getEnchantments(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getTag();
        return compoundNBT != null ? compoundNBT.getList("StoredEnchantments", 10) : new ListNBT();
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        super.addInformation(itemStack, world, list, iTooltipFlag);
        ItemStack.addEnchantmentTooltips(list, EnchantedBookItem.getEnchantments(itemStack));
    }

    public static void addEnchantment(ItemStack itemStack, EnchantmentData enchantmentData) {
        ListNBT listNBT = EnchantedBookItem.getEnchantments(itemStack);
        boolean bl = true;
        ResourceLocation resourceLocation = Registry.ENCHANTMENT.getKey(enchantmentData.enchantment);
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT = listNBT.getCompound(i);
            ResourceLocation resourceLocation2 = ResourceLocation.tryCreate(compoundNBT.getString("id"));
            if (resourceLocation2 == null || !resourceLocation2.equals(resourceLocation)) continue;
            if (compoundNBT.getInt("lvl") < enchantmentData.enchantmentLevel) {
                compoundNBT.putShort("lvl", (short)enchantmentData.enchantmentLevel);
            }
            bl = false;
            break;
        }
        if (bl) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("id", String.valueOf(resourceLocation));
            compoundNBT.putShort("lvl", (short)enchantmentData.enchantmentLevel);
            listNBT.add(compoundNBT);
        }
        itemStack.getOrCreateTag().put("StoredEnchantments", listNBT);
    }

    public static ItemStack getEnchantedItemStack(EnchantmentData enchantmentData) {
        ItemStack itemStack = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(itemStack, enchantmentData);
        return itemStack;
    }

    @Override
    public void fillItemGroup(ItemGroup itemGroup, NonNullList<ItemStack> nonNullList) {
        block4: {
            block3: {
                if (itemGroup != ItemGroup.SEARCH) break block3;
                for (Enchantment enchantment : Registry.ENCHANTMENT) {
                    if (enchantment.type == null) continue;
                    for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
                        nonNullList.add(EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enchantment, i)));
                    }
                }
                break block4;
            }
            if (itemGroup.getRelevantEnchantmentTypes().length == 0) break block4;
            for (Enchantment enchantment : Registry.ENCHANTMENT) {
                if (!itemGroup.hasRelevantEnchantmentType(enchantment.type)) continue;
                nonNullList.add(EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enchantment, enchantment.getMaxLevel())));
            }
        }
    }
}

