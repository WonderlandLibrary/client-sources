package io.github.nevalackin.radium.module.impl.other;

import io.github.nevalackin.radium.event.impl.packet.PacketReceiveEvent;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.Representation;
import io.github.nevalackin.radium.utils.InventoryUtils;
import io.github.nevalackin.radium.utils.TimerUtil;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.network.play.server.S2DPacketOpenWindow;

@ModuleInfo(label = "Chest Stealer", category = ModuleCategory.OTHER)
public final class ChestStealer extends Module {

    private final DoubleProperty clickDelayProperty = new DoubleProperty("Click Delay", 150, 10,
            500, 10, Representation.MILLISECONDS);
    private final DoubleProperty closeDelayProperty = new DoubleProperty("Close Delay", 150, 10,
            500, 10, Representation.MILLISECONDS);
//    TODO: Chest Aura
//    private final Property<Boolean> auraProperty = new Property<>("Aura", false);

    private final TimerUtil timer = new TimerUtil();

    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S2DPacketOpenWindow) {
            timer.reset();
        }
    }

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent e) {
        if (e.isPre()) {
            if (Wrapper.getCurrentScreen() instanceof GuiChest) {
                GuiChest chest = (GuiChest) Wrapper.getCurrentScreen();
                if (chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Chest")) {
                    if (isInventoryFull() || isChestEmpty(chest)) {
                        if (timer.hasElapsed(closeDelayProperty.getValue().longValue()))
                            Wrapper.getPlayer().closeScreen();
                        return;
                    }

                    for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                        if (timer.hasElapsed(clickDelayProperty.getValue().longValue())) {
                            if (InventoryUtils.isValid(chest.getLowerChestInventory().getStackInSlot(i))) {
                                InventoryUtils.windowClick(
                                        chest.inventorySlots.windowId, i, 0, InventoryUtils.ClickType.SHIFT_CLICK);
                                timer.reset();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isChestEmpty(GuiChest chest) {
        for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
            if (InventoryUtils.isValid(chest.getLowerChestInventory().getStackInSlot(i)))
                return false;
        }

        return true;
    }

    private boolean isInventoryFull() {
        for (int i = 9; i < 45; i++) {
            if (!Wrapper.getPlayer().inventoryContainer.getSlot(i).getHasStack())
                return false;
        }
        return true;
    }

}
