/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.WeightedRandom;

public class WeightedRandomChestContent
extends WeightedRandom.Item {
    private ItemStack theItemId;
    private int theMinimumChanceToGenerateItem;
    private int theMaximumChanceToGenerateItem;
    private static final String __OBFID = "CL_00001505";

    public WeightedRandomChestContent(Item p_i45311_1_, int p_i45311_2_, int p_i45311_3_, int p_i45311_4_, int p_i45311_5_) {
        super(p_i45311_5_);
        this.theItemId = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
        this.theMinimumChanceToGenerateItem = p_i45311_3_;
        this.theMaximumChanceToGenerateItem = p_i45311_4_;
    }

    public WeightedRandomChestContent(ItemStack p_i1558_1_, int p_i1558_2_, int p_i1558_3_, int p_i1558_4_) {
        super(p_i1558_4_);
        this.theItemId = p_i1558_1_;
        this.theMinimumChanceToGenerateItem = p_i1558_2_;
        this.theMaximumChanceToGenerateItem = p_i1558_3_;
    }

    public static void generateChestContents(Random p_177630_0_, List p_177630_1_, IInventory p_177630_2_, int p_177630_3_) {
        int var4 = 0;
        while (var4 < p_177630_3_) {
            WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(p_177630_0_, p_177630_1_);
            int var6 = var5.theMinimumChanceToGenerateItem + p_177630_0_.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
            if (var5.theItemId.getMaxStackSize() >= var6) {
                ItemStack var7 = var5.theItemId.copy();
                var7.stackSize = var6;
                p_177630_2_.setInventorySlotContents(p_177630_0_.nextInt(p_177630_2_.getSizeInventory()), var7);
            } else {
                int var9 = 0;
                while (var9 < var6) {
                    ItemStack var8 = var5.theItemId.copy();
                    var8.stackSize = 1;
                    p_177630_2_.setInventorySlotContents(p_177630_0_.nextInt(p_177630_2_.getSizeInventory()), var8);
                    ++var9;
                }
            }
            ++var4;
        }
    }

    public static void func_177631_a(Random p_177631_0_, List p_177631_1_, TileEntityDispenser p_177631_2_, int p_177631_3_) {
        int var4 = 0;
        while (var4 < p_177631_3_) {
            WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(p_177631_0_, p_177631_1_);
            int var6 = var5.theMinimumChanceToGenerateItem + p_177631_0_.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
            if (var5.theItemId.getMaxStackSize() >= var6) {
                ItemStack var7 = var5.theItemId.copy();
                var7.stackSize = var6;
                p_177631_2_.setInventorySlotContents(p_177631_0_.nextInt(p_177631_2_.getSizeInventory()), var7);
            } else {
                int var9 = 0;
                while (var9 < var6) {
                    ItemStack var8 = var5.theItemId.copy();
                    var8.stackSize = 1;
                    p_177631_2_.setInventorySlotContents(p_177631_0_.nextInt(p_177631_2_.getSizeInventory()), var8);
                    ++var9;
                }
            }
            ++var4;
        }
    }

    public static /* varargs */ List func_177629_a(List p_177629_0_, WeightedRandomChestContent ... p_177629_1_) {
        ArrayList var2 = Lists.newArrayList((Iterable)p_177629_0_);
        Collections.addAll(var2, p_177629_1_);
        return var2;
    }
}

