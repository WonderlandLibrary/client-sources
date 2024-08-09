/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IDyeableArmorItem {
    default public boolean hasColor(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getChildTag("display");
        return compoundNBT != null && compoundNBT.contains("color", 0);
    }

    default public int getColor(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getChildTag("display");
        return compoundNBT != null && compoundNBT.contains("color", 0) ? compoundNBT.getInt("color") : 10511680;
    }

    default public void removeColor(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getChildTag("display");
        if (compoundNBT != null && compoundNBT.contains("color")) {
            compoundNBT.remove("color");
        }
    }

    default public void setColor(ItemStack itemStack, int n) {
        itemStack.getOrCreateChildTag("display").putInt("color", n);
    }

    public static ItemStack dyeItem(ItemStack itemStack, List<DyeItem> list) {
        int n;
        float f;
        ItemStack itemStack2 = ItemStack.EMPTY;
        int[] nArray = new int[3];
        int n2 = 0;
        int n3 = 0;
        IDyeableArmorItem iDyeableArmorItem = null;
        Item item = itemStack.getItem();
        if (item instanceof IDyeableArmorItem) {
            iDyeableArmorItem = (IDyeableArmorItem)((Object)item);
            itemStack2 = itemStack.copy();
            itemStack2.setCount(1);
            if (iDyeableArmorItem.hasColor(itemStack)) {
                int n4 = iDyeableArmorItem.getColor(itemStack2);
                float f2 = (float)(n4 >> 16 & 0xFF) / 255.0f;
                float f3 = (float)(n4 >> 8 & 0xFF) / 255.0f;
                f = (float)(n4 & 0xFF) / 255.0f;
                n2 = (int)((float)n2 + Math.max(f2, Math.max(f3, f)) * 255.0f);
                nArray[0] = (int)((float)nArray[0] + f2 * 255.0f);
                nArray[1] = (int)((float)nArray[1] + f3 * 255.0f);
                nArray[2] = (int)((float)nArray[2] + f * 255.0f);
                ++n3;
            }
            for (DyeItem dyeItem : list) {
                float[] fArray = dyeItem.getDyeColor().getColorComponentValues();
                int n5 = (int)(fArray[0] * 255.0f);
                int n6 = (int)(fArray[1] * 255.0f);
                n = (int)(fArray[2] * 255.0f);
                n2 += Math.max(n5, Math.max(n6, n));
                nArray[0] = nArray[0] + n5;
                nArray[1] = nArray[1] + n6;
                nArray[2] = nArray[2] + n;
                ++n3;
            }
        }
        if (iDyeableArmorItem == null) {
            return ItemStack.EMPTY;
        }
        int n7 = nArray[0] / n3;
        int n8 = nArray[1] / n3;
        int n9 = nArray[2] / n3;
        f = (float)n2 / (float)n3;
        float f4 = Math.max(n7, Math.max(n8, n9));
        n7 = (int)((float)n7 * f / f4);
        n8 = (int)((float)n8 * f / f4);
        n9 = (int)((float)n9 * f / f4);
        n = (n7 << 8) + n8;
        n = (n << 8) + n9;
        iDyeableArmorItem.setColor(itemStack2, n);
        return itemStack2;
    }
}

