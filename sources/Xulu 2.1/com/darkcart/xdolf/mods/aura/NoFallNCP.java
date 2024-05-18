package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class NoFallNCP extends Module {
	
	/**
	 * 2F4Us NoFall NCP Bypass from 2H2E [2Hacks2Exploits]
	 */
	Minecraft mc;
	 private boolean onground;
	 private boolean alwaysSend;
	public NoFallNCP() {
		super("noFallNCP", "Old NoCheat+", "2F4Us NCP Nofall, too lazy to code a actual 1", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.COMBAT);
	}
	public void setAlwaysSend(final boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }
	@Override
	public void onEnable(){
		Wrapper.addChatMessage("Just a test, not 100% done yet.");
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
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if(mc.player.fallDistance > 3){
		    	   mc.player.setHealth(20);	
		    	   mc.player.fallDistance = 2;
		    	}
		        if (!this.shouldAlwaysSend()) {
		            this.setGround(true);
		            Wrapper.sendPacketBypass(null);
		        }
		}}}