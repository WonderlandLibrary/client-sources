package me.mees.remix.modules.player;

import me.satisfactory.base.utils.timer.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.events.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import pw.stamina.causam.scan.method.model.*;

public class ChestStealer extends Module
{
    TimerUtil timer2;
    
    public ChestStealer() {
        super("ChestStealer", 0, Category.PLAYER);
        this.timer2 = new TimerUtil();
        this.addSetting(new Setting("DelayChestStealer", this, 150.0, 0.0, 1000.0, true, 10.0));
        this.addSetting(new Setting("Auto Close", this, true));
    }
    
    private boolean containsStack(final Container t) {
        for (int slot = 0; slot < t.inventorySlots.size() - 36; ++slot) {
            final Slot s = t.getSlot(slot);
            if (s != null && s.getStack() != null && s.getHasStack()) {
                return true;
            }
        }
        return false;
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        if (ChestStealer.mc.thePlayer.openContainer != null && ChestStealer.mc.thePlayer.openContainer instanceof ContainerChest) {
            final ContainerChest container = (ContainerChest)ChestStealer.mc.thePlayer.openContainer;
            if (this.containsStack(container)) {
                for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
                    if (container.getLowerChestInventory().getStackInSlot(i) != null && this.timer2.hasTimeElapsed(this.getSettingByModule(this, "DelayChestStealer").doubleValue(), false)) {
                        ChestStealer.mc.playerController.windowClick(container.windowId, i, 0, 1, ChestStealer.mc.thePlayer);
                        this.timer2.reset();
                    }
                }
            }
            else if (this.getSettingByModule(this, "Auto Close").booleanValue()) {
                ChestStealer.mc.thePlayer.closeScreen();
            }
        }
    }
}
