package me.rich.module.misc;

import me.rich.event.EventManager;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;

public class AutoRespawn extends Feature
{
    public AutoRespawn() {
        super("AutoRespawn", 0, Category.MISC);
    }
    
    @EventTarget
    public void onUpdate(EventUpdate event) {
    if (!this.isToggled()) {
            return;
        }
        if (mc.currentScreen instanceof GuiGameOver) {
            mc.player.respawnPlayer();
            mc.displayGuiScreen((GuiScreen)null);
        }
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