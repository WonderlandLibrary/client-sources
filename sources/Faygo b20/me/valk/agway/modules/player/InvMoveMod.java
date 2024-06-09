package me.valk.agway.modules.player;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.valk.event.EventListener;
import me.valk.event.events.player.EventMotion;
import me.valk.help.entity.Player;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.client.gui.GuiChat;

public class InvMoveMod extends Module {

	public InvMoveMod() {
		super(new ModData("InvMove", Keyboard.KEY_NONE, new Color(40, 255, 10)), ModType.PLAYER);
	}

	@EventListener
	public void onMotion(EventMotion event) {
		if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiChat)) {
			if (Keyboard.isKeyDown(200)) {
				Player thePlayer = this.mc.thePlayer;
				thePlayer.rotationPitch -= 2.0f;
			}
			if (Keyboard.isKeyDown(208)) {
				Player thePlayer2 = this.mc.thePlayer;
				thePlayer2.rotationPitch += 2.0f;
			}
			if (Keyboard.isKeyDown(203)) {
				Player thePlayer3 = this.mc.thePlayer;
				thePlayer3.rotationYaw -= 3.0f;
			}
			if (Keyboard.isKeyDown(205)) {
				Player thePlayer4 = this.mc.thePlayer;
				thePlayer4.rotationYaw += 3.0f;
			}
			this.mc.gameSettings.keyBindForward.pressed = Keyboard
					.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode());
			this.mc.gameSettings.keyBindBack.pressed = Keyboard
					.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode());
			this.mc.gameSettings.keyBindLeft.pressed = Keyboard
					.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode());
			this.mc.gameSettings.keyBindRight.pressed = Keyboard
					.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode());
			this.mc.gameSettings.keyBindJump.pressed = Keyboard
					.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode());
		}
	}
}
