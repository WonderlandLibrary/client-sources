/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.StatCollector;

public class ItemFireworkCharge
extends Item {
    private static final String __OBFID = "CL_00000030";

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        if (renderPass != 1) {
            return super.getColorFromItemStack(stack, renderPass);
        }
        NBTBase var3 = ItemFireworkCharge.func_150903_a(stack, "Colors");
        if (!(var3 instanceof NBTTagIntArray)) {
            return 9079434;
        }
        NBTTagIntArray var4 = (NBTTagIntArray)var3;
        int[] var5 = var4.getIntArray();
        if (var5.length == 1) {
            return var5[0];
        }
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        int[] var9 = var5;
        int var10 = var5.length;
        int var11 = 0;
        while (var11 < var10) {
            int var12 = var9[var11];
            var6 += (var12 & 16711680) >> 16;
            var7 += (var12 & 65280) >> 8;
            var8 += (var12 & 255) >> 0;
            ++var11;
        }
        return (var6 /= var5.length) << 16 | (var7 /= var5.length) << 8 | (var8 /= var5.length);
    }

    public static NBTBase func_150903_a(ItemStack p_150903_0_, String p_150903_1_) {
        NBTTagCompound var2;
        if (p_150903_0_.hasTagCompound() && (var2 = p_150903_0_.getTagCompound().getCompoundTag("Explosion")) != null) {
            return var2.getTag(p_150903_1_);
        }
        return null;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        NBTTagCompound var5;
        if (stack.hasTagCompound() && (var5 = stack.getTagCompound().getCompoundTag("Explosion")) != null) {
            ItemFireworkCharge.func_150902_a(var5, tooltip);
        }
    }

    public static void func_150902_a(NBTTagCompound p_150902_0_, List p_150902_1_) {
        boolean var16;
        int var8;
        int var9;
        boolean var14;
        int[] var13;
        byte var2 = p_150902_0_.getByte("Type");
        if (var2 >= 0 && var2 <= 4) {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.type." + var2).trim());
        } else {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
        }
        int[] var3 = p_150902_0_.getIntArray("Colors");
        if (var3.length > 0) {
            boolean var4 = true;
            String var5 = "";
            int[] var6 = var3;
            int var7 = var3.length;
            var8 = 0;
            while (var8 < var7) {
                var9 = var6[var8];
                if (!var4) {
                    var5 = String.valueOf(var5) + ", ";
                }
                var4 = false;
                boolean var10 = false;
                int var11 = 0;
                while (var11 < ItemDye.dyeColors.length) {
                    if (var9 == ItemDye.dyeColors[var11]) {
                        var10 = true;
                        var5 = String.valueOf(var5) + StatCollector.translateToLocal(new StringBuilder("item.fireworksCharge.").append(EnumDyeColor.func_176766_a(var11).func_176762_d()).toString());
                        break;
                    }
                    ++var11;
                }
                if (!var10) {
                    var5 = String.valueOf(var5) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
                ++var8;
            }
            p_150902_1_.add(var5);
        }
        if ((var13 = p_150902_0_.getIntArray("FadeColors")).length > 0) {
            boolean var142 = true;
            String var15 = String.valueOf(StatCollector.translateToLocal("item.fireworksCharge.fadeTo")) + " ";
            int[] var17 = var13;
            var8 = var13.length;
            var9 = 0;
            while (var9 < var8) {
                int var18 = var17[var9];
                if (!var142) {
                    var15 = String.valueOf(var15) + ", ";
                }
                var142 = false;
                boolean var19 = false;
                int var12 = 0;
                while (var12 < 16) {
                    if (var18 == ItemDye.dyeColors[var12]) {
                        var19 = true;
                        var15 = String.valueOf(var15) + StatCollector.translateToLocal(new StringBuilder("item.fireworksCharge.").append(EnumDyeColor.func_176766_a(var12).func_176762_d()).toString());
                        break;
                    }
                    ++var12;
                }
                if (!var19) {
                    var15 = String.valueOf(var15) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
                ++var9;
            }
            p_150902_1_.add(var15);
        }
        if (var14 = p_150902_0_.getBoolean("Trail")) {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
        }
        if (var16 = p_150902_0_.getBoolean("Flicker")) {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
        }
    }
}

