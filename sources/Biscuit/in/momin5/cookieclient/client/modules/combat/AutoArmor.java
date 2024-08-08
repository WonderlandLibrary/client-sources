package in.momin5.cookieclient.client.modules.combat;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class AutoArmor extends Module {
    public AutoArmor() {
        super("AutoArmor", Category.COMBAT);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onUpdate() {
        // checks
        if(nullCheck() || mc.player.ticksExisted % 2 == 0 || mc.currentScreen instanceof GuiContainer) return;

        if(getItemFromSlot(39).getItem() == Items.AIR) {
            switchSlot(getBestSlot(new Item[]{Items.DIAMOND_HELMET, Items.IRON_HELMET, Items.CHAINMAIL_HELMET, Items.GOLDEN_HELMET, Items.LEATHER_HELMET}), 39);
        }

        if(getItemFromSlot(38).getItem() == Items.AIR) {
            switchSlot(getBestSlot(new Item[]{Items.DIAMOND_CHESTPLATE, Items.IRON_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.LEATHER_CHESTPLATE}), 38);
        }

        if(getItemFromSlot(37).getItem() == Items.AIR) {
            switchSlot(getBestSlot(new Item[]{Items.DIAMOND_LEGGINGS, Items.IRON_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.LEATHER_LEGGINGS}), 37);
        }

        if(getItemFromSlot(36).getItem() == Items.AIR) {
            switchSlot(getBestSlot(new Item[]{Items.DIAMOND_BOOTS, Items.IRON_BOOTS, Items.CHAINMAIL_BOOTS, Items.GOLDEN_BOOTS, Items.LEATHER_BOOTS}), 36);
        }
    }

    private ItemStack getItemFromSlot(int slot) {
        try {
            return mc.player.inventory.getStackInSlot(slot);
        }catch (Exception e) {
            return null;
        }
    }

    public void switchSlot(int slot, int slot2) {
        if (slot == -1) {
            return;
        }

        clickSlot(slot);
        clickSlot(slot2);

    }

    public void clickSlot(int id) {
        if (id != -1) {
            try {
                mc.playerController.windowClick(mc.player.openContainer.windowId, getClickSlot(id), 0, ClickType.PICKUP, mc.player);
            } catch (Exception ignored) {

            }
        }
    }

    public int getClickSlot(int id) {
        if (id == -1) {
            return id;
        }

        if (id < 9) {
            id += 36;
            return id;
        }

        if (id == 39) {
            id = 5;
        } else if (id == 38) {
            id = 6;
        } else if (id == 37) {
            id = 7;
        } else if (id == 36) {
            id = 8;
        } else if (id == 40) {
            id = 45;
        }

        return id;
    }


    public int getBestSlot(Item[] items) {
        for (Item item : items) {
            for (ItemStackUtil itemStack : getAllItems()) {
                if (itemStack.itemStack.getItem() == item) {
                    return itemStack.slotId;
                }
            }
        }

        return -1;
    }

    public static class ItemStackUtil {
        public ItemStack itemStack;
        public int slotId;

        public ItemStackUtil(ItemStack itemStack, int slotId) {
            this.itemStack = itemStack;
            this.slotId = slotId;
        }
    }

    public ArrayList<ItemStackUtil> getAllItems() {
        ArrayList<ItemStackUtil> items = new ArrayList<ItemStackUtil>();

        for (int i = 0; i < 36; i++) {
            items.add(new ItemStackUtil(getItemFromSlot(i), i));
        }

        return items;
    }
}

