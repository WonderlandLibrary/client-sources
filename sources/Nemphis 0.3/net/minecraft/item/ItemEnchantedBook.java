/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.item;

import java.util.List;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemEnchantedBook
extends Item {
    private static final String __OBFID = "CL_00000025";

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isItemTool(ItemStack stack) {
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return this.func_92110_g(stack).tagCount() > 0 ? EnumRarity.UNCOMMON : super.getRarity(stack);
    }

    public NBTTagList func_92110_g(ItemStack p_92110_1_) {
        NBTTagCompound var2 = p_92110_1_.getTagCompound();
        return var2 != null && var2.hasKey("StoredEnchantments", 9) ? (NBTTagList)var2.getTag("StoredEnchantments") : new NBTTagList();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        NBTTagList var5 = this.func_92110_g(stack);
        if (var5 != null) {
            int var6 = 0;
            while (var6 < var5.tagCount()) {
                short var7 = var5.getCompoundTagAt(var6).getShort("id");
                short var8 = var5.getCompoundTagAt(var6).getShort("lvl");
                if (Enchantment.func_180306_c(var7) != null) {
                    tooltip.add(Enchantment.func_180306_c(var7).getTranslatedName(var8));
                }
                ++var6;
            }
        }
    }

    public void addEnchantment(ItemStack p_92115_1_, EnchantmentData p_92115_2_) {
        NBTTagList var3 = this.func_92110_g(p_92115_1_);
        boolean var4 = true;
        int var5 = 0;
        while (var5 < var3.tagCount()) {
            NBTTagCompound var6 = var3.getCompoundTagAt(var5);
            if (var6.getShort("id") == p_92115_2_.enchantmentobj.effectId) {
                if (var6.getShort("lvl") < p_92115_2_.enchantmentLevel) {
                    var6.setShort("lvl", (short)p_92115_2_.enchantmentLevel);
                }
                var4 = false;
                break;
            }
            ++var5;
        }
        if (var4) {
            NBTTagCompound var7 = new NBTTagCompound();
            var7.setShort("id", (short)p_92115_2_.enchantmentobj.effectId);
            var7.setShort("lvl", (short)p_92115_2_.enchantmentLevel);
            var3.appendTag(var7);
        }
        if (!p_92115_1_.hasTagCompound()) {
            p_92115_1_.setTagCompound(new NBTTagCompound());
        }
        p_92115_1_.getTagCompound().setTag("StoredEnchantments", var3);
    }

    public ItemStack getEnchantedItemStack(EnchantmentData p_92111_1_) {
        ItemStack var2 = new ItemStack(this);
        this.addEnchantment(var2, p_92111_1_);
        return var2;
    }

    public void func_92113_a(Enchantment p_92113_1_, List p_92113_2_) {
        int var3 = p_92113_1_.getMinLevel();
        while (var3 <= p_92113_1_.getMaxLevel()) {
            p_92113_2_.add(this.getEnchantedItemStack(new EnchantmentData(p_92113_1_, var3)));
            ++var3;
        }
    }

    public WeightedRandomChestContent getRandomEnchantedBook(Random p_92114_1_) {
        return this.func_92112_a(p_92114_1_, 1, 1, 1);
    }

    public WeightedRandomChestContent func_92112_a(Random p_92112_1_, int p_92112_2_, int p_92112_3_, int p_92112_4_) {
        ItemStack var5 = new ItemStack(Items.book, 1, 0);
        EnchantmentHelper.addRandomEnchantment(p_92112_1_, var5, 30);
        return new WeightedRandomChestContent(var5, p_92112_2_, p_92112_3_, p_92112_4_);
    }
}

