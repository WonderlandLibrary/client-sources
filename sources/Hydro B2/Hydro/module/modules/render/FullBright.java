package Hydro.module.modules.render;

import java.util.ArrayList;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FullBright extends Module {

	public FullBright() {
		super("FullBright", 0, true, Category.RENDER, "Makes everything bright");
		ArrayList<String> options = new ArrayList<>();
		options.add("Gamma");
		options.add("NightVision");
		Client.instance.settingsManager.rSetting(new Setting("FullBrightMode", "Mode", this, "Gamma", options));
	}
	
	@Override
	public void onUpdate() {
		if(Client.instance.settingsManager.getSettingByName("FullBrightMode").getValString().equals("Gamma")) {
			mc.gameSettings.gammaSetting = 100;
		}else {
			mc.gameSettings.gammaSetting = 1;
		}
		
		if(Client.instance.settingsManager.getSettingByName("FullBrightMode").getValString().equals("NightVision")) {
			mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 999999999));
		}else {
			mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
		}
	}
	
	@Override
	public void onDisable() {
		if(Client.instance.settingsManager.getSettingByName("FullBrightMode").getValString().equals("Gamma")) {
			mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
		}
		
		mc.gameSettings.gammaSetting = 1;
	}

}
