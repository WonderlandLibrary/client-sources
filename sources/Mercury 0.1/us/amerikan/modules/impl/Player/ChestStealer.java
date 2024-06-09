/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Player;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import us.amerikan.amerikan;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class ChestStealer
extends Module {
    private int delay;
    private int start;

    public ChestStealer() {
        super("ChestStealer", "ChestStealer", 0, Category.PLAYER);
        amerikan.setmgr.rSetting(new Setting("ChestStealer Delay", this, 30.0, 5.0, 100.0, true));
        amerikan.setmgr.rSetting(new Setting("Instant", this, false));
        amerikan.setmgr.rSetting(new Setting("AutoClose", this, true));
        amerikan.setmgr.rSetting(new Setting("ChestStealer StartDelay", this, true));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (amerikan.setmgr.getSettingByName("Instant").getValBoolean()) {
            this.setAddon("Instant");
        } else {
            this.setAddon("ignore");
        }
        ++this.start;
        if (!(Minecraft.thePlayer.openContainer instanceof ContainerChest)) {
            this.start = 0;
        }
        if (Minecraft.thePlayer.openContainer != null) {
            if (Minecraft.thePlayer.openContainer instanceof ContainerChest) {
                if (amerikan.setmgr.getSettingByName("ChestStealer StartDelay").getValBoolean() && this.start <= 5) {
                    return;
                }
                ContainerChest c2 = (ContainerChest)Minecraft.thePlayer.openContainer;
                boolean empty = true;
                for (int i2 = 0; i2 < c2.getLowerChestInventory().getSizeInventory(); ++i2) {
                    if (c2.getLowerChestInventory().getStackInSlot(i2) == null || c2.getLowerChestInventory().getStackInSlot(i2).hasDisplayName()) continue;
                    empty = false;
                    if (!amerikan.setmgr.getSettingByName("Instant").getValBoolean() && !((double)this.delay > amerikan.setmgr.getSettingByName("ChestStealer Delay").getValDouble() / 20.0)) continue;
                    ChestStealer.mc.playerController.windowClick(c2.windowId, i2, 0, 1, Minecraft.thePlayer);
                    this.delay = 0;
                }
                if (empty) {
                    Minecraft.thePlayer.closeScreen();
                }
                ++this.delay;
            }
        }
    }

    @Override
    public void onEnable() {
        this.delay = 0;
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
}

