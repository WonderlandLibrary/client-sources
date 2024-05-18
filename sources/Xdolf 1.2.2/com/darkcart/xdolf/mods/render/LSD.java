package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;

public class LSD extends Module {

	public LSD() {
		super("LSD", "Psycadelic!", Keyboard.KEYBOARD_SIZE, 0xffffff, Category.RENDER);
	}

	public void onEnable() {
		Wrapper.getMinecraft().renderGlobal.loadRenderers();
		if (OpenGlHelper.shadersSupported)
			if (Wrapper.getMinecraft().getRenderViewEntity() instanceof EntityPlayer) {
				if (Wrapper.getMinecraft().entityRenderer.theShaderGroup != null)
					Wrapper.getMinecraft().entityRenderer.theShaderGroup.deleteShaderGroup();

				Wrapper.getMinecraft().entityRenderer.shaderIndex = 19;

				if (Wrapper.getMinecraft().entityRenderer.shaderIndex != EntityRenderer.SHADER_COUNT)
					Wrapper.getMinecraft().entityRenderer.loadShader(EntityRenderer.SHADERS_TEXTURES[19]);
				else
					Wrapper.getMinecraft().entityRenderer.theShaderGroup = null;
			}
	}

	public void onDisable() {
		if (Wrapper.getMinecraft().entityRenderer.theShaderGroup != null) {
			Wrapper.getMinecraft().entityRenderer.theShaderGroup.deleteShaderGroup();
			Wrapper.getMinecraft().entityRenderer.theShaderGroup = null;
		}
		Wrapper.getMinecraft().gameSettings.smoothCamera = false;
	}
	
	public void onUpdate(EntityPlayerSP e) {
		if (isEnabled()) {
			Wrapper.getMinecraft().gameSettings.smoothCamera = true;
		}
	}
}
