package net.silentclient.client.mods.render;

import net.minecraft.client.settings.GameSettings;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

import java.lang.reflect.Field;

public class NewMotionBlurMod extends Mod {
	private Field cachedFastRender;

	public NewMotionBlurMod() {
		super("Motion Blur", ModCategory.MODS, "silentclient/icons/mods/motionblur.png");
		try {
			this.cachedFastRender = GameSettings.class.getDeclaredField("ofFastRender");
		} catch (Exception exception) {

		}
	}

	@Override
	public void setup() {
		this.addSliderSetting("Amount", this, 0.5, 0.1, 0.85, false);
	}

	public boolean isFastRenderEnabled() {
		try {
			return this.cachedFastRender.getBoolean(this.mc.gameSettings);
		} catch (Exception exception) {
			return false;
		}
	}

	@Override
	public boolean isForceDisabled() {
		return isFastRenderEnabled();
	}
}
