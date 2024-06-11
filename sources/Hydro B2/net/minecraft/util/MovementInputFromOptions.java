package net.minecraft.util;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput {
	private final GameSettings gameSettings;

	public MovementInputFromOptions(GameSettings gameSettingsIn) {
		this.gameSettings = gameSettingsIn;
	}

	public void updatePlayerMoveState() {
		if (Client.instance.moduleManager.getModuleByName("InvMove").isEnabled()
				&& (!(Minecraft.getMinecraft().currentScreen instanceof GuiChat))) {
			moveStrafe = 0.0f;
			moveForward = 0.0f;
			if (Keyboard.isKeyDown(this.gameSettings.keyBindForward.getKeyCode())) {
				++moveForward;
			}
			if (Keyboard.isKeyDown(this.gameSettings.keyBindBack.getKeyCode())) {
				--moveForward;
			}
			if (Keyboard.isKeyDown(this.gameSettings.keyBindLeft.getKeyCode())) {
				++moveStrafe;
			}
			if (Keyboard.isKeyDown(this.gameSettings.keyBindRight.getKeyCode())) {
				--moveStrafe;
			}
			jump = Keyboard.isKeyDown(this.gameSettings.keyBindJump.getKeyCode());
			sneak = this.gameSettings.keyBindSneak.isKeyDown();
			if (sneak) {
				moveStrafe *= 0.3;
				moveForward *= 0.3;
			}
		} else {
			moveStrafe = 0.0f;
			moveForward = 0.0f;
			if (this.gameSettings.keyBindForward.isKeyDown()) {
				++moveForward;
			}
			if (this.gameSettings.keyBindBack.isKeyDown()) {
				--moveForward;
			}
			if (this.gameSettings.keyBindLeft.isKeyDown()) {
				++moveStrafe;
			}
			if (this.gameSettings.keyBindRight.isKeyDown()) {
				--moveStrafe;
			}
			jump = this.gameSettings.keyBindJump.isKeyDown();
			sneak = this.gameSettings.keyBindSneak.isKeyDown();
			if (sneak) {
				moveStrafe *= 0.3;
				moveForward *= 0.3;
			}
		}
	}
}
