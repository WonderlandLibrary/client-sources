package com.alan.clients.module.impl.player;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.player.InventoryUtil;
import com.alan.clients.util.player.ItemUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(aliases = {"module.player.manager.name", "Manager"}, description = "module.player.manager.description", category = Category.PLAYER)
public final class NewManager extends Module {

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        Container inventory = mc.thePlayer.inventoryContainer;
        if (mc.currentScreen instanceof GuiInventory && mc.thePlayer.openContainer == inventory) {
            int bestSword = -1;
            int bestBow = -1;

            List<Integer> trash = new ArrayList<>();

            int[] bestTools = new int[3];
            int[] bestArmors = new int[4];

            Arrays.fill(bestArmors, -1);
            Arrays.fill(bestTools, -1);

            int i;
            for (i = InventoryUtil.ARMOR_BEGIN; i < InventoryUtil.END; i++) {
                ItemStack stack = inventory.getSlot(i).getStack();

                if (stack != null && stack.getItem() != null) {
                    if (stack.getItem() instanceof ItemSword) {
                        if (ItemUtil.isBestSword(stack, inventory)) {
                            bestSword = i;
                        }
                    } else if (stack.getItem() instanceof ItemBow) {
                        if (ItemUtil.isBestBow(stack, inventory)) {
                            bestBow = i;
                        }
                    } else if (stack.getItem() instanceof ItemTool tool) {
                        int toolType = ItemUtil.getToolType(tool);
                        if (ItemUtil.isBestTool(stack, inventory, toolType)) {
                            bestTools[toolType] = i;
                        }
                    } else if (stack.getItem() instanceof ItemArmor armor) {
                        if (ItemUtil.isBestArmor(stack, inventory, armor.armorType)) {
                            bestArmors[armor.armorType] = i;
                        }
                    }
                    if (!ItemUtil.isGoodItem(stack, inventory)) {
                        trash.add(i);
                    }
                }
            }

            boolean passed = true; // TODO:: add delay
            for (i = 0; i < bestArmors.length; i++) {
                int bestArmor = bestArmors[i];
                int armorSlot = i + 5;
                if (bestArmor != -1 && bestArmor != armorSlot && inventory.getSlot(armorSlot).getStack() == null && passed) {
                    InventoryUtil.windowClick(mc, inventory.windowId, bestArmor, 0, InventoryUtil.ClickType.QUICK_MOVE);
                    return;
                }
            }

            for (int trashSlot : trash) {
                if (passed) {
                    InventoryUtil.windowClick(mc, inventory.windowId, trashSlot, 1, InventoryUtil.ClickType.THROW);
                    return;
                }
            }

            int currentSlot = 36;

            if (bestSword != -1) {
                if (bestSword != currentSlot && passed) {
                    InventoryUtil.windowClick(mc, inventory.windowId, bestSword, currentSlot - 36, InventoryUtil.ClickType.SWAP);
                    return;
                }
                currentSlot++;
            }

            if (bestBow != -1) {
                if (bestBow != currentSlot && passed) {
                    InventoryUtil.windowClick(mc, inventory.windowId, bestBow, currentSlot - 36, InventoryUtil.ClickType.SWAP);
                    return;
                }
                currentSlot++;
            }

            for (int bestTool : bestTools) {
                if (bestTool != -1) {
                    if (bestTool != currentSlot && passed) {
                        InventoryUtil.windowClick(mc, inventory.windowId, bestTool, currentSlot - 36, InventoryUtil.ClickType.SWAP);
                        return;
                    }

                    currentSlot++;
                }
            }
        }
    };
}
