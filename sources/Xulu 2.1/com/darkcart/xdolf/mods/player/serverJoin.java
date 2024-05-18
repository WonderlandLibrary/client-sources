package com.darkcart.xdolf.mods.player;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;

public class serverJoin extends Module {
	
	/**
	 * Code from 2hacks2exploits by 2F4U. Modified by SLiZ
	 */
	
	int clock = 0;
	  static String[] insults;
	
	public serverJoin() {
		super("7b7t", "NoCheat+", "Sends 7b7t messages", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			ArrayList playermap = new ArrayList(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
		      for (Object npi : playermap) {
		        onInsult((NetworkPlayerInfo)npi);
		      }
		}
	}
	
	public void onInsult(NetworkPlayerInfo npi)
	  {
	    this.clock += 1;
	    int playercount = npi.getGameProfile().getName().length();
	    String player = npi.getGameProfile().getName();
	    if (this.clock >= 11000)
	    {
	      String[] insults = {  "Did you know? 7b7t has Dupe enabled too. 7b7tmc.us.to:40025", "Did you know? Redstone, Nether roof, elytra and more is active on 7b7tmc.us.to:40025" };
	      Random random = new Random();
	      String iteminhand = Minecraft.getMinecraft().player.getHeldEquipment().toString();
	      int index = random.nextInt(insults.length);
	      String chat = insults[index];
	      chat = chat.replace("@p", npi.getGameProfile().getName());
	      chat = chat.replaceAll("itemhand", iteminhand);
	      Minecraft.getMinecraft().player.sendChatMessage("> " + chat);
	      
	      this.clock = 0;
	    }
	    else {}
	}
	
	@Override
	public void onDisable() {
	}
}
