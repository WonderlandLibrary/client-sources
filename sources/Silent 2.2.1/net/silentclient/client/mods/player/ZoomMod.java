package net.silentclient.client.mods.player;

import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.event.impl.EventScrollMouse;
import net.silentclient.client.event.impl.EventZoomFov;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import org.lwjgl.input.Keyboard;

public class ZoomMod extends Mod {
	public ZoomMod() {
		super("Zoom", ModCategory.MODS, "silentclient/icons/mods/customzoom.png", true);
	}
	
	private SimpleAnimation zoomAnimation = new SimpleAnimation(0.0F);
	
	private boolean active;
	private float lastSensitivity;
	private float currentFactor = 1;
	
	public boolean wasCinematic;
	
	@Override
	public void setup() {
		this.addKeybindSetting("Keybind", this, Keyboard.KEY_C);
		this.addBooleanSetting("Scroll", this, false);
		this.addBooleanSetting("Smooth Zoom", this, true);
		this.addSliderSetting("Zoom Speed", this, 14, 5, 20, true);
		this.addSliderSetting("Factor", this, 4, 2, 15, true);
		this.addBooleanSetting("Smooth Camera", this, true);
	}
	
	public boolean isActive() {
		return active;
	}
	
	@EventTarget
	public void onTick(ClientTickEvent event) {
		if(Client.getInstance().getSettingsManager().getSettingByName(this, "Keybind").isKeyDown()) {
			if(!active) {
				active = true;
				lastSensitivity = mc.gameSettings.mouseSensitivity;
				resetFactor();
				wasCinematic = this.mc.gameSettings.smoothCamera;
				mc.gameSettings.smoothCamera = Client.getInstance().getSettingsManager().getSettingByName(this, "Smooth Camera").getValBoolean();
				mc.renderGlobal.setDisplayListEntitiesDirty();
			}
		}else if(active) {
			active = false;
			setFactor(1);
			mc.gameSettings.mouseSensitivity = lastSensitivity;
			mc.gameSettings.smoothCamera = wasCinematic;
		}
	}
	
	@EventTarget
	public void onFov(EventZoomFov event) {
		
		boolean smoothZoom = Client.getInstance().getSettingsManager().getSettingByName(this, "Smooth Zoom").getValBoolean();
		float zoomSpeed = Client.getInstance().getSettingsManager().getSettingByName(this, "Zoom Speed").getValFloat() * 1.5F;
		
		zoomAnimation.setAnimation(currentFactor, zoomSpeed);

		event.setFov(event.getFov() * (smoothZoom ? zoomAnimation.getValue() : currentFactor));
	}
	
	@EventTarget
	public void onScroll(EventScrollMouse event) {
		if(active && Client.getInstance().getSettingsManager().getSettingByName(this, "Scroll").getValBoolean()) {
			event.setCancelled(true);
			if(event.getAmount() < 0) {
				if(currentFactor < 0.98) {
					currentFactor+=0.03;
				}
			}else if(event.getAmount() > 0) {
				if(currentFactor > 0.06) {
					currentFactor-=0.03;
				}
			}
		}
	}
	
	public void resetFactor() {
		setFactor(1 / Client.getInstance().getSettingsManager().getSettingByName(this, "Factor").getValFloat());
	}

	public void setFactor(float factor) {
		if(factor != currentFactor) {
			mc.renderGlobal.setDisplayListEntitiesDirty();
		}
		currentFactor = factor;
	}
}
