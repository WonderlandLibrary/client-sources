package com.darkcart.xdolf.mods.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.RenderUtils;

import net.minecraft.entity.Entity;

public class ItemESP extends Module {

	public ItemESP() {
		super("itemESP", "Old", "Creates an ESP box around dropped items.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
	
	@Override
	public void onRender() {
		if(isEnabled()) {
			for (Entity e: Wrapper.getWorld().loadedEntityList) {
				String entityPackage = e.getClass().getPackage().getName();
				if (entityPackage.equals("net.minecraft.entity.item")) {
					RenderUtils.drawEntityESP(e, Color.magenta);
				}			}
		}
	}
}