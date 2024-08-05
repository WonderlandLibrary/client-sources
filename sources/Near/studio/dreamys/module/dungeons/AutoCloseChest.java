package studio.dreamys.module.dungeons;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import studio.dreamys.libSkyblock.util.ServerUtils;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;
import studio.dreamys.util.PlayerUtils;

public class AutoCloseChest extends Module {
    public AutoCloseChest() {
        super("AutoCloseChest", Category.DUNGEONS);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiScreenEvent.InitGuiEvent e) {
        if (!ServerUtils.isInDungeons()) return;
        if (e.gui instanceof GuiChest) {
            GuiChest gui = (GuiChest) e.gui;
            Container containerChest = gui.inventorySlots;
            if (containerChest instanceof ContainerChest) {
                String displayName = ((ContainerChest) containerChest).getLowerChestInventory().getDisplayName().getUnformattedText().trim();
//                PlayerUtils.addMessage(displayName);
                if (displayName.equals("Chest")) {
                    PlayerUtils.player.closeScreen();
                }
            }
        }
    }
}
