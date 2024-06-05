package zamorozka.modules.TRAFFIC;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.PlayerUtilis;

public class WallSpeed extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("WallSpeedBoost", this, 0.3, 0.1, 2, true));
	}

	public WallSpeed() {
		super("WallSpeed", Keyboard.KEY_NONE, Category.TRAFFIC);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (mc.player.isMoving() && this.mc.player.isCollidedHorizontally) {
			MovementUtilis.setMotion(Zamorozka.settingsManager.getSettingByName("WallSpeedBoost").getValDouble());
		}
	}
}