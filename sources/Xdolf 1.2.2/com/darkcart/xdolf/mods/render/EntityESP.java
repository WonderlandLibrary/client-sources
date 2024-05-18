package com.darkcart.xdolf.mods.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.RenderUtils;

import net.minecraft.entity.Entity;

public class EntityESP extends Module {

	public EntityESP() {
		super("EntityESP", "Creates an ESP box around mob entities.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
	
	@Override
	public void onRender() {
		if(isEnabled()) {
			for (Entity e: Client.mc.world.loadedEntityList) {
				String entityPackage = e.getClass().getPackage().getName();
				if (entityPackage.equals("net.minecraft.entity.monster")) {
					RenderUtils.drawEntityESP(e, Color.red);
				}
				if (entityPackage.equals("net.minecraft.entity.passive")) {
					RenderUtils.drawEntityESP(e, Color.green);
				}
			}
		}
	}
}
