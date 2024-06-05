package me.vaziak.sensation.utils.anthony;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author antja03
 */
public class ContainerUtil {

    public static boolean isContainerFull(Container container) {
        boolean full = true;
        for (Slot slot : container.inventorySlots) {
            if (!slot.getHasStack()) {
                full = false;
                break;
            }
        }
        return full;
    }

    public static int getArmorItemsEquipSlot(ItemStack stack, boolean equipmentSlot) {
        if (stack.getUnlocalizedName().contains("helmet"))
            return equipmentSlot ? 4 : 5;
        if (stack.getUnlocalizedName().contains("chestplate"))
            return equipmentSlot ? 3 : 6;
        if (stack.getUnlocalizedName().contains("leggings"))
            return equipmentSlot ? 2 : 7;
        if (stack.getUnlocalizedName().contains("boots"))
            return equipmentSlot ? 1 : 8;
        return -1;
    }
}
