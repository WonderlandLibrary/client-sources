package de.dietrichpaul.clientbase.feature.engine.inventory.impl;

import de.dietrichpaul.clientbase.feature.engine.inventory.InventoryHandler;
import de.dietrichpaul.clientbase.feature.hack.combat.AutoArmorHack;
import de.dietrichpaul.clientbase.util.minecraft.InventoryUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.MathHelper;

public class AutoArmorHandler extends InventoryHandler {

    private AutoArmorHack parent;

    public AutoArmorHandler(AutoArmorHack parent) {
        super(parent);
        this.parent = parent;
    }

    @Override
    public void handle(int syncId, Inventory container) {
        while (!isClickingBlocked()) {
            PlayerInventory playerInventory = mc.player.getInventory();
            InventoryUtil.BestArmor bestArmor = new InventoryUtil.BestArmor();
            InventoryUtil.getBestArmor(bestArmor, playerInventory);

            int wechselSlot = -1;
            double wechselClosest = Double.MAX_VALUE;
            ItemStack wechselType = null;
            for (int i = 0; i < 4; i++) {
                if (!playerInventory.getArmorStack(i).isEmpty()) continue;

                Slot slot = bestArmor.getSlots()[i];
                if (slot == null || (slot.inventory == playerInventory && slot.getIndex() <= 8 && slot.getIndex() > 4))
                    continue;

                int x = slot.getIndex() % 9;
                int y = MathHelper.floor(slot.getIndex() / 9.0);
                double dist = Math.hypot(engine.getLastSlotX() - x, engine.getLastSlotY() - y);
                if (dist < wechselClosest) {
                    wechselClosest = dist;
                    wechselSlot = slot.getIndex();
                    wechselType = bestArmor.getStacks()[i];
                }
            }

            if (wechselSlot != -1) {
                click(syncId, wechselSlot, 0, SlotActionType.QUICK_MOVE);
                continue;
            }

            int wegwerfSlot = -1;
            double wegwerfClosest = Double.MAX_VALUE;
            for (int type = 0; type < 4; type++) {
                ItemStack stack = playerInventory.getArmorStack(type);
                if (stack.getItem() instanceof ArmorItem item) {
                    ItemStack betterStack = bestArmor.getStacks()[item.getSlotType().getEntitySlotId()];
                    if (betterStack != stack) {
                        int slot = 8 - type;
                        int x = slot % 9;
                        int y = MathHelper.floor(slot / 9.0);
                        double dist = Math.hypot(engine.getLastSlotX() - x, engine.getLastSlotY() - y);
                        if (dist < wegwerfClosest) {
                            wegwerfClosest = dist;
                            wegwerfSlot = slot;
                        }
                    }
                }
            }
            if (wegwerfSlot == -1) {
                for (int i = 0; i < 36; i++) {
                    ItemStack stack = container.getStack(i);
                    if (!stack.isEmpty() && stack.getItem() instanceof ArmorItem item) {
                        ItemStack betterStack = bestArmor.getStacks()[item.getSlotType().getEntitySlotId()];
                        if (betterStack != stack) {
                            int slot = hotbarFix(i);
                            int x = slot % 9;
                            int y = MathHelper.floor(slot / 9.0);
                            double dist = Math.hypot(engine.getLastSlotX() - x, engine.getLastSlotY() - y);
                            if (dist < wegwerfClosest) {
                                wegwerfClosest = dist;
                                wegwerfSlot = slot;
                            }
                        }
                    }
                }
            }

            if (wegwerfSlot != -1) click(syncId, wegwerfSlot, 0, SlotActionType.THROW);
            else break;
        }
    }

    @Override
    public boolean isContainer(PlayerInventory inventory, Inventory container) {
        return inventory == container;
    }

    @Override
    public boolean isToggled() {
        return parent.isToggled();
    }
}
