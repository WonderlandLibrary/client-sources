package me.xatzdevelopments.xatz.client.modules;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.Gui;

public class Keystrokes extends Module {

	public Keystrokes() {
		super("Keystrokes", Keyboard.KEY_NONE, Category.RENDER, "Renders keys and they lightup when you press them.");
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

		super.onUpdate();
	}

	@Override
	public void onGui() {

		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		FontRenderer fr = mc.fontRendererObj;
		
     	int wAlpha = (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()) ? 125 : 50);
		int aAlpha = (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()) ? 125 : 50);
		int sAlpha = (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()) ? 125 : 50);
		int dAlpha = (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()) ? 125 : 50);

		Gui.drawRect(sr.getScaledWidth() - 29 - 29, sr.getScaledHeight() - 4 - 25 - 29, sr.getScaledWidth() - 4 - 29, sr.getScaledHeight() - 4 - 29, new Color(0, 0, 0, wAlpha).getRGB());
		Gui.drawRect(sr.getScaledWidth() - 29 - 29 - 29, sr.getScaledHeight() - 4 - 25, sr.getScaledWidth() - 4 - 29 - 29, sr.getScaledHeight() - 4, new Color(0, 0, 0, aAlpha).getRGB());
		Gui.drawRect(sr.getScaledWidth() - 29 - 29, sr.getScaledHeight() - 4 - 25, sr.getScaledWidth() - 4 - 29, sr.getScaledHeight() - 4, new Color(0, 0, 0, sAlpha).getRGB());
		Gui.drawRect(sr.getScaledWidth() - 29, sr.getScaledHeight() - 4 - 25, sr.getScaledWidth() - 4, sr.getScaledHeight() - 4, new Color(0, 0, 0, dAlpha).getRGB());

		fr.drawString("W", sr.getScaledWidth() - 48, sr.getScaledHeight() - 49, 0xffffffff);
		fr.drawString("A", sr.getScaledWidth() - 77, sr.getScaledHeight() - 20, 0xffffffff);
		fr.drawString("S", sr.getScaledWidth() - 48.5, sr.getScaledHeight() - 20, 0xffffffff);
		fr.drawString("D", sr.getScaledWidth() - 19, sr.getScaledHeight() - 20, 0xffffffff);
		
		super.onRender();
	}

}
