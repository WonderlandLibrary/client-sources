package net.minecraft.util;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import com.google.common.collect.*;
import java.util.*;

public class WeightedRandomChestContent extends WeightedRandom.Item
{
    private ItemStack theItemId;
    private int theMinimumChanceToGenerateItem;
    private int theMaximumChanceToGenerateItem;
    
    public WeightedRandomChestContent(final net.minecraft.item.Item p_i45311_1_, final int p_i45311_2_, final int p_i45311_3_, final int p_i45311_4_, final int p_i45311_5_) {
        super(p_i45311_5_);
        this.theItemId = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
        this.theMinimumChanceToGenerateItem = p_i45311_3_;
        this.theMaximumChanceToGenerateItem = p_i45311_4_;
    }
    
    public WeightedRandomChestContent(final ItemStack p_i1558_1_, final int p_i1558_2_, final int p_i1558_3_, final int p_i1558_4_) {
        super(p_i1558_4_);
        this.theItemId = p_i1558_1_;
        this.theMinimumChanceToGenerateItem = p_i1558_2_;
        this.theMaximumChanceToGenerateItem = p_i1558_3_;
    }
    
    public static void generateChestContents(final Random p_177630_0_, final List p_177630_1_, final IInventory p_177630_2_, final int p_177630_3_) {
        for (int var4 = 0; var4 < p_177630_3_; ++var4) {
            final WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(p_177630_0_, p_177630_1_);
            final int var6 = var5.theMinimumChanceToGenerateItem + p_177630_0_.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
            if (var5.theItemId.getMaxStackSize() >= var6) {
                final ItemStack var7 = var5.theItemId.copy();
                var7.stackSize = var6;
                p_177630_2_.setInventorySlotContents(p_177630_0_.nextInt(p_177630_2_.getSizeInventory()), var7);
            }
            else {
                for (int var8 = 0; var8 < var6; ++var8) {
                    final ItemStack var9 = var5.theItemId.copy();
                    var9.stackSize = 1;
                    p_177630_2_.setInventorySlotContents(p_177630_0_.nextInt(p_177630_2_.getSizeInventory()), var9);
                }
            }
        }
    }
    
    public static void func_177631_a(final Random p_177631_0_, final List p_177631_1_, final TileEntityDispenser p_177631_2_, final int p_177631_3_) {
        for (int var4 = 0; var4 < p_177631_3_; ++var4) {
            final WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(p_177631_0_, p_177631_1_);
            final int var6 = var5.theMinimumChanceToGenerateItem + p_177631_0_.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
            if (var5.theItemId.getMaxStackSize() >= var6) {
                final ItemStack var7 = var5.theItemId.copy();
                var7.stackSize = var6;
                p_177631_2_.setInventorySlotContents(p_177631_0_.nextInt(p_177631_2_.getSizeInventory()), var7);
            }
            else {
                for (int var8 = 0; var8 < var6; ++var8) {
                    final ItemStack var9 = var5.theItemId.copy();
                    var9.stackSize = 1;
                    p_177631_2_.setInventorySlotContents(p_177631_0_.nextInt(p_177631_2_.getSizeInventory()), var9);
                }
            }
        }
    }
    
    public static List func_177629_a(final List p_177629_0_, final WeightedRandomChestContent... p_177629_1_) {
        final ArrayList var2 = Lists.newArrayList((Iterable)p_177629_0_);
        Collections.addAll(var2, p_177629_1_);
        return var2;
    }
}
