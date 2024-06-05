package me.rich.module.movement;

import org.lwjgl.input.Keyboard;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.network.play.client.CPacketPlayer;

public class GuiWalk extends Feature {

	public GuiWalk() {
		super("GuiWalk", Keyboard.KEY_NONE, Category.MOVEMENT);
	}
	@EventTarget
	public void onUpdate(EventUpdate event) {
	    if (!(mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)) {
	        mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
	        mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
	        mc.gameSettings.keyBindBack.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode());
	        mc.gameSettings.keyBindLeft.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
	        mc.gameSettings.keyBindRight.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
	        mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode());
	        mc.gameSettings.keyBindSprint.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode());
	    } 
	  }
	  
	  public void onDisable() {
			NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		    mc.gameSettings.keyBindJump.pressed = false;
		    mc.gameSettings.keyBindForward.pressed = false;
		    mc.gameSettings.keyBindBack.pressed = false;
		    mc.gameSettings.keyBindLeft.pressed = false;
		    mc.gameSettings.keyBindRight.pressed = false;
		    mc.gameSettings.keyBindSneak.pressed = false;
		    mc.gameSettings.keyBindSprint.pressed = false;
	    super.onDisable();
	  }
	    @Override
	    public void onEnable() {
	        super.onEnable();
	        NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	    }
}