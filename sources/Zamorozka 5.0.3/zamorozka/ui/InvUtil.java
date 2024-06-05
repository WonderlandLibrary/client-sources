package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InvUtil implements MCUtil {

    public static void switchToHotbarSlot(int slot, boolean silent) {
        if (Minecraft.player.inventory.currentItem == slot || slot < 0) {
            return;
        }

        if (silent) {
            Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            mc.playerController.updateController();
        } else {
            Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            Minecraft.player.inventory.currentItem = slot;
            mc.playerController.updateController();
        }
    }

    public static void switchToHotbarSlot(Class clazz, boolean silent) {
        int slot = findHotbarBlock(clazz);
        if (slot > -1) {
            switchToHotbarSlot(slot, silent);
        }
    }

    public static boolean isNull(ItemStack stack) {
        return stack == null || stack.getItem() instanceof ItemAir;
    }

    public static int findHotbarBlock(Class clazz) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = Minecraft.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.field_190927_a) {
                continue;
            }

            if (clazz.isInstance(stack.getItem())) {
                return i;
            }

            if (stack.getItem() instanceof ItemBlock) {
                Block block = ((ItemBlock) stack.getItem()).getBlock();
                if (clazz.isInstance(block)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int findHotbarBlock(Block blockIn) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = Minecraft.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.field_190927_a) {
                continue;
            }

            if (stack.getItem() instanceof ItemBlock) {
                Block block = ((ItemBlock) stack.getItem()).getBlock();
                if (block == blockIn) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int getItemHotbar(Item input) {
        for (int i = 0; i < 9; i++) {
            final Item item = Minecraft.player.inventory.getStackInSlot(i).getItem();
            if (Item.getIdFromItem(item) == Item.getIdFromItem(input)) {
                return i;
            }
        }
        return -1;
    }

    public static int findStackInventory(Item input) {
        return findStackInventory(input, false);
    }

    public static int findStackInventory(Item input, boolean withHotbar) {
        for (int i = withHotbar ? 0 : 9; i < 36; i++) {
            final Item item = Minecraft.player.inventory.getStackInSlot(i).getItem();
            if (Item.getIdFromItem(input) == Item.getIdFromItem(item)) {
                return i + (i < 9 ? 36 : 0);
            }
        }
        return -1;
    }

    public static int findItemInventorySlot(Item item, boolean offHand) {
        AtomicInteger slot = new AtomicInteger();
        slot.set(-1);
        for (Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
            if (entry.getValue().getItem() == item) {
                if (entry.getKey() == 45 && !offHand) {
                    continue;
                }
                slot.set(entry.getKey());
                return slot.get();
            }
        }
        return slot.get();
    }


    public static int findInventoryBlock(Class clazz, boolean offHand) {
        AtomicInteger slot = new AtomicInteger();
        slot.set(-1);
        for (Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
            if (isBlock(entry.getValue().getItem(), clazz)) {
                if (entry.getKey() == 45 && !offHand) {
                    continue;
                }
                slot.set(entry.getKey());
                return slot.get();
            }
        }
        return slot.get();
    }

    public static boolean isBlock(Item item, Class clazz) {
        if (item instanceof ItemBlock) {
            Block block = ((ItemBlock) item).getBlock();
            return clazz.isInstance(block);
        }
        return false;
    }

    public static void confirmSlot(int slot) {
        Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(slot));
        Minecraft.player.inventory.currentItem = slot;
        mc.playerController.updateController();
    }

    public static Map<Integer, ItemStack> getInventoryAndHotbarSlots() {
        return getInventorySlots(9, 44);
    }

    private static Map<Integer, ItemStack> getInventorySlots(int currentI, int last) {
        int current = currentI;
        Map<Integer, ItemStack> fullInventorySlots = new HashMap<>();
        while (current <= last) {
            fullInventorySlots.put(current, Minecraft.player.inventoryContainer.getInventory().get(current));
            current++;
        }
        return fullInventorySlots;
    }

    public static boolean holdingItem(Class clazz) {
        boolean result = false;
        ItemStack stack = Minecraft.player.getHeldItemMainhand();
        result = isInstanceOf(stack, clazz);
        if (!result) {
            ItemStack offhand = Minecraft.player.getHeldItemOffhand();
            result = isInstanceOf(stack, clazz);
        }

        return result;
    }

    public static boolean isInstanceOf(ItemStack stack, Class clazz) {
        if (stack == null) {
            return false;
        }

        Item item = stack.getItem();
        if (clazz.isInstance(item)) {
            return true;
        }

        if (item instanceof ItemBlock) {
            Block block = Block.getBlockFromItem(item);
            return clazz.isInstance(block);
        }

        return false;
    }

    public static int convertHotbarToInv(int input) {
        return 45 - 9 + input;
    }


    public enum Switch {
        NORMAL,
        SILENT,
        NONE
    }

    public static class Task {

        private final int slot;
        private final boolean update;
        private final boolean quickClick;

        public Task() {
            this.update = true;
            this.slot = -1;
            this.quickClick = false;
        }

        public Task(int slot) {
            this.slot = slot;
            this.quickClick = false;
            this.update = false;
        }

        public Task(int slot, boolean quickClick) {
            this.slot = slot;
            this.quickClick = quickClick;
            this.update = false;
        }

        public void run() {
            if (this.update) {
                mc.playerController.updateController();
            }

            if (slot != -1) {
                mc.playerController.windowClick(0, this.slot, 0, this.quickClick ? ClickType.QUICK_MOVE : ClickType.PICKUP, Minecraft.player);
            }
        }

        public boolean isSwitching() {
            return !this.update;
        }
    }


}
