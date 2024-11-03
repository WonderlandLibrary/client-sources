package net.silentclient.client.mods.player;

import net.minecraft.client.settings.KeyBinding;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.gui.hud.HUDConfigScreen;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

import java.awt.*;
import java.util.ArrayList;

public class AutoSprintMod extends HudMod {

	private boolean toggled = false;
	private boolean vanilla = false;
		
	public AutoSprintMod() {
		super("Toggle Sprint", ModCategory.MODS, "silentclient/icons/mods/autosprint.png", false);
	}
	
	@Override
	public void setup() {
		this.addSliderSetting("Scale",  this, 2.0F, 1.0F, 5.0F, false);
		this.addBooleanSetting("Background", this, false);
		this.addColorSetting("Background Color", this, new Color(0, 0, 0), 127);
		this.addColorSetting("Color", this, new Color(255, 255, 255));
		this.addBooleanSetting("Font Shadow", this, true);
		this.addBooleanSetting("Brackets", this, true);
		this.addBooleanSetting("Fancy Font", this, false);
		this.addBooleanSetting("Rounded Corners", this, false);
		ArrayList<String> options = new ArrayList<String>();
		options.add("Toggle");
		options.add("Auto");
		this.addModeSetting("Sprint Mode", this, "Toggle", options);
		this.addInputSetting("Sprinting Text", this, "Sprinting");
		this.addBooleanSetting("Show HUD Text", this, true);
	}
	
	@Override
	public String getText() {
		if(Client.getInstance().getSettingsManager().getSettingByClass(AutoSprintMod.class, "Sprint Mode").getValString().equals("Auto")) {
			return Client.getInstance().getSettingsManager().getSettingByClass(AutoSprintMod.class, "Sprinting Text").getValString().trim() + " (Auto)";
		}

		if(toggled || mc.currentScreen instanceof HUDConfigScreen) {
			return Client.getInstance().getSettingsManager().getSettingByClass(AutoSprintMod.class, "Sprinting Text").getValString().trim() + " (Toggled)";
		}

		return Client.getInstance().getSettingsManager().getSettingByClass(AutoSprintMod.class, "Sprinting Text").getValString().trim() + " (Vanilla)";
	}
	
	@Override
	public boolean render(ScreenPosition pos) {
		if(!vanilla && !toggled && !Client.getInstance().getSettingsManager().getSettingByClass(AutoSprintMod.class, "Sprint Mode").getValString().equals("Auto") && !(mc.currentScreen instanceof HUDConfigScreen)) {
			return false;
		}
		if(!Client.getInstance().getSettingsManager().getSettingByName(this, "Show HUD Text").getValBoolean()) {
			return false;
		}
		
		return super.render(pos);
	}
	
	@Override
	public boolean getRender() {
		if(!Client.getInstance().getSettingsManager().getSettingByName(this, "Show HUD Text").getValBoolean()) {
			return false;
		}
		
		return super.getRender();
	}
	
	@EventTarget
	public void onUpdate(ClientTickEvent event) {
		if(Client.getInstance().getSettingsManager().getSettingByClass(AutoSprintMod.class, "Sprint Mode").getValString().equals("Auto")) {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
			return;
		}
		if(mc.thePlayer == null) {
			return;
		}
		if (mc.thePlayer.isRiding())
		{
			return;
		}

		if (mc.currentScreen != null)
		{
			return;
		}

		if(mc.gameSettings.keyBindSprint.isPressed()) {
			toggled = !toggled;
		}
		if(mc.thePlayer.isSprinting()) {
			vanilla = true;
		} else {
			vanilla = false;
		}
		if(toggled) {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
	}
}
