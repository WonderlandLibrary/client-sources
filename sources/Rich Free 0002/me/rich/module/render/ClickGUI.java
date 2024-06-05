package me.rich.module.render;

import org.lwjgl.input.Keyboard;

import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class ClickGUI extends Feature {

	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
	}
	   public void onEnable() {
		      super.onEnable();
		      mc.displayGuiScreen(Main.instance.clickGui1);
		      mc.gameSettings.guiScale = 2;
		        NotificationPublisher.queue(getName(), "Was enabled.", NotificationType.INFO);
		      this.toggle();
		   }
}
