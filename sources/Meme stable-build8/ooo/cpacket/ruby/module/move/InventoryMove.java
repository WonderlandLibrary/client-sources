package ooo.cpacket.ruby.module.move;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.module.Module;

public class InventoryMove extends Module {

	public InventoryMove(String name, int key, Category category) {
		super(name, key, category);
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		
	}
	
	@EventImpl
	public void onUpdate(EventMotionUpdate e) {
		if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
			KeyBinding despacito = mc.gameSettings.keyBindForward;
			if (Keyboard.isKeyDown(despacito.getKeyCode())){
				despacito.pressed = true;
			}
			else {
				despacito.pressed = false;
			}
			despacito = mc.gameSettings.keyBindBack;
			if (Keyboard.isKeyDown(despacito.getKeyCode())){
				despacito.pressed = true;
			}
			else {
				despacito.pressed = false;
			}
			despacito = mc.gameSettings.keyBindLeft;
			if (Keyboard.isKeyDown(despacito.getKeyCode())){
				despacito.pressed = true;
			}
			else {
				despacito.pressed = false;
			}
			despacito = mc.gameSettings.keyBindRight;
			if (Keyboard.isKeyDown(despacito.getKeyCode())){
				despacito.pressed = true;
			}
			else {
				despacito.pressed = false;
			}
			despacito = mc.gameSettings.keyBindJump;
			if (Keyboard.isKeyDown(despacito.getKeyCode())){
				despacito.pressed = true;
			}
			else {
				despacito.pressed = false;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				mc.thePlayer.rotationYaw -= 3;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				mc.thePlayer.rotationYaw += 3;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				mc.thePlayer.rotationPitch += 3;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				mc.thePlayer.rotationPitch -= 3;
			}
		}
	}
	
	
}
