package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.commands.CmdSpeed;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.TimerUtil;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;

public class yPort extends Module {
	
	public static int mode;
	
	public yPort() {
		super("Speed", "", "The commands for this are broken.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}
	
	@Override
	public void onEnable(){
		super.onEnable();
	    
	}

	@Override
	public void onDisable(){
		net.minecraft.util.Timer.timerSpeed = 1.0F;
		super.onDisable();
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if (isEnabled()) {
			if (mode==0){
				this.setMode("yPort");
				net.minecraft.util.Timer.timerSpeed = 1.0f;
	            yPort(player);
	        }else  if (mode==1){
	        	this.setMode("Timer");
	        	net.minecraft.util.Timer.timerSpeed = 1.3f;
	            timer(player);
	        }
	      }
		}
	
	public void timer(EntityPlayerSP player) {
		net.minecraft.util.Timer.timerSpeed = 1.3f;
	}

	public void yPort(EntityPlayerSP player) {
		
		if (((Minecraft.player.moveForward != 0.0F) || (Minecraft.player.moveStrafing != 0.0F)) && (!Minecraft.player.isCollidedHorizontally))
	      {
	        if (Minecraft.player.fallDistance > 3.0) {
	          return;
	        }
	        if ((Minecraft.player.isInWater()) || (Minecraft.player.isOnLadder())) {
	          return;
	        }
	        EntityPlayerSP thePlayer = Minecraft.player;
	        thePlayer.posY += 0.001f;
	        Minecraft.player.motionY = -1.0D;
	        Minecraft.player.cameraPitch = -0.0F;
	        Minecraft.player.distanceWalkedModified = 0.0F;
	        net.minecraft.util.Timer.timerSpeed = 1.1F;
	      }
	      if ((Minecraft.player.isInWater()) || (Minecraft.player.isOnLadder())) {
	        return;
	      }
	      if ((Minecraft.player.onGround) && ((Minecraft.player.moveForward != 0.0F) || (Minecraft.player.moveStrafing != 0.0F)) && (!Minecraft.player.isCollidedHorizontally))
	      {
	        EntityPlayerSP thePlayer2 = Minecraft.player;
	        thePlayer2.posY += 0.0f;
	        Minecraft.player.motionY = 0.009100090122223D;
	        player.setPosition(player.posX-0.00f, player.posY+0.0002-0.0001, player.posZ);
	        Minecraft.player.distanceWalkedOnStepModified = 44.0F;
	        EntityPlayerSP thePlayer3 = Minecraft.player;
	        thePlayer3.motionX *= 1.0097f;
	        EntityPlayerSP thePlayer4 = Minecraft.player;
	        thePlayer4.motionZ *= 1.0097f;
	        Minecraft.player.cameraPitch = 0.0F;
	        net.minecraft.util.Timer.timerSpeed = 1.1023F;
	      }
	  }
	}
