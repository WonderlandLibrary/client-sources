package com.darkcart.xdolf.mods.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class XuluAC extends Module
{
	private boolean noClip;
	public XuluAC()
	{
		super("xuluAntiCheat", "Players", "Detects if another player is hacking.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	private int bufferNoFall = 0;
	  private int bufferFlight = 0;
	  private int bufferSpeed;
	  private int buffernodown;
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			for (Entity p : Minecraft.world.getLoadedEntityList()) {
			      if ((p != Wrapper.getPlayer()) && (!p.isDead) && 
			        ((p instanceof EntityPlayer)))
			      {
			        if ((Wrapper.getDistanceToGround(p) > 4.0F) && (p.onGround) && (p.posY < p.prevPosY) && (!p.isSilent())) {
			          this.bufferNoFall += 1;
			        }
			        if (this.bufferNoFall >= 100)
			        {
			          Wrapper.addAntiCheatMessage("§7§l " + p.getName() + "§r §cTried to use : NoFall.");
			          this.bufferNoFall = 0;
			        }
			        if ((p.lastTickPosY < p.posY - 0.4D) && (!p.isSilent())) {
			          this.bufferFlight += 1;
			        }
			        if (this.bufferFlight >= 500)
			        {
			          Wrapper.addAntiCheatMessage("§7§l " + p.getName() + "§r §cTried to use : Flight.");
			          this.bufferFlight = 0;
			        }
			        if (p.lastTickPosX < p.posX - 0.7D) {
			          this.bufferSpeed += 1;
			        }
			        if (p.posX > p.lastTickPosX + 0.7D) {
			          this.bufferSpeed += 1;
			        }
			        if (p.lastTickPosZ < p.posZ - 0.7D) {
			          this.bufferSpeed += 1;
			        }
			        if (p.posX > p.lastTickPosX + 0.7D) {
			          this.bufferSpeed += 1;
			        }
			        if (this.bufferSpeed >= 50)
			        {
			          Wrapper.addAntiCheatMessage("§7§l " + p.getName() + "§r §cTried to use : Speed.");
			          this.bufferSpeed = 0;
			        }
			      }
			    }
			  }
	}}
