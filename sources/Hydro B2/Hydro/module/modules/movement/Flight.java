package Hydro.module.modules.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.event.events.EventUpdate;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.ChatUtils;
import Hydro.util.MovementUtil;

public class Flight extends Module {

	public Flight() {
		super("Flight", Keyboard.KEY_G, true, Category.MOVEMENT, "Fly like a bird");
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("Velocity");
		options.add("Redesky");
		Client.instance.settingsManager.rSetting(new Setting("FlightMode", "Mode", this, "Vanilla", options));
	}
	
	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		mc.thePlayer.capabilities.isFlying = false;
		mc.timer.timerSpeed = 1f;
	}
	
	@Override
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(Client.instance.settingsManager.getSettingByName("FlightMode").getValString().equals("Vanilla")) {
				setDisplayName("Vanilla");
				mc.thePlayer.capabilities.isFlying = true;
			}
			
			if(Client.instance.settingsManager.getSettingByName("FlightMode").getValString().equals("Velocity")) {
				setDisplayName("Velocity");
				mc.thePlayer.capabilities.isFlying = true;
				MovementUtil.setSpeed(0.5);
			}

			if(Client.instance.settingsManager.getSettingByName("FlightMode").getValString().equals("Redesky")) {
				setDisplayName("Redesky");
				if (mc.thePlayer.onGround) {
					mc.thePlayer.capabilities.isFlying = true;
					mc.timer.timerSpeed = (float) 0.5;
					mc.thePlayer.motionY = 0.7;
				}else {
					mc.thePlayer.capabilities.isFlying = true;
					mc.timer.timerSpeed = (float) 0.5;
				}
			}
		}
	}
	
}
