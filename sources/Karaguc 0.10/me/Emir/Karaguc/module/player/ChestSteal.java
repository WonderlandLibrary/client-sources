package me.Emir.Karaguc.module.player;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import me.Emir.Karaguc.utils.KaragucTimer;
import net.minecraft.inventory.ContainerChest;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ChestSteal extends Module {
	
    private KaragucTimer timer = new KaragucTimer();

    public ChestSteal() {
        super("ChestStealer", Keyboard.KEY_O, Category.PLAYER);
    }

    public void setup() {
        ArrayList<String> modes = new ArrayList<String>();
        modes.add("RedstoneBlock");

        Karaguc.instance.settingsManager.rSetting(new Setting("Bypass", this, true));
        Karaguc.instance.settingsManager.rSetting(new Setting("ChestAura", this, true));
        Karaguc.instance.settingsManager.rSetting(new Setting("StealDelay", this, 4, 0, 50, true));
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest)mc.thePlayer.openContainer;
            for(int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if(chest.getLowerChestInventory().getStackInSlot(i) != null && timer.hasTimerReached(110L)) {
                    mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
                    timer.reset();
                }
                if(chest.getInventory().isEmpty())
                    mc.thePlayer.closeScreen();
            }
        }
    }
}
