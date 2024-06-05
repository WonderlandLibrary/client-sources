package me.satisfactory.base.utils.items;

import net.minecraft.client.*;
import net.minecraft.item.*;

public class ItemUtils
{
    public static int getItem(final int id2) {
        for (int index = 9; index < 45; ++index) {
            final ItemStack item = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(index).getStack();
            if (item != null && Item.getIdFromItem(item.getItem()) == id2) {
                return index;
            }
        }
        return -1;
    }
}
