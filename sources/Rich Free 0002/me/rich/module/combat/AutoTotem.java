package me.rich.module.combat;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

public class AutoTotem extends Feature {
    public Setting counttotem;
    public Setting checkcrystal;

    public AutoTotem() {
        super("AutoTotem", 0, Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("TotemHealth", this, 4, 0, 20, true));
        Main.settingsManager.rSetting(new Setting("TotemDelay", this, 0, 0, 5000, true));
    }

    public int fountTotemCount() {
        int count = 0;
        int i = 0;
        while (true) {
            if (i >= mc.player.inventory.getSizeInventory())
                break;
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.field_190929_cY) {
                ++count;
            }
            ++i;
        }
        return count;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        float delay = (float) Main.settingsManager.getSettingByName("TotemDelay").getValDouble();
        if (timerHelper.check(delay)) {
            if (this.checkCrystal() || mc.player
                    .getHealth() <= (float) Main.settingsManager.getSettingByName("TotemHealth").getValDouble()
                    && this.mc.player.getHeldItemOffhand().getItem() != Items.field_190929_cY && this.totem() != -1
                    && (this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen == null)) {
                mc.playerController.windowClick(0, this.totem(), 1, ClickType.PICKUP, this.mc.player);
                mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, this.mc.player);
                timerHelper.reset();
            }
        }
    }

    private boolean checkCrystal() {
        for (Entity entity : mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderCrystal))
                continue;
            if (!(mc.player.getDistanceToEntity(entity) <= 6.0f))
                continue;
            return true;
        }
        return false;
    }

    public int totem() {
        for (int i = 0; i < 45; ++i) {
            final ItemStack itemStack = this.mc.player.inventoryContainer.getSlot(i).getStack();
            if (itemStack.getItem() == Items.field_190929_cY) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}