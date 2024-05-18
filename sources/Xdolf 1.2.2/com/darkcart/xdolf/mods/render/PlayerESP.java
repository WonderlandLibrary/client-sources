package com.darkcart.xdolf.mods.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.RenderUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerESP extends Module {

	public PlayerESP() {
		super("PlayerESP", "Creates an ESP box around players.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
	
	@Override
	public void onRender() {
		if(isEnabled()) {
			for (Entity e: Wrapper.getWorld().loadedEntityList) {
				if (e != Wrapper.getPlayer() && e != null) {
					if (e instanceof EntityPlayer) {
						if(Wrapper.getFriends().isFriend(e.getName())) {
							RenderUtils.drawEntityESP(e, Color.blue);
	    				}else{
	    					RenderUtils.drawEntityESP(e, Color.red);
	    				}
					}
				}
			}
		}
	}
}