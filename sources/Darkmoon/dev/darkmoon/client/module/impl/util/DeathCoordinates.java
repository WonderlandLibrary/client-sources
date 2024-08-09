package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.manager.notification.NotificationManager;
import dev.darkmoon.client.manager.notification.NotificationType;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.ChatUtility;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.util.text.TextFormatting;

@ModuleAnnotation(name = "DeathCoordinates", category = Category.UTIL)
public class DeathCoordinates extends Module {
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player.getHealth() < 1.0f && mc.currentScreen instanceof GuiGameOver) {
            int x = mc.player.getPosition().getX();
            int y = mc.player.getPosition().getY();
            int z = mc.player.getPosition().getZ();
            if (mc.player.deathTime < 1) {
                NotificationManager.notify(NotificationType.INFO, "Death Coordinates", "X: " + x + " Y: " + y + " Z: " + z, 10000);
                ChatUtility.addChatMessage(TextFormatting.GRAY + "Вы умерли на координатах: " + TextFormatting.GOLD + x + TextFormatting.GRAY + ", " + TextFormatting.GOLD + y + TextFormatting.GRAY + ", " + TextFormatting.GOLD + z + TextFormatting.RESET);
            }
        }
    }
}
