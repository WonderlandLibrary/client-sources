package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.gui.ScreenPos;
import me.xatzdevelopments.xatz.module.Module;

public class BPS extends Module {
double blocksPerSecond;
	public BPS() {
		super("BPS", Keyboard.KEY_NONE, Category.RENDER, "Calculates your blocks per second.");
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public void onUpdate() {
		blocksPerSecond = (double)Math.round(Math.pow(mc.thePlayer.posX - mc.thePlayer.prevPosX, 2) + Math.round(mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * 100) / 100;
		super.onUpdate();
	}

	@Override
	public void onRender() {
if(!Xatz.getModuleByName("ModernHotbar").isToggled()) {
	
		Xatz.getUIRenderer().addToQueue(String.valueOf("§dBPS: §r" + blocksPerSecond), ScreenPos.LEFTUP);
}

		super.onRender();
	}

}
