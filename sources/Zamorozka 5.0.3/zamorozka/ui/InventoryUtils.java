package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class InventoryUtils {
    public static Minecraft mc = Minecraft.getMinecraft();

    public void dropSlot(int slot) {
        int windowId = new GuiInventory((EntityPlayer)InventoryUtils.mc.player).inventorySlots.windowId;
        InventoryUtils.mc.playerController.windowClick(windowId, slot, 1, ClickType.THROW, InventoryUtils.mc.player);
    }

    public static ItemStack getStackInSlot(int slot) {
        return InventoryUtils.mc.player.inventory.getStackInSlot(slot);
    }

    /*
     * Enabled aggressive exception aggregation
     */
    
    public static int findHotbarItem(int itemID) {
		for (int o = 36; o <= 44; o++) {
			if (Minecraft.getMinecraft().player.inventoryContainer.getSlot(o).getHasStack()) {
				ItemStack item = Minecraft.getMinecraft().player.inventoryContainer.getSlot(o).getStack();
				if (item != null) {
					if (Item.getIdFromItem(item.getItem()) == itemID) { return o; }
				}
			}
		}
		return -1;
	}

	public static int findInventoryItem(int itemID) {
		for (int o = 9; o <= 35; o++) {
			if (Minecraft.getMinecraft().player.inventoryContainer.getSlot(o).getHasStack()) {
				ItemStack item = Minecraft.getMinecraft().player.inventoryContainer.getSlot(o).getStack();
				if (item != null) {
					if (Item.getIdFromItem(item.getItem()) == itemID) { return o; }
				}
			}
		}
		return -1;
	}

	public static int findWholeInventoryItem(int itemID) {
		for (int o = 9; o <= 44; o++) {
			if (Minecraft.getMinecraft().player.inventoryContainer.getSlot(o).getHasStack()) {
				ItemStack item = Minecraft.getMinecraft().player.inventoryContainer.getSlot(o).getStack();
				if (item != null) {
					if (Item.getIdFromItem(item.getItem()) == itemID) { return o; }
				}
			}
		}
		return -1;
	}

	public static int findWholeInventoryItem(ItemStack is) {
		for (int o = 9; o <= 44; o++) {
			if (Minecraft.getMinecraft().player.inventoryContainer.getSlot(o).getHasStack()) {
				ItemStack item = Minecraft.getMinecraft().player.inventoryContainer.getSlot(o).getStack();
				if (item != null) {
					if (item == is) { return o; }
				}
			}
		}

		return -1;
	}
    public static boolean hotbarHas(Item item) {
        int index = 0;
        while (index <= 36) {
            ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                return true;
            }
            ++index;
        }
        return false;
    }

    public static boolean hotbarHas(Item item, int slotID) {
        int index = 0;
        while (index <= 36) {
            ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item && InventoryUtils.getSlotID(stack.getItem()) == slotID) {
                return true;
            }
            ++index;
        }
        return false;
    }

    public static int getSlotID(Item item) {
        int index = 0;
        while (index <= 36) {
            ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public static ItemStack getItemBySlotID(int slotID) {
        int index = 0;
        while (index <= 36) {
            ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(index);
            if (stack != null && InventoryUtils.getSlotID(stack.getItem()) == slotID) {
                return stack;
            }
            ++index;
        }
        return null;
    }
    public boolean hasItemMoreTimes(int slotIn) {
        boolean has = false;
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.clear();
        int i = 0;
        while (i < InventoryUtils.mc.player.inventory.getSizeInventory()) {
            if (!stacks.contains(InventoryUtils.getStackInSlot(i))) {
                stacks.add(InventoryUtils.getStackInSlot(i));
            } else if (InventoryUtils.getStackInSlot(i) == InventoryUtils.getStackInSlot(slotIn)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static int getFirstItem(Item i1) {
        int i = 0;
        while (i < InventoryUtils.mc.player.inventory.getSizeInventory()) {
            if (InventoryUtils.getStackInSlot(i) != null && InventoryUtils.getStackInSlot(i).getItem() != null && InventoryUtils.getStackInSlot(i).getItem() == i1) {
                return i;
            }
            ++i;
        }
        return -1;
    }
    public static int getDepthStriderLevel() {
        return EnchantmentHelper.getDepthStriderModifier(Wrapper.getPlayer());
    }

    /**
     * @param slotId             The inventory slot you are clicking.
     *                           Armor slots:
     *                           Helmet is 5 and chest plate is 8
     *                           First slot of inventory is 9 (top left)
     *                           Last slot of inventory is 44 (bottom right)
     * @param mouseButtonClicked Hot bar slot
     * @param mode               The type of click
     */
}