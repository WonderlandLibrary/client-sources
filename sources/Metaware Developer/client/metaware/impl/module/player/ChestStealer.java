package client.metaware.impl.module.player;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.system.TimerUtil;
import client.metaware.impl.utils.util.other.InventoryUtils;
import client.metaware.impl.utils.util.other.PlayerUtil;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import org.lwjgl.input.Mouse;

@ModuleInfo(name = "ChestStealer", renderName = "ChestStealer", aliases = "Stealer", description = "Automatically take items from chest.", category = Category.PLAYER)
public class ChestStealer extends Module {

    private DoubleProperty clickDelayProperty = new DoubleProperty("Click Delay", 20, 1, 1000, 10, Property.Representation.MILLISECONDS);
    private DoubleProperty closeDelayProperty = new DoubleProperty("Close Delay", 20, 1, 1000, 10, Property.Representation.MILLISECONDS);
    private final Property<Boolean> smartProperty = new Property<>("Smart", true);
    private final Property<Boolean> archeryProperty = new Property<>("Archery", false);
    private final Property<Boolean> nameCheckProperty = new Property("Name Check", true);
    private final Property<Boolean> silentProperty = new Property("Silent", false);

    private TimerUtil timer = new TimerUtil();

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = event -> {
        if (event.isPre()) {
            if (mc.currentScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest) mc.currentScreen;
                IInventory lowerChestInv = chest.lowerChestInventory;
                String[] list = new String[]{"menu", "selector", "game", "gui", "server", "inventory", "play",
                        "teleporter", "shop", "melee", "armor", "block", "castle", "mini", "warp", "teleport", "user",
                        "team", "tool", "sure", "trade", "cancel", "accept", "soul", "book", "recipe", "profile", "tele",
                        "port", "map", "kit", "select", "lobby", "vault", "lock", "quick", "travel", "cake", "war", "pvp", "where"};

                String name = lowerChestInv.getDisplayName().getUnformattedText().toLowerCase();

                for(String badname : list){
                    if(name.contains(badname))
                        return;
                }

                if (lowerChestInv.getDisplayName().getUnformattedText().contains("Chest") || !nameCheckProperty.getValue()) {
                    if (!Mouse.isGrabbed() && silentProperty.getValue()) {
                        mc.inGameHasFocus = true;
                        mc.mouseHelper.grabMouseCursor();
                    }
                    if (PlayerUtil.isInventoryFull() || PlayerUtil.isInventoryEmpty(lowerChestInv, archeryProperty.getValue(), smartProperty.getValue())) {
                        if (timer.delay(closeDelayProperty.getValue().longValue()))
                            mc.thePlayer.closeScreen();
                        return;
                    }
                    for (int i = 0; i < lowerChestInv.getSizeInventory(); i++) {
                        if (timer.delay(clickDelayProperty.getValue().longValue())) {
                            if (PlayerUtil.isValid(lowerChestInv.getStackInSlot(i), archeryProperty.getValue(), smartProperty.getValue())) {
                                PlayerUtil.windowClick(chest.inventorySlots.windowId, i, 0, InventoryUtils.ClickType.SHIFT_CLICK);
                                timer.reset();
                                return;
                            }
                        }
                    }
                }
            }
        }
    };
}