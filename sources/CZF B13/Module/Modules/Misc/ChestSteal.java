package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventTick;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class ChestSteal extends Module {
    private final Numbers<Double> delay = new Numbers<Double>("Delay", "Delay", 30.0, 0.0, 1000.0, 10.0);
    private final TimerUtil timer = new TimerUtil();

    public ChestSteal() {
        super("ChestSteal", new String[]{"cheststeal", "chests", "stealer"}, ModuleType.Player);
        this.addValues(this.delay);
        this.setColor(new Color(218, 97, 127).getRGB());
    }

    @EventHandler
    private void onUpdate(EventTick event) {
        if (mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;
            int i = 0;
            while (i < container.getLowerChestInventory().getSizeInventory()) {
                if (container.getLowerChestInventory().getStackInSlot(i) != null
                        && this.timer.hasReached(this.delay.getValue())) {
                    Minecraft.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
                    this.timer.reset();
                }
                ++i;
            }
            if (this.isEmpty()) {
                mc.thePlayer.closeScreen();
            }
        }
    }

    private boolean isEmpty() {
        if (mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;
            int i = 0;
            while (i < container.getLowerChestInventory().getSizeInventory()) {
                ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(i);
                if (itemStack != null && itemStack.getItem() != null) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }
}
