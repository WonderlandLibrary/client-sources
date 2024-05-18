package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;

public class AutoLog extends Module {
	
	public static Value pvpHealthFactor = new Value("AutoLog Threshold");

	public AutoLog() {
		super("autoLog", "New", "Disconnects the player when the player reaches the specified health.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.COMBAT);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if(pvpHealthFactor.getValue() < 1)
			{
				pvpHealthFactor.setValue(6.00F);
			}
			if(player.getHealth() <= pvpHealthFactor.getValue())
			{
                Wrapper.getWorld().sendQuittingDisconnectingPacket();
                Wrapper.getMinecraft().displayGuiScreen(new GuiMainMenu());
                Hacks.findMod(AutoLog.class).toggle();
			}
		}
	}
}