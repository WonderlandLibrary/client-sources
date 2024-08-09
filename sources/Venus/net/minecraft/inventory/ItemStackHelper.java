/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;

public class ItemStackHelper {
    public static ItemStack getAndSplit(List<ItemStack> list, int n, int n2) {
        return n >= 0 && n < list.size() && !list.get(n).isEmpty() && n2 > 0 ? list.get(n).split(n2) : ItemStack.EMPTY;
    }

    public static ItemStack getAndRemove(List<ItemStack> list, int n) {
        return n >= 0 && n < list.size() ? list.set(n, ItemStack.EMPTY) : ItemStack.EMPTY;
    }

    public static CompoundNBT saveAllItems(CompoundNBT compoundNBT, NonNullList<ItemStack> nonNullList) {
        return ItemStackHelper.saveAllItems(compoundNBT, nonNullList, true);
    }

    public static CompoundNBT saveAllItems(CompoundNBT compoundNBT, NonNullList<ItemStack> nonNullList, boolean bl) {
        ListNBT listNBT = new ListNBT();
        for (int i = 0; i < nonNullList.size(); ++i) {
            ItemStack itemStack = nonNullList.get(i);
            if (itemStack.isEmpty()) continue;
            CompoundNBT compoundNBT2 = new CompoundNBT();
            compoundNBT2.putByte("Slot", (byte)i);
            itemStack.write(compoundNBT2);
            listNBT.add(compoundNBT2);
        }
        if (!listNBT.isEmpty() || bl) {
            compoundNBT.put("Items", listNBT);
        }
        return compoundNBT;
    }

    public static void loadAllItems(CompoundNBT compoundNBT, NonNullList<ItemStack> nonNullList) {
        ListNBT listNBT = compoundNBT.getList("Items", 10);
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT2 = listNBT.getCompound(i);
            int n = compoundNBT2.getByte("Slot") & 0xFF;
            if (n < 0 || n >= nonNullList.size()) continue;
            nonNullList.set(n, ItemStack.read(compoundNBT2));
        }
    }

    public static int func_233534_a_(IInventory iInventory, Predicate<ItemStack> predicate, int n, boolean bl) {
        int n2 = 0;
        for (int i = 0; i < iInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = iInventory.getStackInSlot(i);
            int n3 = ItemStackHelper.func_233535_a_(itemStack, predicate, n - n2, bl);
            if (n3 > 0 && !bl && itemStack.isEmpty()) {
                iInventory.setInventorySlotContents(i, ItemStack.EMPTY);
            }
            n2 += n3;
        }
        return n2;
    }

    public static int func_233535_a_(ItemStack itemStack, Predicate<ItemStack> predicate, int n, boolean bl) {
        if (!itemStack.isEmpty() && predicate.test(itemStack)) {
            if (bl) {
                return itemStack.getCount();
            }
            int n2 = n < 0 ? itemStack.getCount() : Math.min(n, itemStack.getCount());
            itemStack.shrink(n2);
            return n2;
        }
        return 1;
    }
}

