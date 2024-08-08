package me.xatzdevelopments.xatz.client.modules;

import me.xatzdevelopments.xatz.client.events.UpdateEvent;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;

public class ItemSize extends Module{

	public ItemSize() {
		super("ACR", 0, Category.RENDER, "This is a test module");
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public void onUpdate(UpdateEvent event) {
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	@Override
	public ModSetting[] getModSettings() {
		SliderSetting<Number> itemSize = new SliderSetting<Number>("Test", ClientSettings.itemSize, 0.1, 2, 0.0, ValueFormat.DECIMAL);
		CheckBtnSetting box2 = new CheckBtnSetting("The Real Shit", "theRealShit");
		return new ModSetting[] { itemSize, box2 };
	}
}
