/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.player;

import mpp.venusfr.events.EventPacket;
import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.server.SHeldItemChangePacket;

public class InventoryUtil
implements IMinecraft {
    private static InventoryUtil instance = new InventoryUtil();

    public static int findEmptySlot(boolean bl) {
        int n = bl ? 0 : 9;
        int n2 = bl ? 9 : 45;
        for (int i = n; i < n2; ++i) {
            if (!InventoryUtil.mc.player.inventory.getStackInSlot(i).isEmpty()) continue;
            return i;
        }
        return 1;
    }

    public static void moveItem(int n, int n2, boolean bl) {
        if (n == n2) {
            return;
        }
        InventoryUtil.pickupItem(n, 0);
        InventoryUtil.pickupItem(n2, 0);
        if (bl) {
            InventoryUtil.pickupItem(n, 0);
        }
    }

    public static void moveItemTime(int n, int n2, boolean bl, int n3) {
        if (n == n2) {
            return;
        }
        InventoryUtil.pickupItem(n, 0, n3);
        InventoryUtil.pickupItem(n2, 0, n3);
        if (bl) {
            InventoryUtil.pickupItem(n, 0, n3);
        }
    }

    public static void moveItem(int n, int n2) {
        if (n == n2) {
            return;
        }
        InventoryUtil.pickupItem(n, 0);
        InventoryUtil.pickupItem(n2, 0);
        InventoryUtil.pickupItem(n, 0);
    }

    public static void pickupItem(int n, int n2) {
        InventoryUtil.mc.playerController.windowClick(0, n, n2, ClickType.PICKUP, InventoryUtil.mc.player);
    }

    public static void pickupItem(int n, int n2, int n3) {
        InventoryUtil.mc.playerController.windowClickFixed(0, n, n2, ClickType.PICKUP, InventoryUtil.mc.player, n3);
    }

    public int getAxeInInventory(boolean bl) {
        int n = bl ? 0 : 9;
        int n2 = bl ? 9 : 36;
        for (int i = n; i < n2; ++i) {
            if (!(InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() instanceof AxeItem)) continue;
            return i;
        }
        return 1;
    }

    public int findBestSlotInHotBar() {
        int n = this.findEmptySlot();
        if (n != -1) {
            return n;
        }
        return this.findNonSwordSlot();
    }

    private int findEmptySlot() {
        for (int i = 0; i < 9; ++i) {
            if (!InventoryUtil.mc.player.inventory.getStackInSlot(i).isEmpty() || InventoryUtil.mc.player.inventory.currentItem == i) continue;
            return i;
        }
        return 1;
    }

    private int findNonSwordSlot() {
        for (int i = 0; i < 9; ++i) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() instanceof SwordItem || InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() instanceof ElytraItem || InventoryUtil.mc.player.inventory.currentItem == i) continue;
            return i;
        }
        return 1;
    }

    public int getSlotInInventory(Item item) {
        int n = -1;
        for (int i = 0; i < 36; ++i) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() != item) continue;
            n = i;
        }
        return n;
    }

    public int getSlotInInventoryOrHotbar(Item item, boolean bl) {
        int n = bl ? 0 : 9;
        int n2 = bl ? 9 : 36;
        int n3 = -1;
        for (int i = n; i < n2; ++i) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() != item) continue;
            n3 = i;
        }
        return n3;
    }

    public static int getSlotInInventoryOrHotbar() {
        int n = 0;
        int n2 = 9;
        int n3 = -1;
        for (int i = n; i < n2; ++i) {
            if (!(Block.getBlockFromItem(InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem()) instanceof SlabBlock)) continue;
            n3 = i;
        }
        return n3;
    }

    public static InventoryUtil getInstance() {
        return instance;
    }

    public static class Hand {
        public static boolean isEnabled;
        private boolean isChangingItem;
        private int originalSlot = -1;

        public void onEventPacket(EventPacket eventPacket) {
            if (!eventPacket.isReceive()) {
                return;
            }
            if (eventPacket.getPacket() instanceof SHeldItemChangePacket) {
                this.isChangingItem = true;
            }
        }

        public void handleItemChange(boolean bl) {
            if (this.isChangingItem && this.originalSlot != -1) {
                isEnabled = true;
                IMinecraft.mc.player.inventory.currentItem = this.originalSlot;
                if (bl) {
                    this.isChangingItem = false;
                    this.originalSlot = -1;
                    isEnabled = false;
                }
            }
        }

        public void setOriginalSlot(int n) {
            this.originalSlot = n;
        }
    }
}

