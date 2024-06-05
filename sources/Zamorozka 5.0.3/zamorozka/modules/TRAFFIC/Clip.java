package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class Clip extends Module {

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("ForwardClip");
		options.add("BackwardClick");
		options.add("VClip");
		options.add("YClip");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Clip Mode", this, "VClip", options));
		Zamorozka.instance.settingsManager.rSetting(new Setting("ClipPower", this, 10, 0, 300, true));
	}

	public Clip() {
		super("Clip", 0, Category.TRAFFIC);
	}

	@Override
	public void onEnable() {
		double x = mc.player.posX;
		double y = mc.player.posY;
		double z = mc.player.posZ;
		double yaw = mc.player.rotationYaw * 0.017453292;
		String mode = Zamorozka.settingsManager.getSettingByName("Clip Mode").getValString();
		double power = Zamorozka.settingsManager.getSettingByName("ClipPower").getValDouble();
		if (mode.equalsIgnoreCase("VClip")) {
			mc.player.setPosition(x, y + power, z);
		}
		if (mode.equalsIgnoreCase("YClip")) {
			mc.player.setPosition(x, y - power, z);
		}
		if (mode.equalsIgnoreCase("ForwardClip")) {
			mc.player.setPosition(x - Math.sin(yaw) * power, y, z + Math.cos(yaw) * power);
		}
		if (mode.equalsIgnoreCase("BackwardClick")) {
			mc.player.setPosition(x + Math.sin(yaw) * power, y, z - Math.cos(yaw) * power);
		}
		super.onEnable();
	}

}