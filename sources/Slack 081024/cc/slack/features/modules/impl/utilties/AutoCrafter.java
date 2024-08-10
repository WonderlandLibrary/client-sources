package cc.slack.features.modules.impl.utilties;

import cc.slack.events.impl.game.TickEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.other.PrintUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;

@ModuleInfo(
        name = "AutoCrafter",
        category = Category.UTILITIES
)
public class AutoCrafter extends Module {

    private final ModeValue<String> crafterMode = new ModeValue<>("Crafter", new String[]{"Boat", "Furnace"});

    public AutoCrafter() {
        addSettings(crafterMode);
    }

    @Override
    public void onEnable() {
        PrintUtil.message("Please, click on the crafting table.");
    }

    @SuppressWarnings("unused")
    @Listen
    public void onTick(TickEvent event) {
        if (!(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiCrafting)) {
            return;
        }

        int windowId = mc.thePlayer.openContainer.windowId;

        switch (crafterMode.getValue()) {
            case "Boat":
                handleBoatCrafting(windowId);
                break;
            case "Furnace":
                handleFurnaceCrafting(windowId);
                break;
        }
    }

    private void handleBoatCrafting(int windowId) {
        for (Slot inventorySlot : mc.thePlayer.inventoryContainer.inventorySlots) {
            if (isValidStack(inventorySlot, Blocks.log, 6)) {
                mc.playerController.windowClick(windowId, inventorySlot.slotNumber + 1, 0, 0, mc.thePlayer);
                mc.playerController.windowClick(windowId, 1, 0, 0, mc.thePlayer);
                mc.playerController.windowClick(windowId, 0, 0, 1, mc.thePlayer);
                return;
            }
        }

        for (Slot inventorySlot : mc.thePlayer.inventoryContainer.inventorySlots) {
            if (isValidStack(inventorySlot, Blocks.planks, 6)) {
                clickBoatSlots(windowId, inventorySlot);
                return;
            }
        }
    }

    private void handleFurnaceCrafting(int windowId) {
        for (Slot inventorySlot : mc.thePlayer.inventoryContainer.inventorySlots) {
            if (isValidStack(inventorySlot, Blocks.cobblestone, 8)) {
                clickFurnaceSlots(windowId, inventorySlot);
                return;
            }
        }
    }

    private void clickBoatSlots(int windowId, Slot inventorySlot) {
        int[] slots = {4, 7, 8, 9, 6};
        mc.playerController.windowClick(windowId, inventorySlot.slotNumber + 1, 0, 0, mc.thePlayer);
        for (int slot : slots) {
            mc.playerController.windowClick(windowId, slot, 1, 0, mc.thePlayer);
        }
        mc.playerController.windowClick(windowId, inventorySlot.slotNumber + 1, 0, 0, mc.thePlayer);
        mc.playerController.windowClick(windowId, 0, 0, 1, mc.thePlayer);
        mc.thePlayer.closeScreen();
    }

    private void clickFurnaceSlots(int windowId, Slot inventorySlot) {
        int[] slots = {1, 2, 3, 4, 6, 7, 8, 9};
        mc.playerController.windowClick(windowId, inventorySlot.slotNumber, 0, 0, mc.thePlayer);
        for (int slot : slots) {
            mc.playerController.windowClick(windowId, slot, 1, 0, mc.thePlayer);
            mc.playerController.windowClick(windowId, inventorySlot.slotNumber, 0, 0, mc.thePlayer);
        }
        mc.playerController.windowClick(windowId, 0, 0, 1, mc.thePlayer);
        mc.thePlayer.closeScreen();
    }

    private boolean isValidStack(Slot inventorySlot, Block block, int requiredStackSize) {
        return inventorySlot.getHasStack() &&
                inventorySlot.getStack().stackSize >= requiredStackSize &&
                inventorySlot.getStack().getItem() instanceof ItemBlock &&
                ((ItemBlock) inventorySlot.getStack().getItem()).getBlock() == block;
    }

}
