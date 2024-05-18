package com.darkcart.xdolf.mods.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.mods.aura.AutoLog;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.RenderUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.Config;

public class OutlineESP extends Module {

	public OutlineESP() {
		super("OutlineESP", "Creates an ESP outline around players and mobs.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
	
	public void onEnable() {
		if(Config.isFastRender()) {
			Hacks.findMod(OutlineESP.class).toggle();
			Wrapper.addChatMessage("Please disable Fast Render in Video Settings");
		}else if(Config.isShaders()) {
			Hacks.findMod(OutlineESP.class).toggle();
			Wrapper.addChatMessage("Please disable Shaders");
		}else if(Config.isAntialiasing()) {
			Hacks.findMod(OutlineESP.class).toggle();
			Wrapper.addChatMessage("Please disable AntiAliasing in Video Settings");
		}
	}
}