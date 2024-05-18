package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Jitter extends Module {

	Minecraft mc = Minecraft.getMinecraft();

	public Jitter() {
		super("jitter", "Old", "Jitter/vanilla speed.", Keyboard.KEY_NONE,
				0xffffff, Category.PLAYER);
	}

	public void onUpdate(EntityPlayerSP player) {
		if (this.isEnabled()){
			
			if (this.mc.gameSettings.keyBindForward.pressed)
		    {
		      net.minecraft.util.Timer.timerSpeed = 2.0F;
		      Minecraft.player.setSprinting(true);
		      Minecraft.player.cameraPitch = 0.3F;
		    }
		    else
		    {
		      net.minecraft.util.Timer.timerSpeed = 1.0F;
		    }
		    if (this.mc.gameSettings.keyBindForward.pressed) {
		      if (Minecraft.player.onGround)
		      {
		        Minecraft.player.jump();
		      }
		      else
		      {
		        Minecraft.player.cameraPitch = 0.0F;
		        if (this.mc.gameSettings.keyBindForward.pressed) {
		          Minecraft.player.motionY = -2.0D;
		        }
		        net.minecraft.util.Timer.timerSpeed = 1.0F;
		      }
		    }
		  }
		}}
