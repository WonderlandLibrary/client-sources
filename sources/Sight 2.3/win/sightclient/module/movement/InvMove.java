package win.sightclient.module.movement;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import win.sightclient.event.Event;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.ui.clickgui.ClickGui;

public class InvMove extends Module {

	public InvMove() {
		super("InvMove", Category.PLAYER);
	}

	@Override
	public void onEvent(Event e) {
		if (mc.thePlayer == null || mc.theWorld == null || mc.currentScreen == null) {
			return;
		}
		
		if (mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChest
				|| mc.currentScreen instanceof GuiFurnace || mc.currentScreen instanceof ClickGui) {
			int forward = mc.gameSettings.keyBindForward.getKeyCode();
			int back = mc.gameSettings.keyBindBack.getKeyCode();
			int right = mc.gameSettings.keyBindRight.getKeyCode();
			int left = mc.gameSettings.keyBindLeft.getKeyCode();
			int jump = mc.gameSettings.keyBindJump.getKeyCode();
			int sprint = mc.gameSettings.keyBindSprint.getKeyCode();
			
			KeyBinding.setKeyBindState(forward, Keyboard.isKeyDown(forward));
			KeyBinding.setKeyBindState(back, Keyboard.isKeyDown(back));
			KeyBinding.setKeyBindState(right, Keyboard.isKeyDown(right));
			KeyBinding.setKeyBindState(left, Keyboard.isKeyDown(left));
			KeyBinding.setKeyBindState(jump, Keyboard.isKeyDown(jump));
			KeyBinding.setKeyBindState(sprint, Keyboard.isKeyDown(sprint));
		}
	}
}
