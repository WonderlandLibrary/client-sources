package com.kilo.mod.all;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class AutoDisconnect extends Module {
	
	public AutoDisconnect(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Health", "Minimum health before disconnecting", Interactable.TYPE.SLIDER, 6, new float[] {0, 10}, false);
	}
	
	public void onPlayerHurt() {
		if (mc.thePlayer.getHealth() < Util.makeInteger(getOptionValue("health"))*2) {
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld((WorldClient)null);
            this.mc.displayGuiScreen(new GuiMainMenu());
		}
	}
}
