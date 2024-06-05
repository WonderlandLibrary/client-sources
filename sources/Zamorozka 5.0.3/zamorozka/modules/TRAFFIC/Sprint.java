package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.KillAura;
import zamorozka.modules.WORLD.Scaffold;
import zamorozka.ui.MoveUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.MovementUtils;

public class Sprint extends Module {

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Default");
		options.add("Multi-Dir");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Sprint Mode", this, "Default", options));
		Zamorozka.settingsManager.rSetting(new Setting("Multi-Dir Speed", this, 0.14, 0.13, 0.30, true));
	}

	public Sprint() {
		super("Sprint", 0, Category.TRAFFIC);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (!(ModuleManager.getModule(Scaffold.class).getState() && Zamorozka.settingsManager.getSettingByName("SprintOFF").getValBoolean())) {
			if (!(ModuleManager.getModule(KillAura.class).getState() && Zamorozka.settingsManager.getSettingByName("SprintDisable").getValBoolean() && KillAura.target != null)) {
				String mode = Zamorozka.instance.settingsManager.getSettingByName("Sprint Mode").getValString();
				String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
				this.setDisplayName("Sprint §f§" + " " + modeput);
				if (mode.equalsIgnoreCase("Default")) {
					mc.player.setSprinting(mc.player.isMoving());
				}
				if (mode.equalsIgnoreCase("Multi-Dir")) {
					double mot = Zamorozka.settingsManager.getSettingByName("Multi-Dir Speed").getValDouble();
					mc.player.setSprinting(mc.player.isMoving());
					if ((mc.gameSettings.keyBindBack.pressed) || (mc.gameSettings.keyBindLeft.pressed) || (mc.gameSettings.keyBindRight.pressed)) {
						if (mc.player.isSneaking() || mc.player.isInWater() || mc.player.isInLava()) {
							MovementUtilis.setMotion(0.08);
						} else if (mc.player.onGround) {
							MovementUtilis.setMotion(mot);
						}
					}
				}
			}
		}
	}
}