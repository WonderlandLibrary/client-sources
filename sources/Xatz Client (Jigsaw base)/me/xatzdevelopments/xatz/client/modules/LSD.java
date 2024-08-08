package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class LSD extends Module {

	public LSD() {
		super("LSD", Keyboard.KEY_NONE, Category.HIDDEN, "Drugged Vision"); // Render
	}

	@Override
	public void onDisable() {

		Minecraft.getMinecraft().entityRenderer.theShaderGroup = null;
		Minecraft.getMinecraft().thePlayer.removePotionEffect(9);
		
		super.onDisable();
	}

	@Override
	public void onEnable() {
		
		Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/wobble.json"));
		Minecraft.getMinecraft().thePlayer.addPotionEffect(new PotionEffect(9, 3600 * 20, 1));
		
		super.onEnable();
	}

	@Override
	public void onUpdate() {
		
			
		super.onUpdate();
	}
}
