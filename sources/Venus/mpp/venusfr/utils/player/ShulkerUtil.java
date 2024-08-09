/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.player;

import java.util.ArrayList;
import java.util.List;
import mpp.venusfr.ui.autobuy.AutoBuy;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

public class ShulkerUtil {
    public static List<ItemStack> getItemInShulker(ItemStack itemStack, AutoBuy autoBuy) {
        CompoundNBT compoundNBT = itemStack.getChildTag("BlockEntityTag");
        if (compoundNBT != null && compoundNBT.contains("Items", 0)) {
            NonNullList<ItemStack> nonNullList = NonNullList.withSize(27, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(compoundNBT, nonNullList);
            if (autoBuy.isDon()) {
                return nonNullList.stream().filter(ShulkerUtil::lambda$getItemInShulker$0).filter(ShulkerUtil::lambda$getItemInShulker$1).toList();
            }
            return nonNullList.stream().filter(ShulkerUtil::lambda$getItemInShulker$2).toList();
        }
        return new ArrayList<ItemStack>();
    }

    private static boolean lambda$getItemInShulker$2(ItemStack itemStack) {
        return itemStack.getItem() != Items.AIR;
    }

    private static boolean lambda$getItemInShulker$1(ItemStack itemStack) {
        return itemStack.getTag() != null && itemStack.getTag().contains("don-item");
    }

    private static boolean lambda$getItemInShulker$0(ItemStack itemStack) {
        return itemStack.getItem() != Items.AIR;
    }
}

