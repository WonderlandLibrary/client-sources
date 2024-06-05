package zamorozka.modules.COMBAT;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.TRAFFIC.Jesus;
import zamorozka.ui.MouseUtilis;

public class AutoGap extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("HealthEat", this, 12, 1, 20, true));
	}

	public AutoGap() {
		super("AutoGApple", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		if (mc.player.getHealth() <= (float) Zamorozka.settingsManager.getSettingByName("HealthEat").getValDouble() && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
			mc.gameSettings.keyBindUseItem.pressed = true;
		} else {
			if (!(mc.player.isDrinking() || mc.player.isBowing() || mc.player.isBlocking() || mc.player.isEating())) {
				if (mc.player.getHealth() >= (float) Zamorozka.settingsManager.getSettingByName("HealthEat").getValDouble()) {
					mc.gameSettings.keyBindUseItem.pressed = false;
				}
			}
		}
	}

	@Override
	public void onDisable() {
		mc.gameSettings.keyBindUseItem.pressed = false;
		super.onDisable();

	}
}