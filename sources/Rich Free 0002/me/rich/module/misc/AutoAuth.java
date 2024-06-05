package me.rich.module.misc;

import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventChatMessage;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.Minecraft;

public class AutoAuth extends Feature{
	  public AutoAuth() {
	        super("AutoAuth", 0,Category.MISC);
}
	  @EventTarget
	    public void onReceiveChat(EventChatMessage event) {
	        if (event.getMessage().contains("/reg") || event.getMessage().contains("/register")) {
	            mc.player.sendChatMessage("/reg qwerty" + Minecraft.getSystemTime() % 1000L + " qwerty" + Minecraft.getSystemTime() % 1000L);
	            Main.msg("ur pass: qwerty" + Minecraft.getSystemTime() % 1000L, true);
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
