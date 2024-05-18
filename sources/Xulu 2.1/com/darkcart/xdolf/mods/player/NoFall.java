package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

public class NoFall extends Module {
	
	/**
	 * Nuddles cool bypass
	 */
	Minecraft mc;
	  private boolean cancelled;
	 private boolean onground;
	 private boolean alwaysSend;
	 private double packets = 3000;
	public NoFall() {
		super("noFall", "Testing", "Made By nuddles mkay", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	public void setAlwaysSend(final boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }
	 public boolean isCancelled() {
	        return this.cancelled;
	    }
	    
	    public void setCancelled(final boolean state) {
	        this.cancelled = state;
	    }
	@Override
	public void onEnable(){
		Wrapper.addChatMessage("Just a test, not 100% done yet. // Nuddles");
	}
	 public boolean shouldAlwaysSend() {
	        return this.alwaysSend;
	    }
	  public void setGround(final boolean ground) {
	        this.onground = ground;
	        if (ground){
	        	mc.player.onGround = true;
	        	mc.player.isAirBorne = false;
	        }
	    }
	public void life( float setlife){
		mc.player.setHealth(setlife);
	}
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			
			if(player.onGround){
				life(19);	
				this.alwaysSend = false;
			}
			if(mc.player.fallDistance > 3){
				  for (int i = 0; i < this.packets; ++i) {
		                Wrapper.sendPacketBypass(new CPacketPlayer(true));
		            }
		    	   life(19);	
		    	   
		    	   
		    	   mc.player.onGround = true;
		    	   mc.player.isAirBorne = false;
		    	  
		    	   mc.player.fallDistance += 0;
		    	   if (!this.shouldAlwaysSend()) {
			            this.setGround(true);
			         //   Wrapper.sendPacketBypass(new CPacketPlayer(true));
			        }
		    	}
		       
		}}}