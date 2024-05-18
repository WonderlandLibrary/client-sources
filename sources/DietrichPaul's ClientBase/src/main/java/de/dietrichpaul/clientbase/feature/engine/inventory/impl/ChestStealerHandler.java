package de.dietrichpaul.clientbase.feature.engine.inventory.impl;

import de.dietrichpaul.clientbase.feature.engine.inventory.InventoryHandler;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.property.impl.IntProperty;
import de.dietrichpaul.clientbase.util.minecraft.InventoryUtil;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.MathHelper;

public class ChestStealerHandler extends InventoryHandler {

    private final Hack parent;

    private final IntProperty closeDelayProperty = new IntProperty("CloseDelay", 0, -1, 20);
    private int closeDelay;

    public ChestStealerHandler(Hack hack) {
        super(hack);
        hack.addProperty(closeDelayProperty);
        this.parent = hack;
    }

    public int getNextSlot(int syncId, Inventory container) {
        InventoryUtil.BestArmor bestArmor = new InventoryUtil.BestArmor();
        InventoryUtil.getBestArmor(bestArmor, mc.player.getInventory());
        InventoryUtil.getBestArmor(bestArmor, container);

        int closest = -1;
        double closestDist = Double.MAX_VALUE;
        for (int i = 0; i < container.size(); i++) {
            ItemStack stack = container.getStack(i);
            if (stack.getItem() instanceof ArmorItem item && stack != bestArmor.getStacks()[item.getSlotType().getEntitySlotId()])
                continue;

            if (!stack.isEmpty()) {
                int x = i % 9;
                int y = MathHelper.floor(i / 9.0);
                if (engine.getLastSyncId() != syncId) {
                    closest = i;
                    break;
                }

                double dist = Math.hypot(x - engine.getLastSlotX(), y - engine.getLastSlotY());
                if (dist < closestDist) {
                    closestDist = dist;
                    closest = i;
                }
            }
        }

        return closest;
    }

    @Override
    public void handle(int syncId, Inventory container) {
        while (!isClickingBlocked()) {
            int closest = getNextSlot(syncId, container);

            if (closest != -1) {
                click(syncId, closest, 0, SlotActionType.QUICK_MOVE);
                closeDelay = 0;
            } else break;
        }
        int closest = getNextSlot(syncId, container);
        if (closest == -1 && closeDelayProperty.getValue() >= 0) {
            closeDelay++;
            if (closeDelay > closeDelayProperty.getValue()) {
                mc.currentScreen.close();
            }
        } else {
            closeDelay = 0;
        }
    }

    @Override
    public boolean isContainer(PlayerInventory inventory, Inventory container) {
        return container instanceof SimpleInventory;
    }

    @Override
    public boolean isToggled() {
        return parent.isToggled();
    }
}
