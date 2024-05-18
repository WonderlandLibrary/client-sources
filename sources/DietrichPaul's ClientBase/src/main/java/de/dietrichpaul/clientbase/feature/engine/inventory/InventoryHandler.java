package de.dietrichpaul.clientbase.feature.engine.inventory;

import de.dietrichpaul.clientbase.property.PropertyGroup;
import de.dietrichpaul.clientbase.property.impl.IntProperty;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.SlotActionType;

public abstract class InventoryHandler {

    protected MinecraftClient mc = MinecraftClient.getInstance();

    private final IntProperty delay = new IntProperty("Delay", 10, 0, 20);
    private final IntProperty openDelay = new IntProperty("OpenDelay", 0, 0, 20);

    private boolean blockClicking;

    protected InventoryEngine engine;

    public InventoryHandler(PropertyGroup propertyGroup) {
        propertyGroup.addProperty(delay);
        propertyGroup.addProperty(openDelay);
    }

    public abstract void handle(int syncId, Inventory container);

    protected int hotbarFix(int slot) {
        return slot < 9 ? slot + 36 : slot;
    }

    public void click(int syncId, int slotId, int clickData, SlotActionType actionType) {
        if (blockClicking)
            return;
        mc.interactionManager.clickSlot(syncId, slotId, clickData, actionType, mc.player);
        engine.click(getDelay() - 1);
        if (getDelay() != 0) {
            blockClicking = true;
        }
    }

    public boolean isClickingBlocked() {
        return blockClicking;
    }

    public void unblockClicking() {
        blockClicking = false;
    }

    public abstract boolean isContainer(PlayerInventory inventory, Inventory container);

    public abstract boolean isToggled();

    public int getPriority() {
        return 0;
    }

    public int getDelay() {
        return delay.getValue();
    }

    public int getOpenDelay() {
        return openDelay.getValue();
    }
}
