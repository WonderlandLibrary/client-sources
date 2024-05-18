package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.TimeHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

public class aacFly extends Module
{
	
	int delay = 0;
	  private double start;
	  private int d;
	  private TimeHelper time = new TimeHelper();
	  private double damage = 0.5D;
	private boolean FLYDAMAGE;
	public aacFly()
	{
		super("aacFly", "Broken", "o", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}
	
	@Override
	public void onDisable() {
		
	}
	@Override
	public void onEnable()
	  {
	  }
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			Minecraft.player.jump();
		      Minecraft.player.motionY = 10.01D;
		      Minecraft.player.onGround = true;
		      Minecraft.player.motionY = -0.01D;
		      if (Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
		        Minecraft.player.motionY = 0.3D;
		      }
		      if (Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed) {
		        Minecraft.player.motionY = -0.3D;
		      }
		      this.delay += 1;
		      if (this.delay > 45)
		      {
		        Minecraft.player.onGround = true;
		        this.delay = 0;
		      }
		    }
		  }
		}
			//Wrapper.getPlayer().setPosition(player.posX, player.posY+0.000001f, player.posZ);
			//Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayer.Position(player.posX, player.posY + 0.0010, player.posZ, false));
