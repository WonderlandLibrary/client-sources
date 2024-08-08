package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.client.gui.GuiMainMenu;

public class AutoDisconnect extends Module {
	
	 public ModSetting[] getModSettings() {
	    	SliderSetting<Number> auhealth = new SliderSetting<Number>("Health", ClientSettings.auhealth, 1, 10, 0.0, ValueFormat.INT);
			
			return new ModSetting[] { auhealth };
	    }
	public AutoDisconnect() {
		super("AutoDisconnect", Keyboard.KEY_NONE, Category.WORLD, "Disconnects you on low health");
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
		if (mc.thePlayer.getHealth() < (ClientSettings.auhealth)) {
			this.toggle();
			mc.theWorld.sendQuittingDisconnectingPacket();
			mc.displayGuiScreen(new GuiMainMenu());
			System.out.println("DISCONNECTING, This is because AutoDisconnect module is active and your health got low, if you accidentally activated this module, turn it off if you want..");
		}
		super.onUpdate();
	}
}


