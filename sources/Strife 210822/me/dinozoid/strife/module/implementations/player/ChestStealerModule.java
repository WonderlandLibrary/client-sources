package me.dinozoid.strife.module.implementations.player;

import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.system.TimerUtil;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

@ModuleInfo(name = "ChestStealer", renderName = "ChestStealer", aliases = "Stealer", description = "Automatically take items from chest.", category = Category.PLAYER)
public class ChestStealerModule extends Module {

    private DoubleProperty clickDelayProperty = new DoubleProperty("Click Delay", 20, 1, 1000, 10, Property.Representation.MILLISECONDS);
    private DoubleProperty closeDelayProperty = new DoubleProperty("Close Delay", 20, 1, 1000, 10, Property.Representation.MILLISECONDS);
    private final Property<Boolean> smartProperty = new Property<>("Smart", true);
    private final Property<Boolean> archeryProperty = new Property<>("Archery", false);
    private final Property<Boolean> nameCheckProperty = new Property("Name Check", true);
    private final Property<Boolean> silentProperty = new Property("Silent", false);

    private TimerUtil timer = new TimerUtil();

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
        if (event.state() == EventState.PRE) {
            if (mc.currentScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest) mc.currentScreen;
                IInventory lowerChestInv = chest.lowerChestInventory;
                if (lowerChestInv.getDisplayName().getUnformattedText().contains("Chest") || !nameCheckProperty.value()) {
                    if (!Mouse.isGrabbed() && silentProperty.value()) {
                        mc.inGameHasFocus = true;
                        mc.mouseHelper.grabMouseCursor();
                    }
                    if (PlayerUtil.isInventoryFull() || PlayerUtil.isInventoryEmpty(lowerChestInv, archeryProperty.value(), smartProperty.value())) {
                        if (timer.hasElapsed(closeDelayProperty.value().longValue()))
                            mc.thePlayer.closeScreen();
                        return;
                    }
                    for (int i = 0; i < lowerChestInv.getSizeInventory(); i++) {
                        if (timer.hasElapsed(clickDelayProperty.value().longValue())) {
                            if(PlayerUtil.isValid(lowerChestInv.getStackInSlot(i), archeryProperty.value(), smartProperty.value())) {
                                PlayerUtil.windowClick(chest.inventorySlots.windowId, i, 0, PlayerUtil.ClickType.SHIFT_CLICK);
                                timer.reset();
                                return;
                            }
                        }
                    }
                }
            }
        }
    });

}
