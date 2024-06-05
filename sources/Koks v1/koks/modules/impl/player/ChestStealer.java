package koks.modules.impl.player;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.RandomUtil;
import koks.utilities.TimeUtil;
import koks.utilities.value.values.NumberValue;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 01:24
 */
public class ChestStealer extends Module {

    public NumberValue<Long> startDelay = new NumberValue<>("Start Delay", 100L, 500L, 0L, this);
    public NumberValue<Long> takeDelay = new NumberValue<>("Take Delay", 90L, 125L, 150L, 0L, this);
    private final RandomUtil randomUtil = new RandomUtil();
    private final TimeUtil startTimer = new TimeUtil();
    private final TimeUtil throwTimer = new TimeUtil();
    private InventoryManager inventoryManager;

    public ChestStealer() {
        super("ChestStealer", "You steal the items from a chest", Category.PLAYER);

        addValue(startDelay);
        addValue(takeDelay);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(takeDelay.getMinDefaultValue() + ", " + takeDelay.getDefaultValue());
            if (mc.currentScreen instanceof GuiChest) {
                if (!startTimer.hasReached(startDelay.getDefaultValue()))
                    return;
                ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
                boolean empty = true;
                for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                    if (chest.getSlot(i).getHasStack() && chest.getSlot(i).getStack() != null && !inventoryManager.trashItems.contains(chest.getSlot(i).getStack().getItem())) {
                        if (throwTimer.hasReached(randomUtil.randomLong(takeDelay.getMinDefaultValue(), takeDelay.getDefaultValue()))) {
                            mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
                            throwTimer.reset();
                        }
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    mc.thePlayer.closeScreen();
                }
            } else {
                startTimer.reset();
            }
        }
    }

    @Override
    public void onEnable() {
        inventoryManager = Koks.getKoks().moduleManager.getModule(InventoryManager.class);
    }

    @Override
    public void onDisable() {

    }

}