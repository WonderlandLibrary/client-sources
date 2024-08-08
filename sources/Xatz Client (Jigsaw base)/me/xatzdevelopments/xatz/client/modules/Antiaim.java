package me.xatzdevelopments.xatz.client.modules;

import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import me.xatzdevelopments.xatz.client.events.EventUpdate;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;
import me.xatzdevelopments.xatz.utils.Rotation.RotationUtils;

public class Antiaim extends Module {
	
	SliderSetting<Number> yawOffset = new SliderSetting<Number>("Flight Speed", ClientSettings.ayawOffset, -180, -180, 180, ValueFormat.DECIMAL);
	SliderSetting<Number> pitch = new SliderSetting<Number>("Flight Speed", ClientSettings.apitch, -75, -90, 90, ValueFormat.DECIMAL);
	
	public Antiaim() {
		super("Antiaim", Keyboard.KEY_NONE, Category.COMBAT, "Prevents you from drowning in SinglePlayer only!");
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {

		super.onEnable();
	}

	public void onUpdate(EventUpdate eu) {
		if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
			return;
		}
		int yaw = (int) -ClientSettings.ayawOffset;
		int pitch = (int) -ClientSettings.apitch;
		
		float setYaw = eu.getYaw();
		if (currentMode.equals("Normal")) {
			setYaw = yaw;
		} else if (currentMode.equals("Jitter")) {
			setYaw = (float) (mc.thePlayer.rotationYaw - (yaw + ThreadLocalRandom.current().nextDouble(-10, 10)));
		} else if (currentMode.equals("Spinx")) {
			setYaw = (float) (eu.getYaw() + ThreadLocalRandom.current().nextDouble(50, 80));
		}
		
		float setPitch = eu.getPitch();
		if (currentMode.equals("Normal")) {
			setPitch = pitch;
		} else if (currentMode.equals("Jitter")) {
			setPitch = (float) Math.min(Math.max(pitch + ThreadLocalRandom.current().nextDouble(-7.5, 7.5), -90), 90);
		}
		
		eu.setYaw(RotationUtils.roundTo360(setYaw));
		eu.setPitch(setPitch);
	}
	@Override
	public String[] getModes() {
		return new String[] {"Jitter","Spin", "Normal"};
	}

	@Override
	public String getAddonText() {
		return currentMode;
	}

	public String getModeName() {
		return "Mode: ";
	}

}