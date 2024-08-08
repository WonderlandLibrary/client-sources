package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class Ambiance extends Module {

	public Ambiance() {
		super("Ambiance", Keyboard.KEY_NONE, Category.WORLD, "Changes world time.(Only you can see this)");
	}
	
	@Override
	public ModSetting[] getModSettings() {
		SliderSetting<Number> ambiencetime = new SliderSetting<Number>("Time", ClientSettings.ambiencetime, 90, 590, 1000, ValueFormat.DECIMAL);
		return new ModSetting[] { ambiencetime };
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {

		super.onEnable();
	}

	@Override
	public void onUpdate() {
		mc.theWorld.setWorldTime((long) ClientSettings.ambiencetime * 1000); // Hmm
		super.onUpdate();
	}
}