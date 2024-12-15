package com.alan.clients.util.player;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;

import java.util.stream.IntStream;

public class InventoryUtil {
    public static final int ARMOR_BEGIN = 5;
    public static final int BEGIN = 9;
    public static final int END = 45;
    public static final int HOT_BAR_BEGIN = 36;

    private InventoryUtil() {
    }

    public static boolean isFull(Container container) {
        return IntStream.range(BEGIN, END).allMatch(i -> container.getSlot(i).getHasStack());
    }

    public static void windowClick(Minecraft mc, int windowId, int slotId, int mouseButtonClicked, ClickType type) {
        mc.playerController.windowClick(windowId, slotId, mouseButtonClicked, type.ordinal(), mc.thePlayer);
    }

    public enum ClickType {
        PICKUP, QUICK_MOVE, SWAP, CLONE, THROW, QUICK_CRAFT, PICKUP_ALL
    }
}
