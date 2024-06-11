package Hydro.module.modules.render;

import java.util.ArrayList;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.ChatUtils;
import net.minecraft.util.EnumChatFormatting;

public class Weather extends Module {

	public Weather() {
		super("Weather", 0, true, Category.RENDER, "Changes the weather client side");
		ArrayList<String> options = new ArrayList<>();
		options.add("Clear");
		options.add("Rain");
		options.add("ThunderStorm");
		Client.instance.settingsManager.rSetting(new Setting("WeatherMode", "Mode", this, "Clear", options));
	}

	@Override
	public void onEnable() {
		ChatUtils.sendMessageToPlayer(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "WARNING" + EnumChatFormatting.RESET + "" + EnumChatFormatting.BLACK + ": " + EnumChatFormatting.WHITE + "This module can cause flashing!");
	}

	@Override
	public void onUpdate() {
		
		if(Client.instance.settingsManager.getSettingByName("WeatherMode").getValString().equals("Clear")) {
			mc.theWorld.setThunderStrength(0);
			mc.theWorld.setRainStrength(0);
		}
		
		if(Client.instance.settingsManager.getSettingByName("WeatherMode").getValString().equals("Rain")) {
			mc.theWorld.setThunderStrength(0);
			mc.theWorld.setRainStrength(1);
		}
		
		if(Client.instance.settingsManager.getSettingByName("WeatherMode").getValString().equals("ThunderStorm")) {
			mc.theWorld.setThunderStrength(1);
			mc.theWorld.setRainStrength(1);
		}
		
	}

}
