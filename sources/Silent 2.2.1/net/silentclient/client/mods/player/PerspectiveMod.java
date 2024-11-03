package net.silentclient.client.mods.player;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.event.impl.EventCameraRotation;
import net.silentclient.client.event.impl.EventPlayerHeadRotation;
import net.silentclient.client.event.impl.KeyEvent;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.util.Server;
import org.lwjgl.input.Keyboard;

public class PerspectiveMod extends Mod {
	
	private boolean active;
	private float yaw;
	private float pitch;
	private int previousPerspective;
	
	public PerspectiveMod() {
		super("Perspective", ModCategory.MODS, "silentclient/icons/mods/perspective.png");
	}

	@Override
	public void setup() {
		super.setup();
		this.addKeybindSetting("Keybind", this, Keyboard.KEY_LMENU);
		this.addBooleanSetting("Smooth Camera", this, false);
		this.addBooleanSetting("Toggle Perspective", this, false);
	}

	@Override
	public boolean isForceDisabled() {
		if(Client.getInstance().getAccount().isAdmin() || Client.getInstance().getAccount().isStaff() || Client.getInstance().getAccount().isTester()) {
			return false;
		}
		return Server.isHypixel() ? true : false;
	}
	
    @EventTarget
    public void updateEvent(ClientTickEvent event) {
    	if(!isForceDisabled() && !Client.getInstance().getSettingsManager().getSettingByName(this, "Toggle Perspective").getValBoolean()) {
    		if(Client.getInstance().getSettingsManager().getSettingByName(this, "Keybind").isKeyDown()) {
				start();
				mc.gameSettings.thirdPersonView = 3;
				if(Client.getInstance().getSettingsManager().getSettingByName(this, "Smooth Camera").getValBoolean()) {
					mc.gameSettings.smoothCamera = true;
				}
			}
			else {
				if(Client.getInstance().getSettingsManager().getSettingByName(this, "Smooth Camera").getValBoolean()) {
					mc.gameSettings.smoothCamera = false;
				}
				stop();
			}
    	}
    }

	boolean toggleAllowed = false;

	@EventTarget
	public void onKey(KeyEvent event) {
		if(!isForceDisabled() && Client.getInstance().getSettingsManager().getSettingByName(this, "Toggle Perspective").getValBoolean()) {
			if(event.getKey() == Client.getInstance().getSettingsManager().getSettingByName(this, "Keybind").getKeybind()) {
				toggleAllowed = !toggleAllowed;
				if(toggleAllowed) {
					if(active) {
						if(Client.getInstance().getSettingsManager().getSettingByName(this, "Smooth Camera").getValBoolean()) {
							mc.gameSettings.smoothCamera = false;
						}
						stop();
					} else {
						start();
						mc.gameSettings.thirdPersonView = 3;
						if(Client.getInstance().getSettingsManager().getSettingByName(this, "Smooth Camera").getValBoolean()) {
							mc.gameSettings.smoothCamera = true;
						}
					}
				}
			}
		}
	}
    
    public boolean isActive() {
		return this.isEnabled() && !isForceDisabled() && active;
	}
    
    @EventTarget
	public void onCameraRotation(EventCameraRotation event) {
		if(active) {
			event.setYaw(yaw);
			event.setPitch(pitch);
		}
	}
	
	@EventTarget
	public void onPlayerHeadRotation(EventPlayerHeadRotation event) {
				
		if(active) {
			float yaw = event.getYaw();
			float pitch = event.getPitch();
			event.setCancelled(true);
			pitch = -pitch;
			
			this.yaw += yaw * 0.15F;
			this.pitch = MathHelper.clamp_float(this.pitch + (pitch * 0.15F), -90, 90);
			mc.renderGlobal.setDisplayListEntitiesDirty();
		}
	}
	
	private void start() {
		if(!active) {
			active = true;
			previousPerspective = mc.gameSettings.thirdPersonView;
			mc.gameSettings.thirdPersonView = 3;
			Entity renderView = mc.getRenderViewEntity();
			yaw = renderView.rotationYaw;
			pitch = renderView.rotationPitch;
		}
	}
	
	private void stop() {
		if(active) {
			active = false;
			mc.gameSettings.thirdPersonView = previousPerspective;
			mc.renderGlobal.setDisplayListEntitiesDirty();
		}
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
}
