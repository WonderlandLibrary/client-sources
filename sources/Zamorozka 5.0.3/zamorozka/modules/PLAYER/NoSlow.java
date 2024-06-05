package zamorozka.modules.PLAYER;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ClientUtils;

public class NoSlow extends Module {

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("MatrixOLD");
		options.add("MatrixNewSneak");
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoSlowdown Mode", this, "Vanilla", options));
		Zamorozka.settingsManager.rSetting(new Setting("CustomSpeed", this, 70, 10, 100, true));
		Zamorozka.settingsManager.rSetting(new Setting("NoSprint", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("StopActiveHand", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("AutoJumping", this, false));
	}

	public NoSlow() {
		super("NoSlowdown", 0, Category.PLAYER);
	}

	@EventTarget
	public void onUpd(EventUpdate event) {
		double spd = Zamorozka.settingsManager.getSettingByName("CustomSpeed").getValDouble();
		String mode = Zamorozka.instance.settingsManager.getSettingByName("NoSlowdown Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		if (!mode.equalsIgnoreCase("MatrixNewSneak")) {
			this.setDisplayName("NoSlowdown §f§" + " " + modeput + ", " + (int) spd + "%");
		} else {
			this.setDisplayName("NoSlowdown §f§" + " " + "MatrixNewSneak" + ", " + (int) spd + "%");
		}
		if (mode.equalsIgnoreCase("MatrixOLD") && mc.player.ticksExisted % 5 == 0 && mc.player.fallDistance == 0 && mc.gameSettings.keyBindUseItem.pressed) {
			if (mc.player.isEating() || mc.player.isBlocking() || mc.player.isBowing() || mc.player.isDrinking()) {
				if (!mc.gameSettings.keyBindJump.pressed) {
					mc.player.motionY = 0;
				}
			}
		}

		if (mode.equalsIgnoreCase("MatrixNewSneak")) {
			if (!mc.player.isMoving())
				return;
			if (mc.player.isEating() || mc.player.isBlocking() || mc.player.isBowing() || mc.player.isDrinking()) {
				if (mc.player.ticksExisted % 2 == 0) {
					mc.gameSettings.keyBindSneak.pressed = true;
				} else {
					mc.gameSettings.keyBindSneak.pressed = false;
				}
			}
			if (!(mc.player.isEating() || mc.player.isBlocking() || mc.player.isBowing() || mc.player.isDrinking())) {
				if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
					mc.gameSettings.keyBindSneak.pressed = false;
				}
			}
		}

		if (Zamorozka.settingsManager.getSettingByName("NoSprint").getValBoolean()) {
			if (mc.player.isEating() || mc.player.isBlocking() || mc.player.isBowing() || mc.player.isDrinking()) {
				mc.player.setSprinting(false);
				mc.gameSettings.keyBindSprint.pressed = false;
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("AutoJumping").getValBoolean()) {
			if (mc.player.isEating() || mc.player.isBlocking() || mc.player.isBowing() || mc.player.isDrinking()) {
				if (!mc.player.onGround)
					return;
				mc.player.jump();
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("StopActiveHand").getValBoolean()) {
			// by vk.com/uweak
			if (!(mc.player.isEating() || mc.player.isDrinking()))
				return;
			for (int i = 0; i < 50; i++) {
				mc.player.stopActiveHand();
			}
		}

	}
}