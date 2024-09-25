package eze.modules.player;

import eze.modules.*;
import eze.util.*;
import eze.settings.*;
import eze.events.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class Cheststealer extends Module
{
    Timer timer;
    public NumberSetting delay;
    public static boolean enabled;
    
    public Cheststealer() {
        super("ChestStealer", 0, Category.PLAYER);
        this.timer = new Timer();
        this.delay = new NumberSetting("Delay", 100.0, 0.0, 1000.0, 50.0);
        this.addSettings(this.delay);
    }
    
    @Override
    public void onEnable() {
        Cheststealer.enabled = true;
        if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel) {
                this.delay.setValue(200.0);
            }
            if (AutoSetting.isMineplex) {
                this.delay.setValue(50.0);
            }
            if (AutoSetting.isOldVerus) {
                this.delay.setValue(200.0);
            }
            if (AutoSetting.isRedesky) {
                this.delay.setValue(50.0);
            }
        }
    }
    
    public void OnDisable() {
        Cheststealer.enabled = false;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel) {
                this.delay.setValue(200.0);
            }
            if (AutoSetting.isMineplex) {
                this.delay.setValue(50.0);
            }
            if (AutoSetting.isOldVerus) {
                this.delay.setValue(200.0);
            }
            if (AutoSetting.isRedesky) {
                this.delay.setValue(50.0);
            }
        }
        if (this.mc.thePlayer != null && this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
            final ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;
            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); ++i) {
                if (chest.getLowerChestInventory().getStackInSlot(i) != null && this.timer.hasTimeElapsed((long)this.delay.getValue(), true)) {
                    this.mc.playerController.windowClick(chest.windowId, i, 0, 1, this.mc.thePlayer);
                }
            }
        }
    }
}
