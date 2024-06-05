package zamorozka.modules.POTION;

import com.sun.jna.platform.unix.X11.Font;

import de.Hero.settings.Setting;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class FlyPoin extends Module {
	public FlyPoin() {
		super("FlyPoin", 0, Category.POTION);
	}

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("PoinSpeed", this, 1, 0, 3, true));
		Zamorozka.settingsManager.rSetting(new Setting("YPoinSpeed", this, false));
	}

	public static int tick = 0;
	public static boolean helpBool = true;
	public static int times = 0;

	public void onUpdate() {
		if (this.getState()) {
			mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById(25), 9999999, 8));
		}
	}

	public void onDisable() {
		mc.player.removePotionEffect(Potion.getPotionById(25));
	}
}