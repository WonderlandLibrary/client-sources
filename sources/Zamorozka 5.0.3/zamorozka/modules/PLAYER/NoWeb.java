package zamorozka.modules.PLAYER;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventMove;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MovementUtilis;

public class NoWeb extends Module {

	public NoWeb() {
		super("NoWeb", 0, Category.PLAYER);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("Matrix");
		options.add("NCP");
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoWeb Mode", this, "Vanilla", options));
	}

	@EventTarget
	public void onUpdate(EventMove event) {
		if (Keyboard.isKeyDown(42)) {
			mc.gameSettings.keyBindSneak.pressed = true;
		} else {
			mc.gameSettings.keyBindSneak.pressed = false;
			String mode = Zamorozka.instance.settingsManager.getSettingByName("NoWeb Mode").getValString();
			String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
			this.setDisplayName("NoWeb §f§" + " " + modeput);
			if (mode.equalsIgnoreCase("Vanilla")) {
				mc.player.isInWeb = false;
			} else if (mode.equalsIgnoreCase("NCP")) {
				if (mc.player.isInWeb) {
					if (mc.player.onGround) {
						mc.gameSettings.keyBindSneak.pressed = false;
						mc.player.isInWeb = true;
						if ((mc.gameSettings.keyBindForward.pressed) || (mc.gameSettings.keyBindLeft.pressed)
								|| (mc.gameSettings.keyBindRight.pressed))
							ClientUtils.setSpeed(0.27600166893005D);
					} else {
						ClientUtils.setSpeed(0D);
						mc.player.isInWeb = false;
					}
				}
			}
			if (mode.equalsIgnoreCase("Matrix")) {
				if (mc.player.onGround && mc.player.isInWeb) {
					mc.player.isInWeb = true;
				} else {
					if (mc.gameSettings.keyBindJump.isKeyDown())
						return;
					mc.player.isInWeb = false;
				}
				if (mc.player.isInWeb && !mc.gameSettings.keyBindSneak.isKeyDown()) {
					MovementUtilis.setMotion(event, 0.483);
				}
			}
		}
	}
}