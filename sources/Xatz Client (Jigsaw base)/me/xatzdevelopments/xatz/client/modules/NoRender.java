package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.module.Module;

public class NoRender extends Module {

	static boolean EorD = mc.gameSettings.ofFireworkParticles;
	
	
	public NoRender() {
		super("NoRender", Keyboard.KEY_NONE, Category.RENDER, "Doesn't load in certain shit, can be used for lag prevention and more.");
	}



	@Override
	public void onDisable() {
		mc.gameSettings.ofFireworkParticles = EorD;
		super.onDisable();
	}

	@Override
	public void onEnable() {
		EorD = mc.gameSettings.ofFireworkParticles;
		super.onEnable();
	}

	@Override
	public void onUpdate() {
		if (ClientSettings.FireWork) {
		mc.gameSettings.ofFireworkParticles = false;
		}
		super.onUpdate();
	}
	@Override
	public ModSetting[] getModSettings() {
//		BasicSlider slider1 = new BasicSlider("Flight Speed", ClientSettings.FlightdefaultSpeed, 0, 10, 0,
//				ValueDisplay.DECIMAL);
//		SliderListener listener1 = new SliderListener() {
//
//			@Override
//			public void onSliderValueChanged(Slider slider) {
//				ClientSettings.FlightdefaultSpeed = slider.getValue();
//			}
//		};
//		slider1.addSliderListener(listener1);
//		final BasicCheckButton box1 = new BasicCheckButton("Default Smooth Flight");
//		box1.setSelected(ClientSettings.Flightsmooth);
//		ButtonListener listener2 = new ButtonListener() {
//
//			@Override
//			public void onRightButtonPress(Button button) {
//
//			}
//
//			@Override
//			public void onButtonPress(Button button) {
//				ClientSettings.Flightsmooth = box1.isSelected();
//			}
//		};
//		box1.addButtonListener(listener2);
//		final BasicCheckButton box2 = new BasicCheckButton("Flight Kick Bypass");
//		box2.setSelected(ClientSettings.flightkick);
//		ButtonListener listener3 = new ButtonListener() {
//
//			@Override
//			public void onRightButtonPress(Button button) {
//
//			}
//
//			@Override
//			public void onButtonPress(Button button) {
//				ClientSettings.flightkick = box2.isSelected();
//			}
//		};
//		box2.addButtonListener(listener3);
//		
//		final BasicCheckButton box3 = new BasicCheckButton("Glide Damage");
//		box3.setSelected(ClientSettings.glideDmg);
//		ButtonListener listener4 = new ButtonListener() {
//
//			@Override
//			public void onRightButtonPress(Button button) {
//
//			}
//
//			@Override
//			public void onButtonPress(Button button) {
//				ClientSettings.glideDmg = box3.isSelected();
//			}
//		};
//		box3.addButtonListener(listener4);
//		
//		final BasicCheckButton box4 = new BasicCheckButton("onGround Spoof");
//		box4.setSelected(ClientSettings.onGroundSpoofFlight);
//		ButtonListener listener5 = new ButtonListener() {
//
//			@Override
//			public void onRightButtonPress(Button button) {
//
//			}
//
//			@Override
//			public void onButtonPress(Button button) {
//				ClientSettings.onGroundSpoofFlight = box4.isSelected();
//			}
//		};
//		box3.addButtonListener(listener5);
		CheckBtnSetting box = new CheckBtnSetting("Items", "ITEMS");
		CheckBtnSetting box2 = new CheckBtnSetting("Firework", "FireWork");
		CheckBtnSetting box1 = new CheckBtnSetting("Holograms", "HOLOGRAM");
		
		
		return new ModSetting[] { box, box2, box1};
	}

	
}