package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;

public class vClip extends Module
{
	public static Value height = new Value("vClip Height");
	
	public vClip()
	{
		super("vClip", "Old", "Works like the .vclip command.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			Minecraft.getMinecraft();
		      EntityPlayerSP var10000 = Minecraft.getMinecraft().player;
		      Minecraft.getMinecraft();
		      double var10001 = Minecraft.getMinecraft().player.posX;
		      Minecraft.getMinecraft();
		      double var10002 = Minecraft.getMinecraft().player.posY + height.getValue();
		      Minecraft.getMinecraft();
		      var10000.setPosition(var10001, var10002, Minecraft.getMinecraft().player.posZ);
		  }
		}
	}
