package zamorozka.modules.WORLD;

import de.Hero.settings.Setting;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import zamorozka.event.EventTarget;
import zamorozka.event.events.DamageBlockEvent;
import zamorozka.event.events.EventBlockBreaking;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class FastBreak extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("CurDamageCheck", this, 0.5, 0.01, 1, true));
		Zamorozka.settingsManager.rSetting(new Setting("CurDamageValue", this, 1, 1, 50, true));
	}

	public FastBreak() {
		super("FastBreak", 0, Category.WORLD);
	}

	@EventTarget
	public void onPreUdpate(EventPreMotionUpdates event) {
		mc.playerController.blockHitDelay = 0;
		if (mc.playerController.curBlockDamageMP >= Zamorozka.settingsManager.getSettingByName("CurDamageCheck")
				.getValDouble()) {
			mc.playerController.curBlockDamageMP = (float) Zamorozka.settingsManager.getSettingByName("CurDamageValue")
					.getValDouble();
		}
	}
}