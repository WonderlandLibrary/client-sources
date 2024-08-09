package wtf.shiyeno.util.world;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import wtf.shiyeno.events.impl.player.EventWindowClick;
import wtf.shiyeno.util.IMinecraft;

public class InventoryUtil
        implements IMinecraft {
    public static int getHotBarSlot(Item input) {
        for (int i = 0; i < 9; ++i) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() != input) continue;
            return i;
        }
        return -1;
    }

    public static int getFireWorks() {
        for (int i = 0; i < 9; ++i) {
            if (!(InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() instanceof FireworkRocketItem)) continue;
            return i;
        }
        return -1;
    }

    public static int getTrident() {
        for (int i = 0; i < 9; ++i) {
            if (!(InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() instanceof TridentItem)) continue;
            return i;
        }
        return -1;
    }

    public static int getItem(Item item, boolean hotbar) {
        for (int i = 0; i < (hotbar ? 9 : 45); ++i) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() != item) continue;
            return i;
        }
        return -1;
    }

    public static int getSlotInHotBar(Item item) {
        for (int i = 0; i < 9; ++i) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() != item) continue;
            return i;
        }
        return -1;
    }

    public static boolean doesHotbarHaveItem(Item item) {
        for (int i = 0; i < 9; ++i) {
            InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() != item) continue;
            return true;
        }
        return false;
    }

    public static void inventorySwapClick(Item item, boolean b) {
        int i;
        if (InventoryHelper.getItemIndex(item) == -1) {
            return;
        }
        if (InventoryUtil.doesHotbarHaveItem(item)) {
            for (i = 0; i < 9; ++i) {
                if (InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() != item) continue;
                boolean propusk = false;
                if (i != InventoryUtil.mc.player.inventory.currentItem) {
                    InventoryUtil.mc.player.connection.sendPacket(new CHeldItemChangePacket(i));
                    propusk = true;
                }
                InventoryUtil.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                if (!propusk) break;
                InventoryUtil.mc.player.connection.sendPacket(new CHeldItemChangePacket(InventoryUtil.mc.player.inventory.currentItem));
                break;
            }
        }
        if (!InventoryUtil.doesHotbarHaveItem(item)) {
            for (i = 0; i < 36; ++i) {
                if (InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() != item) continue;
                InventoryUtil.mc.playerController.windowClick(0, i, InventoryUtil.mc.player.inventory.currentItem % 8 + 1, ClickType.SWAP, InventoryUtil.mc.player);
                InventoryUtil.mc.player.connection.sendPacket(new CHeldItemChangePacket(InventoryUtil.mc.player.inventory.currentItem % 8 + 1));
                InventoryUtil.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                InventoryUtil.mc.player.connection.sendPacket(new CHeldItemChangePacket(InventoryUtil.mc.player.inventory.currentItem));
                InventoryUtil.mc.playerController.windowClick(0, i, InventoryUtil.mc.player.inventory.currentItem % 8 + 1, ClickType.SWAP, InventoryUtil.mc.player);
                break;
            }
        }
    }

    public static int getItemSlot(Item input) {
        for (ItemStack stack : InventoryUtil.mc.player.getArmorInventoryList()) {
            if (stack.getItem() != input) continue;
            return -2;
        }
        int slot = -1;
        for (int i = 0; i < 36; ++i) {
            ItemStack s = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (s.getItem() != input) continue;
            slot = i;
            break;
        }
        if (slot < 9 && slot != -1) {
            slot += 36;
        }
        return slot;
    }

    public static int getItemSlot(ItemStack input) {
        for (ItemStack stack : InventoryUtil.mc.player.getArmorInventoryList()) {
            if (stack != input) continue;
            return -2;
        }
        int slot = -1;
        for (int i = 0; i < 36; ++i) {
            ItemStack s = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (s != input) continue;
            slot = i;
            break;
        }
        if (slot < 9 && slot != -1) {
            slot += 36;
        }
        return slot;
    }

    public static void handleItemTransfer() {
        int emptySlot;
        if (InventoryUtil.mc.player.inventory.getItemStack().getItem() != Items.AIR && (emptySlot = InventoryUtil.findEmptySlot(false)) != -1) {
            InventoryUtil.mc.playerController.windowClick(0, emptySlot, 0, ClickType.PICKUP, InventoryUtil.mc.player);
        }
        InventoryUtil.mc.player.closeScreen();
    }

    public static void handleClick(EventWindowClick windowClick) {
        boolean isSneaking = InventoryUtil.mc.player.isSneaking();
        if (windowClick.getClickStage() == EventWindowClick.ClickStage.PRE) {
            InventoryUtil.mc.player.setSprinting(false);
            if (!isSneaking) {
                return;
            }
            InventoryUtil.mc.player.connection.sendPacket(new CEntityActionPacket(InventoryUtil.mc.player, CEntityActionPacket.Action.RELEASE_SHIFT_KEY));
        }
        if (windowClick.getClickStage() == EventWindowClick.ClickStage.POST) {
            if (!isSneaking) {
                return;
            }
            InventoryUtil.mc.player.connection.sendPacket(new CEntityActionPacket(InventoryUtil.mc.player, CEntityActionPacket.Action.PRESS_SHIFT_KEY));
        }
    }

    public static int findEmptySlot(boolean isStartingFromZero) {
        int start = isStartingFromZero ? 0 : 9;
        int end = isStartingFromZero ? 9 : 45;
        for (int i = start; i < end; ++i) {
            if (!InventoryUtil.mc.player.inventory.getStackInSlot(i).isEmpty()) continue;
            return i;
        }
        return -1;
    }

    public static void moveItem(int from, int to, boolean air) {
        if (from == to) {
            return;
        }
        InventoryUtil.pickupItem(from, 0);
        InventoryUtil.pickupItem(to, 0);
        if (air) {
            InventoryUtil.pickupItem(from, 0);
        }
    }

    public static void pickupItem(int slot, int button) {
        InventoryUtil.mc.playerController.windowClick(0, slot, button, ClickType.PICKUP, InventoryUtil.mc.player);
    }

    public static void dropItem(int slot) {
        InventoryUtil.mc.playerController.windowClick(0, slot, 0, ClickType.THROW, InventoryUtil.mc.player);
    }

    public static int getAxe(boolean hotBar) {
        int startSlot = hotBar ? 0 : 9;
        int endSlot = hotBar ? 9 : 36;
        for (int i = startSlot; i < endSlot; ++i) {
            ItemStack itemStack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (!(itemStack.getItem() instanceof AxeItem)) continue;
            return i;
        }
        return -1;
    }

    public static int getPearls() {
        for (int i = 0; i < 9; ++i) {
            if (!(InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem() instanceof EnderPearlItem)) continue;
            return i;
        }
        return -1;
    }
}