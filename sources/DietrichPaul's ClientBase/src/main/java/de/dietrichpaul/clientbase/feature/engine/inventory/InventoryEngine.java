package de.dietrichpaul.clientbase.feature.engine.inventory;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.OpenScreenListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.MathHelper;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class InventoryEngine implements OpenScreenListener {

    private final List<InventoryHandler> handlers = new LinkedList<>();
    private MinecraftClient mc = MinecraftClient.getInstance();

    public InventoryEngine() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(OpenScreenListener.class, this);
    }

    private int delay;

    private int lastSyncId;
    private int lastSlotX;
    private int lastSlotY;

    public void add(InventoryHandler handler) {
        handler.engine = this;
        handlers.add(handler);
    }

    void click(int delay) {
        this.delay = delay;
    }

    public void clickOnSlot(int syncId, int slot) {
        lastSyncId = syncId;
        lastSlotX = slot % 9;
        lastSlotY = MathHelper.floor(slot / 9.0);
    }

    public int getDelay() {
        return delay;
    }

    public int getLastSlotX() {
        return lastSlotX;
    }

    public int getLastSlotY() {
        return lastSlotY;
    }

    public int getLastSyncId() {
        return lastSyncId;
    }

    public void onUpdate() {
        if (delay > 0) {
            delay--;
            return;
        }

        if (mc.player == null)
            return;

        int syncId = 0;
        final Inventory container;
        if (mc.currentScreen instanceof GenericContainerScreen generic) {
            container = generic.getScreenHandler().getInventory();
            syncId = generic.getScreenHandler().syncId;
        } else if (mc.currentScreen instanceof InventoryScreen inv) {
            container = mc.player.getInventory();
            syncId = mc.player.currentScreenHandler.syncId;
        } else {
            container = null;
            lastSyncId = 0;
        }
        InventoryHandler inventoryHandler = handlers.stream().filter(InventoryHandler::isToggled)
                .filter(e -> e.isContainer(mc.player.getInventory(), container))
                .max(Comparator.comparingInt(InventoryHandler::getPriority))
                .orElse(null);
        if (inventoryHandler != null) {
            inventoryHandler.unblockClicking();
            inventoryHandler.handle(syncId, container);
        }
    }

    @Override
    public void onOpenScreen(OpenScreenEvent event) {
        if (event.getScreen() instanceof HandledScreen<?> screen) {
            final Inventory container;
            if (screen instanceof GenericContainerScreen generic) {
                container = generic.getScreenHandler().getInventory();
            } else {
                container = null;
            }
            InventoryHandler inventoryHandler = handlers.stream().filter(InventoryHandler::isToggled)
                    .filter(e -> e.isContainer(mc.player.getInventory(), container))
                    .max(Comparator.comparingInt(InventoryHandler::getPriority))
                    .orElse(null);
            if (inventoryHandler != null)
                delay = inventoryHandler.getOpenDelay();
        }
    }
}
