package zamorozka.modules.TRAFFIC;

import de.Hero.settings.Setting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class WaterLeave extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("YBoost", this, 15, 1, 30, true));
		Zamorozka.settingsManager.rSetting(new Setting("AutoStuck", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("StuckTicks", this, 100, 50, 500, true));
	}
	
	public WaterLeave() {
		super("WaterLeave", 0, Category.TRAFFIC);
	}
	
	@EventTarget
	public void onPre(EventPreMotionUpdates event) {
		if(mc.player.motionY < 0 && mc.player.isInLiquid2()) {
			mc.player.motionY = Zamorozka.settingsManager.getSettingByName("YBoost").getValDouble();
		}
		if(!Zamorozka.settingsManager.getSettingByName("AutoStuck").getValBoolean())
			return;
    	int tks = (int) Zamorozka.settingsManager.getSettingByName("StuckTicks").getValDouble();
		if(mc.player.ticksExisted % tks == 0 && !mc.player.isInLiquid2()) {
			mc.player.isDead = true;
		}
	}
	
	@Override
	public void onDisable() {
		mc.player.isDead = false;
		super.onDisable();
	}
}